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

import com.mmc.dubbo.doe.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Joey
 * @date 2018/6/16 11:57
 */
@Controller
@RequestMapping("/doe/home")
public class HomeController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/index")
    public String index(Model model) {

        // open easyCnt page defaultly.
        return index("f16001100", model);
    }

    @RequestMapping("/main")
    public String index(String mid, Model model) {

        // you can do something here, such as auth validation,,,
        Integer menuId = Integer.valueOf(mid.substring(1));
        String path = menuService.getUrl(menuId);
        String menuHtml = menuService.getHtml();

        model.addAttribute("mid", mid);
        model.addAttribute("menuHtml", menuHtml);

        return path;

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
    @RequestMapping("/editPom")
    public String openEditPomPage() {

        return "/pages/v3/editPom.html";
    }

    @RequestMapping("/listZk")
    public String openListZkPage() {

        return "/pages/v3/listZk.html";
    }

    @RequestMapping("/sys")
    public String openSysPage() {

        return "/pages/v3/sys.html";
    }

}
