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

import java.io.Serializable;

/**
 * @author Joey
 * @date 2019/5/10 16:11
 */
public class GenericResp<T> implements Serializable {

    private static final long serialVersionUID = 6753766666093779059L;
    private T data;
    private String name;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
