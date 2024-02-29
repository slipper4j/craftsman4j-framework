package com.craftsman4j.framework.limiter.core.util;

import com.craftsman4j.framework.limiter.core.RateLimiterContext;
import com.craftsman4j.framework.limiter.core.RateLimiterApi;
import com.craftsman4j.framework.limiter.core.constant.LimiterType;
import lombok.Setter;

import java.util.function.Supplier;

/**
 * 令牌桶算法限速工具类
 *
 * @author zhougang
 * @since 2023/5/15 9:51
 */
public class RateLimiterUtils {

    @Setter
    private static RateLimiterContext rateLimiterContext;

    /**
     * 限速工具类方法
     *
     * @param key      业务唯一key
     * @param capacity 最大容量
     * @param interval 时间窗口，单位 秒。 interval 秒 可以处理，capacity个请求
     * @return
     */
    public static boolean rateLimiter(LimiterType limiterType, String key, int capacity, int interval) {
        RateLimiterApi limiterApi = rateLimiterContext.getByLimiterType(limiterType);
        return limiterApi.tryAcquire(key, capacity, interval);
    }

    public static void rateLimiter(LimiterType limiterType, String key, int maxAttempts, int interval, Runnable runnable) {
        RateLimiterApi limiterApi = rateLimiterContext.getByLimiterType(limiterType);
        if (limiterApi.tryAcquire(key, maxAttempts, interval)) {
            runnable.run();
        }
    }

    public static <T> T rateLimiter(LimiterType limiterType, String key, int maxAttempts, int interval, Supplier<T> supplier) {
        RateLimiterApi limiterApi = rateLimiterContext.getByLimiterType(limiterType);
        if (limiterApi.tryAcquire(key, maxAttempts, interval)) {
            return supplier.get();
        }

        return null;
    }
}
