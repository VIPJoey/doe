/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.service.impl;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.mmc.dubbo.doe.cache.MethodCaches;
import com.mmc.dubbo.doe.cache.RedisResolver;
import com.mmc.dubbo.doe.cache.UrlCaches;
import com.mmc.dubbo.doe.context.Const;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.exception.DoeException;
import com.mmc.dubbo.doe.model.CaseModel;
import com.mmc.dubbo.doe.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Joey
 * @date 2018/6/29 15:20
 */
@Service("caseService")
public class CaseServiceImpl implements CaseService {

    @Autowired
    private RedisResolver redisResolver;

    private static final AtomicLong counter = new AtomicLong();

    /**
     * save the case.
     *
     * @param model
     * @return
     */
    @Override
    public ResultDTO<CaseModel> save(@NotNull CaseModel model) {

        if (StringUtils.isEmpty(model.getProviderKey())) {
            throw new DoeException("获取不到提供者！");
        }
        if (StringUtils.isEmpty(model.getMethodKey())) {
            throw new DoeException("获取不到方法！");
        }

        model.setAddress(UrlCaches.get(model.getProviderKey()).getUrl().getAddress());
        model.setInterfaceName(UrlCaches.get(model.getProviderKey()).getUrl().getParameter(Constants.INTERFACE_KEY ));
        model.setMethodText(MethodCaches.get(model.getMethodKey()).getMethodText());
        model.setCaseId(counter.getAndAdd(1));
        model.setInsertTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        redisResolver.rPush(Const.DOE_CASE_KEY, model);

        // TODO
        // save to db.

        return ResultDTO.createSuccessResult("SUCCESS", model, CaseModel.class);
    }

    /**
     * list all case.
     *
     * @return
     */
    @Override
    public List<Object> listAll() {

        List<Object> list = redisResolver.lGet(Const.DOE_CASE_KEY, 0, -1);

        if (CollectionUtils.isEmpty(list)) {

            // TODO
            // get from db and put them to cache.

        }

        return list;
    }
}
