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
import com.mmc.dubbo.doe.dto.CaseModelDTO;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.model.CaseModel;
import com.mmc.dubbo.doe.service.CaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Joey
 * @date 2018/6/29 15:57
 */
@RestController
@RequestMapping("/doe/case")
@Slf4j
public class CaseController {

    @Autowired
    private CaseService caseService;


    @RequestMapping("/doSave")
    public ResultDTO<CaseModel> doSave(@NotNull CaseModelDTO dto) {

        log.info("CaseController.doSave({})", JSON.toJSONString(dto));

        ResultDTO<CaseModel> resultDTO;

        try {

            CaseModel model = new CaseModel();
            BeanUtils.copyProperties(dto, model);
            resultDTO = caseService.save(model);

        } catch(Exception e) {

            resultDTO = ResultDTO.createExceptionResult(e, CaseModel.class);
        }

        return resultDTO;
    }

    @RequestMapping("/doList")
    public String doList(CaseModelDTO dto) {

        log.info("CaseController.doList({})", JSON.toJSONString(dto));

        try {

            List<Object> list = caseService.listAll();

            List<CaseModel> ret = list.stream().map(l -> {
                CaseModel model = new CaseModel();
                BeanUtils.copyProperties(l, model);
                return model;
            }).collect(Collectors.toList());

            return JSON.toJSONString(ret);

        } catch(Exception e) {

            return "[]";
        }
    }
}
