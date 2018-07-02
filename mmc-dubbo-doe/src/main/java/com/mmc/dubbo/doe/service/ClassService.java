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
import com.mmc.dubbo.doe.dto.MethodModelDTO;
import com.mmc.dubbo.doe.dto.ResultDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Joey
 * @date 2018/6/28 11:28
 */
public interface ClassService {

    /**
     * generate the simple json string of the method parameters.
     *
     * @param dto
     * @return
     */
    ResultDTO<String> generateMethodParamsJsonString(@NotNull MethodModelDTO dto) throws ClassNotFoundException, InstantiationException, IllegalAccessException;

    /**
     * get all methods from the given interface.
     *
     * @param dto
     * @return
     */
    List<MethodModelDTO> listMethods(ConnectDTO dto);

}
