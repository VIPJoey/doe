/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.cache;

import com.mmc.dubbo.doe.exception.DoeException;
import com.mmc.dubbo.doe.handler.CuratorHandler;
import com.mmc.dubbo.doe.util.StringUtil;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * cache all zk connection.
 *
 * @author Joey
 * @date 2018/6/18 20:12
 */
public class CuratorCaches {

    private final static Map<String, CuratorHandler> map = new ConcurrentHashMap<>();

    public static CuratorHandler getHandler(@NotNull String conn) throws NoSuchFieldException, IllegalAccessException {

        CuratorHandler client = map.get(conn);

        if (null == client) {


            try {
                // split host and port
                String[] pairs = conn.replace("：", ":").split(":");
                String host = pairs[0];
                String port = pairs[1];

                client = new CuratorHandler("zookeeper", host, Integer.valueOf(port));
                // connect to zk
                client.doConnect();
                // cache client for reuse
                if (client.isAvailable()) {
                    map.putIfAbsent(conn, client);
                }

            } catch(Exception e) {

                throw new DoeException(StringUtil.format("can't connect to {}, {}", conn, e.getMessage()));

            } finally {

                client.close();
            }
        }

        return client;
    }
}
