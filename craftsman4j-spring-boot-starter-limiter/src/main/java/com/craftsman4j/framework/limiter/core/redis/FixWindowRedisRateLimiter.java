package com.craftsman4j.framework.limiter.core.redis;

import com.craftsman4j.framework.limiter.core.constant.LimiterType;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 固定窗口，限流
 *
 * @author zhougang
 * @since 2023/5/11 13:39
 */
@Configuration
public class FixWindowRedisRateLimiter extends AbstractApiRedisRateLimiter {


    public FixWindowRedisRateLimiter(StringRedisTemplate stringRedisTemplate) {
        super(stringRedisTemplate);
    }

    @Override
    public LimiterType support() {
        return LimiterType.FIX_WINDOW;
    }

    @Override
    protected String getScriptName() {
        return "rateLimiter/FixWindow.lua";
    }
}
