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

import com.alibaba.dubbo.common.utils.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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

        System.out.println("----------------------------");

        System.out.println(isMoreThanDiffDay("", "", 31));
        System.out.println(isMoreThanDiffDay("2018-07-25", "2018-07-25", 31));
        System.out.println(isMoreThanDiffDay("2018-08-27", "2018-07-25", 31));
        System.out.println(isMoreThanDiffDay("2018-07-10", "2018-07-25", 31));
        System.out.println(isMoreThanDiffDay("2018-07-10", "2018-08-10", 31));
        System.out.println(isMoreThanDiffDay("2018-07-10", "2018-08-11", 31));
        System.out.println(isMoreThanDiffDay("2018-01-25", "2018-07-25", 31));
        System.out.println(isMoreThanDiffDay("2018-07-10", "2018-08-25", 31));
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

    /**
     * 判断两日期字符串是否超过指定天数.
     * @param tmStartStr 开始日期
     * @param tmEndStr 结束日期
     * @param days 指定天数
     * @return 如果传入参数不为空且超过指定天数返回true，否则返回false
     */
    public static boolean isMoreThanDiffDay(String tmStartStr, String tmEndStr, int days) {
        if (days <= 0) {
            return false;
        }
        if (StringUtils.isEmpty(tmEndStr) || StringUtils.isEmpty(tmEndStr)) {
            return false;
        }
        LocalDate startDate = LocalDate.parse(tmStartStr);
        LocalDate endDate = LocalDate.parse(tmEndStr);

        return startDate.until(endDate, ChronoUnit.DAYS) > days;
    }

}
