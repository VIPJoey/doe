/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.crontroller;

import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.service.PomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;

/**
 * @author Joey
 * @date 2018/10/30 16:28
 */
@Slf4j
@RestController
@RequestMapping("/doe/sys")
public class SysConfController {

    @Value("${doe.watchdog.url}")
    private String url;

    @Resource
    private PomService pomService;

    @RequestMapping("/doReload")
    public ResultDTO<String> doReload(HttpServletResponse response) {

        log.info("SysConfController.doReload");

        try {

            return pomService.loadJars("");

        } catch (NoSuchMethodException | MalformedURLException e) {

            return ResultDTO.handleException(null, null, e);
        }

    }

    @RequestMapping("/doRepublish")
    public ResultDTO<String> doRepublish(HttpServletResponse response) {

        log.info("SysConfController.doRepublish");

        return pomService.deleteJars("");

    }
}
