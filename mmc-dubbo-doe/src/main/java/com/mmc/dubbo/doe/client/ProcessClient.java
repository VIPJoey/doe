/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.client;

import com.mmc.dubbo.doe.cache.RedisResolver;
import com.mmc.dubbo.doe.context.Const;
import com.mmc.dubbo.doe.context.TaskContainer;
import com.mmc.dubbo.doe.dto.PomDTO;
import com.mmc.dubbo.doe.handler.StreamHandler;
import com.mmc.dubbo.doe.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Joey
 * @date 2018/6/17 23:43
 */
@Slf4j
public class ProcessClient extends Thread {

    private final String libPath;
    private final RedisResolver redisResolver;
    private final PomDTO dto;
    private final String pomXml;
    private long timeout = 20;
    private volatile boolean done;

    public ProcessClient(PomDTO dto, RedisResolver redisResolver, String pomXml, String libPath) {
        this.dto = dto;
        this.redisResolver = redisResolver;
        this.pomXml = pomXml;
        this.libPath = libPath;
    }

    @Override
    public void run() {

        log.info("begin to download the jars.");

        // set running flag
        this.putFlag();

        // make the command depends on the OS.
        String command = makeCommand(pomXml);

        log.info("begin to exec the command {}", command);
        Process ps = null;
        try {
            ps = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            log.error(StringUtil.format("can't execute the command {}", command), e);
            return;
        }

        // 再开线程执行
        TaskContainer.getTaskContainer().execute(new StreamHandler(ps, redisResolver, dto.getRequestId(), libPath));

        // no longer than default 20 minutes.
        try {
            ps.waitFor(timeout, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.error("waiting too long...", e);
        }

        // remove the key
        this.removeFlag();

        // set complete normally flag
        this.done = true;

    }

    private void putFlag() {
        // set the key mark as the running flag and the longest lifetime of task was one hour.
        log.info("set the key to mark as the running flag and the longest lifetime of task was one hour");
        redisResolver.set(Const.DOE_DOWNLOAD_JAR_TASK, Const.RUNNING_FlAG, 1, TimeUnit.HOURS);
    }

    /**
     * remove the running flag.
     */
    private void removeFlag() {
        log.info("remove the running flag.");
        redisResolver.del(Const.DOE_DOWNLOAD_JAR_TASK);
    }
    /**
     * get the cmd code.
     *
     * @param pomXml
     * @return
     */
    private String makeCommand(String pomXml) {

        if (isOSLinux()) {
            return StringUtil.format("/bin/bash -c  mvn dependency:copy-dependencies -DoutputDirectory={} -DincludeScope=compile -f {}", libPath, pomXml);
        } else if (isOSMac()){
            return StringUtil.format("mvn dependency:copy-dependencies -DoutputDirectory={} -DincludeScope=compile -f {}", libPath, pomXml);
        }else {
            return StringUtil.format("cmd /c  mvn dependency:copy-dependencies -DoutputDirectory=lib -DincludeScope=compile -f {}", pomXml);
        }
    }

    /**
     * judge if linux os.
     *
     * @return
     */
    public static boolean isOSLinux() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().contains("linux")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * judge if mac os.
     *
     * @return
     */
    public static boolean isOSMac() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().contains("mac")) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isDone() {
        return done;
    }

    public boolean isRunning() {

        if (redisResolver.hasKey(Const.DOE_DOWNLOAD_JAR_TASK)) {
            log.warn("some task was already running at background, please try again for a few minutes later.");
            return true;
        }
        return false;
    }
}
