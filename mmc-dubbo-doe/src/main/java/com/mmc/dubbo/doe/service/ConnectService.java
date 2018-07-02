/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.service;

import com.mmc.dubbo.doe.dto.ConnectDTO;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.dto.MethodModelDTO;
import com.mmc.dubbo.doe.dto.UrlModelDTO;
import com.mmc.dubbo.doe.model.ServiceModel;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Joey
 * @date 2018/6/18 17:10
 */
public interface ConnectService {

    /**
     * connect to zk and get all providers.
     *
     * @param conn
     * @return
     */
    List<ServiceModel> connect(@NotNull String conn) throws NoSuchFieldException, IllegalAccessException;

    /**
     * list providers of service.
     *
     * @param connect
     * @return
     */
    List<UrlModelDTO> listProviders(@NotNull ConnectDTO connect) throws NoSuchFieldException, IllegalAccessException;

    /**
     * send request to the real dubbo server.
     *
     * @param dto
     * @return
     */
    ResultDTO<String> send(ConnectDTO dto) throws Exception;
}
