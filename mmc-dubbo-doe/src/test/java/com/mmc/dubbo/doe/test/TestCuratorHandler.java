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

import com.mmc.dubbo.doe.dto.ConnectDTO;
import com.mmc.dubbo.doe.handler.CuratorHandler;
import com.mmc.dubbo.doe.dto.MethodModelDTO;
import com.mmc.dubbo.doe.model.ServiceModel;
import com.mmc.dubbo.doe.model.UrlModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author Joey
 * @date 2018/6/14 19:40
 */
public class TestCuratorHandler {


    private CuratorHandler client;

    @Before
    public void testConnect() throws NoSuchFieldException, IllegalAccessException {

        client = new CuratorHandler("zookeeper", "127.0.0.1", 2181);
        client.doConnect();

    }

    @Test
    public void testGetInterfaces() {

        List<ServiceModel> list = client.getInterfaces();
        Assert.assertFalse(list.isEmpty());

        System.out.println("----------------------------------------------------------------");
        list.forEach(l -> {
            System.out.println(l);
        });

    }

    @Test
    public void testGetProviders() {

        String interfaceName = "com.mmc.dubbo.api.user.UserService";

        ConnectDTO dto = new ConnectDTO();
        dto.setServiceName(interfaceName);

        List<UrlModel> list = client.getProviders(dto);
        Assert.assertTrue(list.size() <= 1);

        dto.setVersion("2.0.0");
        dto.setGroup("mmcgroup");
        list = client.getProviders(dto);
        Assert.assertTrue(list.size() == 0);

        System.out.println("----------------------------------------------------------------");
        list.forEach(l -> {
            System.out.println(l);
        });
    }

    @Test
    public void testGetMethods() throws ClassNotFoundException {

        String interfaceName = "com.mmc.dubbo.api.user.UserService";
        List<MethodModelDTO> list = client.getMethods(interfaceName);
        Assert.assertFalse(list.isEmpty());

        System.out.println("----------------------------------------------------------------");
        list.forEach(l -> {
            System.out.println(l);
        });
    }



}
