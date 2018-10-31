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
import com.mmc.dubbo.doe.dto.ConnectDTO;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.dto.MethodModelDTO;
import com.mmc.dubbo.doe.dto.UrlModelDTO;
import com.mmc.dubbo.doe.model.ServiceModel;
import com.mmc.dubbo.doe.service.ClassService;
import com.mmc.dubbo.doe.service.ConnectService;
import com.mmc.dubbo.doe.service.TelnetService;
import com.mmc.dubbo.doe.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Joey
 * @date 2018/6/18 17:07
 */
@RestController
@RequestMapping("/doe/dubbo")
@Slf4j
public class DubboController {

    @Autowired
    private ConnectService connectService;

    @Autowired
    private ClassService classService;

    @Autowired
    private TelnetService telnetService;

    @RequestMapping("/doSendWithTelnet")
    public ResultDTO<String> doSendWithTelnet(@NotNull ConnectDTO dto) {

        log.info("DubboController.doSendWithTelnet({})", JSON.toJSONString(dto));

        ResultDTO<String> resultDTO;

        try {

            resultDTO = telnetService.send(dto);

        } catch(Exception e) {

            resultDTO = ResultDTO.createExceptionResult(e, String.class);
        }

        return resultDTO;
    }

    @RequestMapping("/doSend")
    public ResultDTO<String> doSend(@NotNull ConnectDTO dto) {

        log.info("DubboController.doSend({})", JSON.toJSONString(dto));

        ResultDTO<String> resultDTO;

        try {

            resultDTO = connectService.send(dto);

        } catch(Exception e) {

            resultDTO = ResultDTO.createExceptionResult(e, String.class);
        }

        return resultDTO;
    }

    @RequestMapping("/doListParams")
    public ResultDTO<String> doListParams(@NotNull MethodModelDTO dto) {

        log.info("DubboController.doListParams({})", JSON.toJSONString(dto));

        ResultDTO<String> resultDTO;

        try {

            resultDTO = classService.generateMethodParamsJsonString(dto);

        } catch(Exception e) {

            resultDTO = ResultDTO.createExceptionResult(e, String.class);
        }


        return resultDTO;
    }

    @RequestMapping("/doListMethods")
    public ResultDTO<Object> doListMethods(@NotNull ConnectDTO dto) {

        log.info("DubboController.doListMethods({})", dto.getProviderKey());

        ResultDTO<Object> resultDTO = new ResultDTO<>();

        try {

            List<MethodModelDTO> models = classService.listMethods(dto);
            if (CollectionUtils.isEmpty(models)) {

                resultDTO = ResultDTO.createErrorResult(StringUtil.format("no methods for {}.",
                        dto.getServiceName()), Object.class);

            } else {

                log.info("methods: {}", JSON.toJSONString(models));
                resultDTO.setData(models);
                resultDTO.setSuccess(true);

            }

        } catch(Exception e) {

            resultDTO = ResultDTO.createExceptionResult(e, Object.class);
            resultDTO.setMsg("occur an error when get methods : " + resultDTO.getMsg());
        }

        return resultDTO;
    }

    @RequestMapping("/doListProviders")
    public ResultDTO<Object> doListProviders(@NotNull ConnectDTO dto) {

        log.info("DubboController.doListProviders({} {} {})", dto.getServiceName(), dto.getVersion(), dto.getGroup());

        ResultDTO<Object> resultDTO = new ResultDTO<>();

        try {


            List<UrlModelDTO> models = connectService.listProviders(dto);
            if (CollectionUtils.isEmpty(models)) {

                resultDTO = ResultDTO.createErrorResult(StringUtil.format("no provider for {} in this zookeeper registry.",
                        dto.getServiceName()), Object.class);

            } else {

                log.info("providers: {}", JSON.toJSONString(models));
                resultDTO.setData(models);
                resultDTO.setSuccess(true);

            }

        } catch(Exception e) {

            resultDTO = ResultDTO.createExceptionResult(e, Object.class);
            resultDTO.setMsg("occur an error when get provider : " + resultDTO.getMsg());
        }

        return resultDTO;
    }

    @RequestMapping("/doConnect")
    public ResultDTO<Object> doConnect(@NotNull String conn) {

        log.debug("DubboController.doConnect({})", conn);

        ResultDTO<Object> resultDTO = new ResultDTO<>();

        try {

            List<ServiceModel> models = connectService.connect(conn);
            if (CollectionUtils.isEmpty(models)) {

                resultDTO = ResultDTO.createErrorResult("no provider for this this zookeeper registry.", Object.class);

            } else {

                resultDTO.setData(models);
                resultDTO.setSuccess(true);

            }

        } catch(Exception e) {

            resultDTO = ResultDTO.createExceptionResult(e, Object.class);
        }

        return resultDTO;

    }
}
