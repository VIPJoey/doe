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

import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.model.CaseModel;

import java.util.List;

/**
 * @author Joey
 * @date 2018/6/29 15:21
 */
public interface CaseService {

    /**
     * save the case.
     *
     * @param model
     * @return
     */
    ResultDTO<CaseModel> save(CaseModel model);

    /**
     * list all case.
     *
     * @return
     */
    List<Object> listAll();

}
