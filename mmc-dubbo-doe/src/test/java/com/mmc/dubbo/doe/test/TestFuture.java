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

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Joey
 * @date 2018/6/12 13:54
 */
public class TestFuture {

    @Test
    public void test() throws ExecutionException, InterruptedException {

        CompletableFuture<String>  future = new CompletableFuture<>();

        System.out.println("do something.");

        new Thread(() -> {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.complete("World");

        }).start();

        System.out.println(future.get());

        System.out.println("done.");
    }


}