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

/**
 * @author Joey
 * @date 2018/11/26 16:20
 */
public interface MenuService {

    /**
     * get url map to the mid.
     *
     * @param mid menuId
     * @return the menu mrl
     */
    String getUrl(Integer mid);
}
