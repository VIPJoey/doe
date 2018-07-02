/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.model;

import com.alibaba.dubbo.common.URL;

/**
 * @author Joey
 * @date 2018/6/15 17:56
 */
public class UrlModel {

    private final String key;
    private final URL url;

    public UrlModel(String key, URL url) {
        this.key = key;
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public URL getUrl() {
        return url;
    }
}
