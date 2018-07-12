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

import org.apache.commons.net.telnet.TelnetClient;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;

/**
 * @author Joey
 * @date 2018/7/5 14:57
 */
public class TestTelnetService {

    @Test
    public void testConnect() {

        TelnetClient telnetClient = null;
        try {
            telnetClient = new TelnetClient("vt200");  // 指明Telnet终端类型，否则会返回来的数据中文会乱码
            telnetClient.setDefaultTimeout(5000); // socket延迟时间：5000ms
            telnetClient.connect("127.0.0.1", 20880);  // 建立一个连接,默认端口是23
            InputStream inputStream = telnetClient.getInputStream(); // 读取命令的流
            PrintStream pStream = new PrintStream(telnetClient.getOutputStream());  // 写命令的流

            pStream.println("\r\n");
            pStream.println("invoke com.fcbox.edms.terminal.api.CabinetServiceFacade.getCabinetInfo({\"cabinetCode\": \"FC0231103\"})\n");
            pStream.flush();

            byte[] b = new byte[1024 * 10];
            int size;
            StringBuffer sBuffer = new StringBuffer(300);

            size = inputStream.read(b);
            if (-1 != size) {
                sBuffer.append(new String(b, 0, size, "gbk"));
            }
            System.out.println(sBuffer.toString());

            pStream.println("exit"); // 写命令
            pStream.flush(); // 将命令发送到telnet Server
            pStream.close();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {


        }

    }

}
