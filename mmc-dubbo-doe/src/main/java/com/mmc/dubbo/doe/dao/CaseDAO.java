/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.dao;

import com.mmc.dubbo.doe.model.CaseModel;

import java.util.List;

/**
 * @author Joey
 * @date 2018/6/29 15:36
 */
public interface CaseDAO {

    /**
     * save the case.
     *
     * @param model
     * @return
     */
    int save(CaseModel model);

    /**
     * list all model.
     * @return
     */
    List<CaseModel> listAll();

}
