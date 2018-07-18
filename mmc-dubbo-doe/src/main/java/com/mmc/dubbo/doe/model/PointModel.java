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

import lombok.Data;

/**
 * ip and port.
 *
 * @author Joey
 * @date 2018/7/18 10:17
 */
@Data
public class PointModel {

    private String ip;
    private int port;

    public PointModel(String host, Integer port) {
        this.ip = host;
        this.port = port;
    }
}
