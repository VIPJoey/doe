/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.util;

import com.mmc.dubbo.doe.exception.DoeException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author Joey
 * @date 2018/11/23 17:28
 */
@Slf4j
public class FileUtil {

    public static String readToString(String fileName) throws DoeException {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            int read = in.read(filecontent);
            in.close();
            log.info("read:{} filelength:{}", read, filelength);
        } catch (IOException e) {
            throw new DoeException(StringUtil.format("can't load the file content, because {}.", e.getMessage()));
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new DoeException(StringUtil.format("can't load the file content, because {}.", e.getMessage()));
        }
    }

    public static void WriteStringToFile(String fileName, String text) {
        try {
            try (PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile())) {
                out.print(text);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
