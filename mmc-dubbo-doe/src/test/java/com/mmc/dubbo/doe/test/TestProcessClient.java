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

import com.mmc.dubbo.doe.cache.RedisResolver;
import com.mmc.dubbo.doe.client.ProcessClient;
import com.mmc.dubbo.doe.dto.PomDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Joey
 * @date 2018/6/15 18:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProcessClient {

    @Autowired
    private RedisResolver redisResolver;

    @Test
    public void testDownload() throws InterruptedException {


        String pomXml = TestProcessClient.class.getResource("/test-pom.xml").getPath();
        pomXml = pomXml.substring(1);
        System.out.println(pomXml);

        String libPath = pomXml.substring(0, pomXml.lastIndexOf("/")) + "lib";
        System.out.println(libPath);

        PomDTO dto = new PomDTO();
        ProcessClient client = new ProcessClient(dto, redisResolver, pomXml, libPath);
        client.start();
        client.join();

        Assert.assertTrue(client.isDone());

    }

}
