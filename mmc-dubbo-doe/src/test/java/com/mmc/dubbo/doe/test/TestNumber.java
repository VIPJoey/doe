/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.test;

import org.junit.Test;

/**
 * @author Joey
 * @date 2018/7/27 17:04
 */
public class TestNumber {

    @Test
    public void testPercent() {

        int a = 19;
        int b = 17;

        double c = ((double) a) * 100 / b;

        System.out.println(String.format("%.0f%s", c, "%"));
    }

}
