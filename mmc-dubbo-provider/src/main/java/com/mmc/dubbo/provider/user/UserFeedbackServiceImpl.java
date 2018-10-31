/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.provider.user;

import com.mmc.dubbo.api.user.UserFact;
import com.mmc.dubbo.api.user.UserService;

/**
 * @author Joey
 * @date 2018/5/8 20:31
 */
@com.alibaba.dubbo.config.annotation.Service(version = "2.0.0", group = "mmcgroup")
public class UserFeedbackServiceImpl implements UserService {

    /**
     * 测试方法一（单参数）.
     *
     * @param id
     * @return
     */
    @Override
    public UserFact getCurrentById(long id) {

        System.out.println("default provider UserFeedbackServiceImpl.getCurrentById()");

        UserFact user = new UserFact();
        user.setId(id);
        user.setName("mmcgroup");

        return user;
    }

    /**
     * 测试方法二（多参数）.
     *
     * @param u
     * @param name
     * @param sex
     * @return
     */
    @Override
    public UserFact insert(UserFact u, String name, int sex) {

        UserFact userFact = new UserFact();
        userFact.setName(name);
        userFact.setSex(sex);
        userFact.setId(u.getId());

        return userFact;
    }
}
