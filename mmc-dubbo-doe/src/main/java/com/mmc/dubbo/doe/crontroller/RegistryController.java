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

import com.alibaba.fastjson.JSON;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.model.RegistryModel;
import com.mmc.dubbo.doe.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Joey
 * @date 2018/7/9 19:39
 */
@RestController
@RequestMapping("/doe/zk")
@Slf4j
public class RegistryController {

    @Autowired
    private ConfigService configService;

    @RequestMapping("/doListZk")
    public String doListZk() {

        log.info("RegistryController.doListZk()");

        String result;

        try {

            List<RegistryModel> models = configService.listRegistry();
            result = JSON.toJSONString(models);

        } catch (Exception e) {

            result = "[]";
        }

        return result;
    }


    @RequestMapping("/doListRegistry")
    public ResultDTO<Object> doListRegistry() {

        log.info("RegistryController.doListRegistry()");

        ResultDTO<Object> resultDTO = new ResultDTO<>();

        try {

            List<RegistryModel> models = configService.listRegistry();
            resultDTO.setData(models);
            resultDTO.setSuccess(true);

        } catch (Exception e) {

            resultDTO = ResultDTO.createExceptionResult("occur an error when list registry address : ", e, Object.class);
        }

        return resultDTO;
    }

    @RequestMapping("/addRegistry")
    public ResultDTO<RegistryModel> addRegistry(@NotNull RegistryModel dto) {

        log.info("RegistryController.addRegistry({})", JSON.toJSONString(dto));

        ResultDTO<RegistryModel> resultDTO;

        try {

            resultDTO = configService.addRegistry(dto);

        } catch (Exception e) {

            resultDTO = ResultDTO.createExceptionResult(e, RegistryModel.class);
        }

        return resultDTO;
    }

    @RequestMapping("/delRegistry")
    public ResultDTO<RegistryModel> delRegistry(@NotNull RegistryModel dto) {

        log.info("RegistryController.delRegistry({})", JSON.toJSONString(dto));

        ResultDTO<RegistryModel> resultDTO;

        try {

            resultDTO = configService.delRegistry(dto);

        } catch (Exception e) {

            resultDTO = ResultDTO.createExceptionResult(e, RegistryModel.class);
        }

        return resultDTO;
    }


}
