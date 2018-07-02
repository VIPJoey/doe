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

import com.alibaba.dubbo.common.URL;
import com.mmc.dubbo.doe.model.UrlModel;
import com.mmc.dubbo.doe.util.StringUtil;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Joey
 * @date 2018/6/15 17:59
 */
public class UrlCaches {

    private final static Map<String, UrlModel> map = new ConcurrentHashMap<>();

    /**
     * cache all providers by unique key.
     *
     * @param interfaceName
     * @param urls
     * @return
     */
    public static List<UrlModel> cache(String interfaceName, List<URL> urls) {

        List<UrlModel> ret = new ArrayList<>();

        for (int i = 0; i < urls.size(); i++) {

            URL url = urls.get(i);
            String key = generateUrlKey(interfaceName, url.getHost(), url.getPort());
            UrlModel model = new UrlModel(key, url);
            ret.add(model);

            map.put(model.getKey(), model); // 存入缓存
        }

        return ret;
    }

    private static String generateUrlKey(String interfaceName, String host, int port) {
        return StringUtil.format("{}#{}#{}#", interfaceName, host, port);
    }

    public static UrlModel get(@NotNull String key) {
        return map.get(key);
    }
}
