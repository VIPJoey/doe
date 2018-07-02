/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.test;

import com.alibaba.fastjson.JSON;
import com.mmc.dubbo.api.user.UserService;
import com.mmc.dubbo.doe.dto.ConnectDTO;
import com.mmc.dubbo.doe.dto.MethodModelDTO;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.service.ClassService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Joey
 * @date 2018/6/27 17:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestClassService {

    @Autowired
    private ClassService classService;

    @Test
    public void testGenerateMethodParamsJsonString() throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        System.out.println("begin.");

        MethodModelDTO dto = new MethodModelDTO();
        dto.setInterfaceName("com.mmc.dubbo.doe.service.ConnectService");
        dto.setMethodName("send");
        ResultDTO<String> ret = classService.generateMethodParamsJsonString(dto);

        Assert.assertTrue(ret.isSuccess());
        System.out.println(ret.getData());

        System.out.println("-----------------------------");

        dto.setInterfaceName("com.mmc.dubbo.doe.service.PomService");
        dto.setMethodName("appendPom");

        ret = classService.generateMethodParamsJsonString(dto);
        Assert.assertTrue(ret.isSuccess());
        System.out.println(ret.getData());

        System.out.println("done.");
    }

    @Test
    public void testListMethods() {

        System.out.println("begin.");

        ConnectDTO dto = new ConnectDTO();
        dto.setServiceName(UserService.class.getName());

        List<MethodModelDTO> ret = classService.listMethods(dto);

        Assert.assertFalse(ret.isEmpty());

        System.out.println(JSON.toJSONString(ret));

        System.out.println("------------------------");

        ret = classService.listMethods(dto);

        System.out.println(JSON.toJSONString(ret));


        System.out.println("done.");

    }


}
