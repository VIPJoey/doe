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

import java.time.LocalTime;

/**
 * @author Joey
 * @date 2018/6/12 21:05
 */
public class TestTime {

    public static void main(String[] args) {

        System.out.println(isRestTime(LocalTime.now()));
        System.out.println(isRestTime(LocalTime.parse("23:00")));
        System.out.println(isRestTime(LocalTime.parse("01:00")));
        System.out.println(isRestTime(LocalTime.parse("07:00")));
        System.out.println(isRestTime(LocalTime.parse("09:00")));
        System.out.println(isRestTime(LocalTime.parse("12:00")));

    }

    private static boolean isRestTime(LocalTime now) {

        boolean open = true;
        boolean ret;

        LocalTime begin = LocalTime.parse("22:00"); // begin
        LocalTime end = LocalTime.parse("08:00"); // end

        if (begin.isBefore(end)) {
            // 开始时间小于结束时间
            ret = (begin.isBefore(now) && now.isBefore(end));

        } else {
            // 开始时间大于结束时间
            ret = (begin.isBefore(now) || now.isBefore(end));
        }

        return open && ret;

    }

}
