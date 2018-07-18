/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mmc.dubbo.doe.dto.ConnectDTO;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.model.PointModel;
import com.mmc.dubbo.doe.service.TelnetService;
import com.mmc.dubbo.doe.util.ParamUtil;
import com.mmc.dubbo.doe.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author Joey
 * @date 2018/7/17 19:43
 */
@Slf4j
@Service("telnetService")
public class TelnetServiceImpl implements TelnetService {

    /**
     * send message with telnet client.
     *
     * @param dto
     * @return
     */
    @Override
    public ResultDTO<String> send(@NotNull ConnectDTO dto) {

        PointModel model = ParamUtil.parsePointModel(dto.getConn());

        TelnetClient telnetClient = null;
        try {
            telnetClient = new TelnetClient("VT220");  // 指明Telnet终端类型，否则会返回来的数据中文会乱码
            telnetClient.setDefaultTimeout(dto.getTimeout() <= 0 ? 5000 : dto.getTimeout()); // socket延迟时间：5000ms
            telnetClient.connect(model.getIp(), model.getPort());  // 建立一个连接,默认端口是23
            InputStream in = telnetClient.getInputStream(); // 读取命令的流
            PrintStream out = new PrintStream(telnetClient.getOutputStream());  // 写命令的流

            String command = makeCommand(dto.getServiceName(), dto.getMethodName(), dto.getJson());
            log.info("send: {}", command);

            out.println("\r\n");
            out.println(command);
            out.println("\r\n");
            out.flush();

            // handle inputStream
            StringBuilder sb = new StringBuilder();
            BufferedInputStream bi = new BufferedInputStream(in);

            while (true) {
                byte[] buffer = new byte[1024];
                int len = bi.read(buffer);
                if (len <= -1) {
                    break;
                }

                String msg = new String(buffer, 0, len, "GBK");
                sb.append(msg);
                if (msg.endsWith("dubbo>")) {
                    break;
                }
            }

            out.println("exit"); // 写命令
            out.flush(); // 将命令发送到telnet Server
            telnetClient.disconnect();

            String ret = sb.toString();
            String lineSeparator = System.getProperty("line.separator", "\n");
            if (StringUtils.isNotEmpty(ret)) {
                ret = ret.split(lineSeparator)[0];
            }
            log.info("receive: {}", ret);

            // format the json string
            String result = JSON.toJSONString(JSON.parse(ret), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
            return ResultDTO.createSuccessResult("SUCCESS", result, String.class);

        } catch (Exception e) {

            log.error("occur an error when sending message with telnet client.", e);
            return ResultDTO.createExceptionResult(e, String.class);

        } finally {
            try {
                if (null != telnetClient) {
                    telnetClient.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String makeCommand(String serviceName, String methodName, String json) {

        return StringUtil.format("invoke {}.{}({})", serviceName, methodName, json);
    }
}
