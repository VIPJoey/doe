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

import javax.validation.constraints.NotNull;

/**
 * @author Joey
 * @date 2018/7/17 15:10
 */
public interface TelnetService {

    /**
     * send message with telnet client.
     * @param dto
     * @return
     */
    ResultDTO<String> send(@NotNull ConnectDTO dto);
}
