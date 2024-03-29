package com.craftsman4j.framework.limiter.core;

import com.craftsman4j.framework.limiter.core.constant.LimiterType;

/**
 * @author zhougang
 * @since 2023/5/11 10:16
 */
public interface RateLimiterProvider extends RateLimiterApi {

    LimiterType support();
}
