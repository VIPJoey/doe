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
import com.mmc.dubbo.doe.dto.ConnectDTO;
import com.mmc.dubbo.doe.dto.MethodModelDTO;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.dto.UrlModelDTO;
import com.mmc.dubbo.doe.model.ServiceModel;
import com.mmc.dubbo.doe.service.ClassService;
import com.mmc.dubbo.doe.service.ConnectService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Joey
 * @date 2018/6/29 10:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestConnectService {

    @Autowired
    private ConnectService connectService;
    @Autowired
    private ClassService classService;

    @Test
    public void testSend() throws Exception {

        String conn = "127.0.0.1:2181";

        // connect to zk
        List<ServiceModel> models = connectService.connect(conn);

        String interfaceName = "com.mmc.dubbo.api.user.UserService";
        ServiceModel model = new ServiceModel();
        model.setServiceName(interfaceName);

        Assert.assertTrue(models.contains(model));

        // list all providers
        ConnectDTO connectDTO = new ConnectDTO();
        connectDTO.setServiceName(interfaceName);
        connectDTO.setConn(conn);
        List<UrlModelDTO> providers = connectService.listProviders(connectDTO);

        Assert.assertFalse(providers.isEmpty());

        // list all method
        List<MethodModelDTO> methods = classService.listMethods(connectDTO);

        Assert.assertFalse(methods.isEmpty());

        // get the expected method
        MethodModelDTO methodModelDTO = methods.stream().filter(m ->  m.getMethodName().equals("insert") ).findAny().orElse(null);

        Assert.assertNotNull(methodModelDTO);

        // send request
        UrlModelDTO provider = providers.get(0);
        connectDTO.setProviderKey(provider.getKey());
        connectDTO.setMethodKey(methodModelDTO.getMethodKey());
        connectDTO.setJson("[{\"id\": 88888}, \"sally\", 1]");
        ResultDTO<String> ret = connectService.send(connectDTO);

        Assert.assertTrue(ret.isSuccess());

        System.out.println(JSON.toJSONString(ret));

    }



}
