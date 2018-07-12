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
import com.mmc.dubbo.doe.model.RegistryModel;

import java.util.List;

/**
 * @author Joey
 * @date 2018/7/9 19:40
 */
public interface ConfigService {

    /**
     * list all registry.
     *
     * @return
     */
    List<RegistryModel> listRegistry();

    /**
     * add registry.
     *
     * @return
     */
    ResultDTO<RegistryModel> addRegistry(RegistryModel model);

    /**
     * load zk config.
     */
    void loadZkConfigFromResource();

    /**
     * delete registry.
     *
     * @param model
     * @return
     */
    ResultDTO<RegistryModel> delRegistry(RegistryModel model);
}
