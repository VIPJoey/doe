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

import lombok.Data;

/**
 * @author Joey
 * @date 2018/6/18 19:10
 */
@Data
public class ConnectDTO extends BaseDTO {

    /**
     * ip and port.
     */
    private String conn;
    /**
     * interface name;
     */
    private String serviceName;
    /**
     * the provider cache key.
     */
    private String providerKey;
    /**
     * method key.
     */
    private String methodKey;
    /**
     * method name.
     */
    private String methodName;
    /**
     * method params.
     */
    private String json;
    /**
     * timeout of waiting for result.
     */
    private int timeout;
    /**
     * interface version number, eg: 1.0.0
     */
    private String version;
    /**
     * the group of interface, eg: mmcgroup
     */
    private String group;
}
