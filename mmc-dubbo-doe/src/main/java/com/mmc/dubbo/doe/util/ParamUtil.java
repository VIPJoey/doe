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

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.PojoUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mmc.dubbo.doe.exception.DoeException;
import com.mmc.dubbo.doe.model.PointModel;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Joey
 * @date 2018/6/13 19:22
 */
public class ParamUtil {

    public static HashMap<String,String> getAttachmentFromUrl(URL url) throws Exception {

        String interfaceName = url.getParameter(Constants.INTERFACE_KEY, "");
        if (StringUtils.isEmpty(interfaceName)) {
            throw new DoeException("找不到接口名称！");
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Constants.PATH_KEY, interfaceName);
        map.put(Constants.VERSION_KEY, url.getParameter(Constants.VERSION_KEY));
        map.put(Constants.GROUP_KEY, url.getParameter(Constants.GROUP_KEY));
        /**
         *  doesn't necessary to set these params.
         *
         map.put(Constants.SIDE_KEY, Constants.CONSUMER_SIDE);
         map.put(Constants.DUBBO_VERSION_KEY, Version.getVersion());
         map.put(Constants.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
         map.put(Constants.PID_KEY, String.valueOf(ConfigUtils.getPid()));
         map.put(Constants.METHODS_KEY, methodNames);
         map.put(Constants.INTERFACE_KEY, interfaceName);
         map.put(Constants.VERSION_KEY, "1.0"); // 不能设置这个，不然服务端找不到invoker
         */
        return map;
    }

    /**
     * prepare method parameters.
     *
     * @param jsonStr
     * @param invokeMethod
     * @return
     */
    public static Object[] parseJson(String jsonStr, Method invokeMethod) {

        jsonStr = jsonStr.trim();

        // we should convert to array model if more the one parameter prevent someone forgetting about it.
        String json;
        if (invokeMethod.getParameters().length > 0) {
            if (StringUtils.isEmpty(jsonStr)) {
                throw new DoeException("json parameter can't be blank.");
            }
            if (jsonStr.startsWith("[") && jsonStr.endsWith("]")) {
                json = jsonStr;
            } else {
                json = "[" + jsonStr + "]";
            }
        } else {
            json = jsonStr;
        }

        List<Object> list = JSON.parseArray(json, Object.class);
        Object[] array = PojoUtils.realize(list.toArray(), invokeMethod.getParameterTypes(), invokeMethod.getGenericParameterTypes());

        return array;
    }

    /**
     * parse ip and port from the conn.
     *
     * @param conn
     * @return
     */
    public static PointModel parsePointModel(@NotNull String conn) {

        // split host and port
        String[] pairs = conn.replace("：", ":").split(":");
        String host = pairs[0];
        String port = pairs[1];

        return new PointModel(host, Integer.valueOf(port));
    }
}
