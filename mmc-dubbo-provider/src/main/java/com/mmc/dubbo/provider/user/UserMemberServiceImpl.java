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
@com.alibaba.dubbo.config.annotation.Service
public class UserMemberServiceImpl implements UserService {


    @Override
    public UserFact getCurrentById(long id) {

        System.out.println("UserMemberServiceImpl.getCurrentById");

        UserFact user = new UserFact();
        user.setId(id);
        user.setName("SUCCESS");

        return user;

    }

    @Override
    public UserFact insert(UserFact u, String name, int sex) {

        System.out.println("UserMemberServiceImpl.insert");

        UserFact userFact = new UserFact();
        userFact.setName(name);
        userFact.setSex(sex);
        userFact.setId(u.getId());

        return userFact;

    }
}
