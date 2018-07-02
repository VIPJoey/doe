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
 * @date 2018/6/17 10:03
 */
@Data
public class PomDTO extends BaseDTO {

    /**
     * the pom xml content.
     */
    private String pom;

    /**
     * the path of pom file.
     */
    private String path;

}
