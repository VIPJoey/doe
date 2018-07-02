/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.cache;

import com.mmc.dubbo.doe.model.MethodModel;
import com.mmc.dubbo.doe.dto.MethodModelDTO;
import com.mmc.dubbo.doe.util.MD5Util;
import com.mmc.dubbo.doe.util.StringUtil;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Joey
 * @date 2018/6/15 14:55
 */
public class MethodCaches {

    private final static Map<String, MethodModel> map = new ConcurrentHashMap<>();

    /**
     * cache the method object so we can get them next time quickly.
     *
     * @param interfaceName
     * @param methods
     * @return
     */
    public static List<MethodModelDTO> cache(final String interfaceName, Method[] methods) {

        List<MethodModelDTO> ret = new ArrayList<>();

        Arrays.stream(methods).forEach(m -> {

            String key = generateMethodKey(m, interfaceName);

            MethodModel model = new MethodModel(key, m);

            ret.add(new MethodModelDTO(model));

            map.putIfAbsent(key, model); // add to cache

        });


        return ret;
    }

    private static String generateMethodKey(Method method, String interfaceName) {
        return StringUtil.format("{}#{}", interfaceName, MD5Util.encrypt(method.toGenericString()));
    }

    public static MethodModel get(@NotNull String key) {
        return map.get(key);
    }
}
