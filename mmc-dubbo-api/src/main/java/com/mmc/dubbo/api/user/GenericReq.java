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
 * @date 2019/5/10 16:10
 */
public class GenericReq<T> implements Serializable {


    private static final long serialVersionUID = 3998577120137245599L;
    private String name;
    private T data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

