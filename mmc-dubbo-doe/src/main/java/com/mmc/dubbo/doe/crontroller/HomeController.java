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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Joey
 * @date 2018/6/16 11:57
 */
@Controller
@RequestMapping("/doe/home")
public class HomeController {

    @RequestMapping("/index")
    public String index() {

        return "/pages/v3/easyCnt.html";
    }

    @RequestMapping("/normalCnt")
    public String openNormalPage() {

        return "/pages/v3/normalCnt.html";
    }

    @RequestMapping("/caseCnt")
    public String openCasePage() {

        return "/pages/v3/caseCnt.html";
    }

    @RequestMapping("/easyCnt")
    public String openEasyPage() {

        return "/pages/v3/easyCnt.html";
    }

    @RequestMapping("/addJar")
    public String openAddJarPage() {

        return "/pages/v3/addJar.html";
    }

    @RequestMapping("/listJar")
    public String openListJarPage() {

        return "/pages/v3/listJar.html";
    }

    @RequestMapping("/listZk")
    public String openListZkPage() {

        return "/pages/v3/listZk.html";
    }


}
