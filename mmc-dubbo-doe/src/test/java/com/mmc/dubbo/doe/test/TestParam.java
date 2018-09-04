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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Joey
 * @date 2018/8/6 14:10
 */
public class TestParam {

    @Test
    public void testMethodPassValue() {


        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        new TestParam().print(list.toArray(new String[0]));

    }

    private void print(String... values) {

        Arrays.stream(values).forEach(System.out::println);
    }

}
