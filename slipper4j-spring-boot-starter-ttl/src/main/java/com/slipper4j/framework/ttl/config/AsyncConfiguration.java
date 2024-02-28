package com.slipper4j.framework.ttl.config;


import cn.hutool.extra.spring.SpringUtil;
import com.slipper4j.framework.web.core.GlobalExceptionEvent;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 这是{@link ThreadPoolTaskExecutor}的一个简单替换，可搭配TransmittableThreadLocal实现父子线程之间的数据传递
 *
 * @author andanyoung
 */
public class AsyncConfiguration implements AsyncConfigurer {

    Executor executor;

    public AsyncConfiguration(Executor executor) {
        this.executor = executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            SpringUtil.getApplicationContext().publishEvent(new GlobalExceptionEvent("自定义线程异常", throwable));
        };
    }
}
