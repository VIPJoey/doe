/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.dubbo.doe.context;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author Joey
 * @date 2018/6/29 20:00
 */
@Slf4j
public class TaskContainer {

    // 获取当前的cpu核心数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // 线程池最大容量
    public static final int MAXIMUM_POOL_SIZE = CPU_COUNT;
    // 线程池核心容量
    private static final int CORE_POOL_SIZE = CPU_COUNT;
    // 线程池
    private final ThreadPoolExecutor poolExecutor;
    // 判断是否关闭
    protected volatile boolean isShutdown;
    // 任务计数器
    protected CountDownLatch watch;

    private TaskContainer() {

        // 创建任务池
        poolExecutor = new ThreadPoolExecutor(2, MAXIMUM_POOL_SIZE, 1,
                TimeUnit.HOURS, new ArrayBlockingQueue<Runnable>(CORE_POOL_SIZE), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    // 核心改造点，由blocking queue的offer改成put阻塞方法
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    log.error("任务进入队列出错：", e);
                }
            }
        });

    }

    public static TaskContainer getTaskContainer() {
        return TaskContainerHolder.instance;
    }

    /**
     * execute task.
     *
     * @param task
     */
    public void execute(Runnable task) {
        poolExecutor.execute(task);
    }

    static class TaskContainerHolder {
        static final TaskContainer instance = new TaskContainer();
    }
}
