/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */

package com.mmc.dubbo.api.user;

/**
 * @author Joey
 * @date 2018/5/8 20:25
 */
public interface UserService {

    /**
     * 测试方法一（单个参数）.
     * @param id
     * @return
     */
    UserFact getCurrentById(long id);

    /**
     * 测试方法二（多个参数）.
     * @param u
     * @param name
     * @param sex
     * @return
     */
    UserFact insert(UserFact u, String name, int sex);

}
