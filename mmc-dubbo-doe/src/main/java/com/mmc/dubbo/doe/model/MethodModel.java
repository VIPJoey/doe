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

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mmc.dubbo.doe.util.StringUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author Joey
 * @date 2018/6/15 14:54
 */
public class MethodModel {

    private final Method method;
    private final String key;

    public String getKey() {
        return key;
    }

    public Method getMethod() {
        return method;
    }

    public MethodModel(String key, Method method) {
        this.key = key;
        this.method = method;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(method.getName());
        sb.append("(");

        for (Parameter param : method.getParameters()) {
            sb.append(param.getType().getName());
            sb.append(" ");
            sb.append(param.getName());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");

        return sb.toString();

    }

    public String getMethodText() {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName());
        sb.append("(");

        for (Parameter param : method.getParameters()) {
            sb.append(getShortType(param.getType().getName()));
            sb.append(" ");
            sb.append(param.getName());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");

        return sb.toString();
    }

    private String getShortType(String name) {

        if (StringUtils.isEmpty(name)) {
            return name;
        }
        int index = name.lastIndexOf(".");
        if (index > 0 && index < name.length()) {
            name = name.substring(index + 1);
        }
        return name;
    }

}
