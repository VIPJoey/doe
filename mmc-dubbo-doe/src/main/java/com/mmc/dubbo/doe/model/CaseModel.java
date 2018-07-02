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

import java.util.Date;
import java.util.Map;

/**
 * @author Joey
 * @date 2018/6/28 10:42
 */
@Data
public class CaseModel {

    /**
     * case Id.
     */
    private long caseId;
    /**
     * case group.
     */
    private String caseGroup;
    private String caseName;
    private String caseDesc;
    private String insertTime;
    /**
     * provider address.
     */
    private String address;
    private String interfaceName;
    /**
     * the method name with parameters.
     */
    private String methodText;
    private String providerKey;
    private String methodKey;
    /**
     * parameters.
     */
    private String json;
    /**
     * assert condition.
     */
    private String condition;
    /**
     * expected result.
     */
    private String expect;

}
