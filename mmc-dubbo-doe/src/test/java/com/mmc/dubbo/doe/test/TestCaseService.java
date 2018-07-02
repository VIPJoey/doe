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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.model.CaseModel;
import com.mmc.dubbo.doe.service.CaseService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Joey
 * @date 2018/6/29 15:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCaseService {

    @Autowired
    private CaseService caseService;

    @Test
    public void testSave() {

        CaseModel model = new CaseModel();
        model.setCaseId(1);
        model.setCaseName("demo case");
        model.setProviderKey("com.mmc.dubbo.api.user.UserService#172.26.246.1#30880#");
        model.setMethodKey("com.mmc.dubbo.api.user.UserService#6A365B586448DD533E9F474672D657CE");
        model.setJson("[{\"height\":165,\"id\":490,\"name\":\"sally\",\"sex\":0},\"sally\",0]");

        ResultDTO<CaseModel> ret = caseService.save(model);

        Assert.assertTrue(ret.isSuccess());

    }

    @Test
    public void testListAll() {

        List<Object> list = caseService.listAll();

        String json = JSON.toJSONString(list, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        System.out.println(json);

    }

}
