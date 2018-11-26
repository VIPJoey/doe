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
import com.mmc.dubbo.doe.context.Const;
import com.mmc.dubbo.doe.dto.PomDTO;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.model.PomModel;
import com.mmc.dubbo.doe.service.PomService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Joey
 * @date 2018/6/15 20:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPomService {

    @Autowired
    private PomService pomService;

    @Test
    public void testPath() {

        String path = TestPomService.class.getResource("").getPath();
        System.out.println(path);
        String path2 = TestPomService.class.getResource("/test-pom.xml").getPath();
        System.out.println(path2);

    }

    @Test
    public void testParse() throws IOException, SAXException {

        String xml = "\t\t<dependency>\n" +
                "\t\t\t<groupId>mmc-dubbo</groupId>\n" +
                "\t\t\t<artifactId>api</artifactId>\n" +
                "\t\t\t<version>1.0-SNAPSHOT</version>\n" +
                "\t\t</dependency>";


        List<PomModel> models = pomService.parsePom(xml);
        Assert.assertFalse(models.isEmpty());

        models.forEach(m -> {

            Assert.assertFalse(m.isBroken());
            System.out.println(JSON.toJSONString(m));
        });
    }

    @Test
    public void testAppend() throws Exception {

        String groupId = "com.mmc";
        String artifactId = "mybatis";
        String version = "1.0-SNAPSHOT";

        String path2 = TestPomService.class.getResource("/test-pom.xml").getPath();
        System.out.println(path2);

        List<PomModel> models = new ArrayList<>();
        PomModel aModel = new PomModel();
        aModel.setGroupId(groupId);
        aModel.setArtifactId(artifactId);
        aModel.setVersion(version);
        models.add(aModel);

        PomModel bModel = new PomModel();
        bModel.setGroupId(groupId);
        bModel.setArtifactId(artifactId);
        bModel.setVersion(version);
        models.add(bModel);

        pomService.appendPom(models, path2);

    }

    @Test
    public void testLoad() throws NoSuchMethodException, MalformedURLException, ClassNotFoundException {

        String className = "com.nora.interfaces.auth.UserFact";

        ResultDTO<String> ret = pomService.loadJars("");


        Assert.assertTrue(ret.isSuccess());

        Class<?> clazz = Class.forName(className);

        Assert.assertNotNull(clazz);

        Arrays.stream(clazz.getMethods()).forEach(m -> {

            System.out.println(m.getName());

        });

    }

    @Test
    public void testListJars() throws IOException, SAXException, ParserConfigurationException {

        PomDTO dto = new PomDTO();
        String pomPath = TestPomService.class.getResource("/test-pom.xml").getPath();
        dto.setPath(pomPath);

        List<PomModel> list = pomService.listJars(dto);

        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testInvoke() throws Exception {

        String xml = "\t\t<dependency>\n" +
                "\t\t\t<groupId>mmc-dubbo</groupId>\n" +
                "\t\t\t<artifactId>api</artifactId>\n" +
                "\t\t\t<version>1.0-SNAPSHOT</version>\n" +
                "\t\t</dependency>";

        PomDTO dto = new PomDTO();
        dto.setPom(xml);

        ResultDTO<PomDTO> ret = pomService.invoke(dto);
        System.out.println("ret: " + JSON.toJSONString(ret));

        if (!ret.isSuccess()) {
            System.out.println(ret.getMsg());
            return;
        }

        String requestId = ret.getData().getRequestId();
        ResultDTO<String> msgRet = new ResultDTO<>();

        while (msgRet.getCode() != Const.COMPLETE_FLAG) {

            msgRet = pomService.getRealTimeMsg(requestId);
            System.out.println(JSON.toJSONString(msgRet));

            Thread.sleep(1000);
        }

    }

    @Test
    public void testLoadPomFile() {

        String pomXmlPath = TestPomService.class.getResource("/test-pom.xml").getPath();
        String content = pomService.loadPomFile(pomXmlPath);

        Assert.assertNotNull(content);
        System.out.println(content);


    }

    @Test
    public void testOverridePomFile() {

        String pomXmlPath = TestPomService.class.getResource("/test-pom.xml").getPath();
        String content = pomService.loadPomFile(pomXmlPath) + "\r\naaa";

        boolean flag = pomService.overridePomFile(pomXmlPath, content);

        Assert.assertTrue(flag);

    }

}
