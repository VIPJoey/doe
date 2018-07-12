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
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.model.RegistryModel;
import com.mmc.dubbo.doe.service.ConfigService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Joey
 * @date 2018/7/10 11:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestConfigService {

    @Autowired
    private ConfigService configService;

    @Test
    public void testListRegistry() {

        List<RegistryModel> list = configService.listRegistry();

        Assert.assertFalse(list.isEmpty());

        int size = list.size();

        list.stream().forEach(l -> {
            System.out.println(JSON.toJSONString(l));
        });

        System.out.println("-------------------------------");

        configService.loadZkConfigFromResource();

        list = configService.listRegistry();

        Assert.assertEquals(size, list.size());

        list.stream().forEach(l -> {
            System.out.println(JSON.toJSONString(l));
        });

    }

    @Test
    public void testAddRegistry() {

        RegistryModel model = new RegistryModel();
        model.setRegistryDesc("test registry");
        model.setRegistryKey("test address");

        ResultDTO<RegistryModel> ret = configService.addRegistry(model);
        System.out.println(JSON.toJSONString(ret));

        List<RegistryModel> list = configService.listRegistry();
        list.stream().forEach(l -> {
            System.out.println(JSON.toJSONString(l));
        });

        Assert.assertTrue(list.contains(model));


    }

    @Test
    public void testDelRegistry() {

        RegistryModel model = new RegistryModel();
        model.setRegistryDesc("test registry");
        model.setRegistryKey("test address");

        ResultDTO<RegistryModel> ret = configService.delRegistry(model);
        System.out.println(JSON.toJSONString(ret));

        List<RegistryModel> list = configService.listRegistry();
        list.stream().forEach(l -> {
            System.out.println(JSON.toJSONString(l));
        });

        Assert.assertFalse(list.contains(model));

    }


}
