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

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.exchange.Request;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.dubbo.rpc.RpcResult;
import com.mmc.dubbo.api.user.UserFact;
import com.mmc.dubbo.doe.client.DoeClient;
import com.mmc.dubbo.doe.context.ResponseDispatcher;
import com.mmc.dubbo.doe.dto.ConnectDTO;
import com.mmc.dubbo.doe.handler.CuratorHandler;
import com.mmc.dubbo.doe.model.UrlModel;
import com.mmc.dubbo.doe.util.ParamUtil;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Joey
 * @date 2018/6/15 13:45
 */
public class TestDemo {

    @Test
    public void testDemo() throws Exception {

        String protocol = "dubbo";
        String host = "127.0.0.1";
        int port = 2181;

        String interfaceName = "com.mmc.dubbo.api.user.UserService";
        String jsonStr = String.valueOf(8888);

        CuratorHandler curatorHandler = new CuratorHandler(protocol, host, port);
        curatorHandler.doConnect();
        ConnectDTO conn = new ConnectDTO();
        conn.setServiceName(interfaceName);
        List<UrlModel> urls = curatorHandler.getProviders(conn);

        URL url = urls.get(0).getUrl();
        url = url.addParameter(Constants.CODEC_KEY, protocol); // 非常重要，必须要设置编码器协议类型
        DoeClient client = new DoeClient(url);
        client.doConnect();

        HashMap<String, String> map = ParamUtil.getAttachmentFromUrl(url);
        Class<?> clazz = Class.forName(interfaceName);
        Method[] methods = clazz.getMethods();
        Method method = methods[0];
        Object[] params = ParamUtil.parseJson(jsonStr, method);

        // create request.
        Request req = new Request();
        req.setVersion("2.0.0");
        req.setTwoWay(true);
        req.setData(new RpcInvocation(method, params, map));

        client.send(req);

        CompletableFuture<RpcResult> future = ResponseDispatcher.getDispatcher().getFuture(req);
        RpcResult result = future.get(10, TimeUnit.SECONDS);
        UserFact fact = (UserFact) result.getValue();

        Assert.assertEquals("SUCCESS", fact.getName());

    }

}
