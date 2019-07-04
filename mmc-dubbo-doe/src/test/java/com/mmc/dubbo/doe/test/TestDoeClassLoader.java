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

import com.mmc.dubbo.doe.context.DoeClassLoader;
import org.junit.Test;

/**
 * @author Joey
 * @date 2019/6/28 14:54
 */
public class TestDoeClassLoader {

    @Test
    public void testLoad() throws Exception {

        String path = "F:\\app\\doe\\lib";

        DoeClassLoader doeClassLoader = new DoeClassLoader(path);

        doeClassLoader.loadJars();
        doeClassLoader.loadClassFile();
        Class<?> clazz = doeClassLoader.loadClass("com.fcbox.edms.terminal.api.CabinetServiceFacade");


        System.out.println(clazz.getClassLoader());

    }

}
