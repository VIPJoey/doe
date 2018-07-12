/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.context;

/**
 * @author Joey
 * @date 2018/6/17 18:38
 */
public class Const {

    /**
     * download task key.
     */
    public static final String DOE_DOWNLOAD_JAR_TASK = "doe:download:jar:task";
    /**
     * when the task was running.
     */
    public static final int RUNNING_FlAG = 1;
    /**
     * when the task has completed.
     */
    public static final int COMPLETE_FLAG = 2;
    /**
     * download task real time message key.
     */
    public static final String DOE_DOWNLOAD_JAR_MESSAGE = "doe:download:jar:msg:{}";

    /**
     * the project cache namespace.
     */
    public static final String DOE_CACHE_PREFIX = "doe:cache";
    /**
     * use case key.
     */
    public static final String DOE_CASE_KEY = "doe:case";
    /**
     * all config of zk address key.
     */
    public static final String DOE_REGISTRY_KEY = "doe:registry:list";
}
