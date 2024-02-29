package com.craftsman4j.framework.limiter.core;

import com.craftsman4j.framework.limiter.core.constant.LimiterType;

/**
 * @author zhougang
 * @since 2023/5/11 14:53
 */
public interface RateLimiterContext {


    RateLimiterApi getByLimiterType(LimiterType limiterType);
}
