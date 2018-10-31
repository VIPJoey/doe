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

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.exchange.Request;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mmc.dubbo.doe.cache.CuratorCaches;
import com.mmc.dubbo.doe.cache.MethodCaches;
import com.mmc.dubbo.doe.cache.UrlCaches;
import com.mmc.dubbo.doe.client.DoeClient;
import com.mmc.dubbo.doe.context.ResponseDispatcher;
import com.mmc.dubbo.doe.dto.ConnectDTO;
import com.mmc.dubbo.doe.dto.ResultDTO;
import com.mmc.dubbo.doe.dto.UrlModelDTO;
import com.mmc.dubbo.doe.exception.DoeException;
import com.mmc.dubbo.doe.handler.CuratorHandler;
import com.mmc.dubbo.doe.model.MethodModel;
import com.mmc.dubbo.doe.model.ServiceModel;
import com.mmc.dubbo.doe.model.UrlModel;
import com.mmc.dubbo.doe.service.ConnectService;
import com.mmc.dubbo.doe.util.ParamUtil;
import com.mmc.dubbo.doe.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Joey
 * @date 2018/6/18 17:10
 */
@Service("connectService")
@Slf4j
public class ConnectServiceImpl implements ConnectService {

    @Override
    public ResultDTO<String> send(@NotNull ConnectDTO dto) throws Exception {

        log.info("begin to send {} .", JSON.toJSONString(dto));

        // get provider url
        URL url = UrlCaches.get(dto.getProviderKey()).getUrl();
        // get method
        MethodModel methodModel = MethodCaches.get(dto.getMethodKey());
        // parse parameter
        Object[] params = ParamUtil.parseJson(dto.getJson(), methodModel.getMethod());


        url = url.addParameter(Constants.CODEC_KEY, "dubbo"); // 非常重要，必须要设置编码器协议类型
        DoeClient client = new DoeClient(url);
        client.doConnect();

        // set the path variables
        Map<String, String> map = ParamUtil.getAttachmentFromUrl(url);

        // create request.
        Request req = new Request();
        req.setVersion("2.0.0");
        req.setTwoWay(true);
        req.setData(new RpcInvocation(methodModel.getMethod(), params, map));

        client.send(req);

        int timeout = (0 == dto.getTimeout()) ? 10 : dto.getTimeout(); // send timeout
        CompletableFuture<RpcResult> future = ResponseDispatcher.getDispatcher().getFuture(req);
        RpcResult result = future.get(timeout, TimeUnit.SECONDS);
        ResponseDispatcher.getDispatcher().removeFuture(req);

        return ResultDTO.createSuccessResult("SUCCESS",
                JSON.toJSONString(result.getValue(), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat),
                String.class);
    }

    @Override
    public List<UrlModelDTO> listProviders(@NotNull ConnectDTO connect) throws NoSuchFieldException, IllegalAccessException {

        // get client
        CuratorHandler client = CuratorCaches.getHandler(connect.getConn());

        if (null == client) {
            throw new DoeException("the cache is validate, please reconnect to zk againt.");
        }

        List<UrlModel> providers = client.getProviders(connect);

        // throw fast json error if you don't convert simple pojo
        // I have no idea why the UrlModel object will throw stack over flow exception.
        List<UrlModelDTO> ret = new ArrayList<>();
        providers.forEach(p -> {
            UrlModelDTO m = new UrlModelDTO();
            m.setKey(p.getKey());
            m.setHost(p.getUrl().getHost());
            m.setPort(p.getUrl().getPort());

            ret.add(m);
        });

        return ret;

    }

    /**
     * connect to zk and get all providers.
     *
     * @param conn
     * @return
     */
    @Override
    public List<ServiceModel> connect(@NotNull String conn) throws NoSuchFieldException, IllegalAccessException {

        // get client
        CuratorHandler client = CuratorCaches.getHandler(conn);

        if (!client.isAvailable()) {
            throw new DoeException(StringUtil.format("can't connect to {}", conn));
        }

        // get providers
        List<ServiceModel> list = client.getInterfaces();


        return list;
    }
}
