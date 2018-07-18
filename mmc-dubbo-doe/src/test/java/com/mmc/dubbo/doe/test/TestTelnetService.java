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
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.service.TelnetService;
import com.mmc.dubbo.doe.service.impl.TelnetServiceImpl;
import org.apache.commons.net.telnet.TelnetClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;

/**
 * @author Joey
 * @date 2018/7/5 14:57
 */
public class TestTelnetService {

    private TelnetService telnetService = new TelnetServiceImpl();

    @Test
    public void testSend() {

        ConnectDTO dto = new ConnectDTO();

        dto.setConn("127.0.0.1:30880");
        dto.setServiceName("com.mmc.dubbo.api.user.UserService");
        dto.setMethodName("getCurrentById");
        dto.setJson("22222");

        ResultDTO<String> ret = telnetService.send(dto);

        Assert.assertTrue(ret.isSuccess());

        System.out.println(ret.getData());
    }



}
