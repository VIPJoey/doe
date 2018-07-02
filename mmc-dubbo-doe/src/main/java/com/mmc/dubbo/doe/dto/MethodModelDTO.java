/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.dto;

import com.mmc.dubbo.doe.model.MethodModel;
import lombok.Data;

/**
 * @author Joey
 * @date 2018/6/18 21:49
 */
@Data
public class MethodModelDTO {

    /**
     * the name of interface which the method belong to.
     */
    private String interfaceName;
    /**
     * the cache key.
     */
    private String methodKey;
    /**
     * just only the method name.
     */
    private String methodName;
    /**
     * show on the web.
     */
    private String methodText;

    public MethodModelDTO() {

    }

    public MethodModelDTO(MethodModel model) {

        this.methodKey = model.getKey();
        this.methodName = model.getMethod().getName();
        this.methodText = model.getMethodText();
    }
}
