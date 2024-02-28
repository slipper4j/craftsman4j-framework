package com.slipper4j.framework.limiter.core.redis;

import com.slipper4j.framework.limiter.core.constant.LimiterType;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 固定窗口，限流
 *
 * @author andanyang
 * @since 2023/5/11 13:39
 */
public class SlidingLogRedisRateLimiter extends AbstractApiRedisRateLimiter {


    public SlidingLogRedisRateLimiter(StringRedisTemplate stringRedisTemplate) {
        super(stringRedisTemplate);
    }

    @Override
    public LimiterType support() {
        return LimiterType.SLIDING_LOG;
    }

    @Override
    protected String getScriptName() {
        return "rateLimiter/SlidingLog.lua";
    }
}
