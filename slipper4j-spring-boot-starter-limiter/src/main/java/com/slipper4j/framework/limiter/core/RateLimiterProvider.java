package com.slipper4j.framework.limiter.core;

import com.slipper4j.framework.limiter.core.constant.LimiterType;

/**
 * @author andanyang
 * @since 2023/5/11 10:16
 */
public interface RateLimiterProvider extends RateLimiterApi {

    LimiterType support();
}
