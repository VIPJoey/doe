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

import com.mmc.dubbo.doe.service.PomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.MalformedURLException;

/**
 * @author Joey
 * @date 2018/6/22 13:55
 */
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {


    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        log.info("ApplicationReadyEventListener.onApplicationEvent()");
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();

        PomService pomService = applicationContext.getBean("pomService", PomService.class);
        try {
            log.info("begin auto to load jars.");
            pomService.loadJars("");
            log.info("finished load jars.");
        } catch (NoSuchMethodException | MalformedURLException e) {
            log.error("fail to load jars.", e);
        }

    }
}
