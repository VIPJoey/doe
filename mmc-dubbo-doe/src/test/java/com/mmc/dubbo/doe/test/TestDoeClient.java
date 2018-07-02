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

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.exchange.Request;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.dubbo.rpc.RpcResult;
import com.mmc.dubbo.api.user.UserFact;
import com.mmc.dubbo.api.user.UserService;
import com.mmc.dubbo.doe.context.ResponseDispatcher;
import com.mmc.dubbo.doe.client.DoeClient;
import com.mmc.dubbo.doe.util.ParamUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author Joey
 * @date 2018/6/6 20:18
 */
public class TestDoeClient {

    private URL url = URL.valueOf("dubbo://10.204.240.75:30880/com.mmc.dubbo.api.user.UserService?anyhost=true&application=dubboConsumer&async=false&check=false&codec=dubbo&dubbo=2.6.1&generic=false&heartbeat=60000&interface=com.mmc.dubbo.api.user.UserService&methods=getCurrentById&pid=23984&register.ip=10.204.240.75&remote.timestamp=1527252773377&revision=1.0-SNAPSHOT&side=consumer&timeout=30000&timestamp=1527254250379");
    private DoeClient client;

    @Before
    public void testConnect() {

        client = new DoeClient(url);
        client.doConnect();
    }

    @Test
    public void testSend() throws Exception {

        Class<?> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("getCurrentById", new Class[]{long.class});

        HashMap<String, String> map = ParamUtil.getAttachmentFromUrl(url);

        // create request.
        Request req = new Request();
        req.setVersion("2.0.0");
        req.setTwoWay(true);
        req.setData(new RpcInvocation(method, new Object[] {1111}, map));

        client.send(req);

        CompletableFuture<RpcResult> future = ResponseDispatcher.getDispatcher().getFuture(req);
        RpcResult result = future.get();
        UserFact fact = (UserFact) result.getValue();

        Assert.assertEquals("SUCCESS", fact.getName());

        System.out.println("done.");
    }

}
