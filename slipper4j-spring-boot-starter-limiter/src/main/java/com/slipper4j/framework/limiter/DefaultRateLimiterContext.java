package com.slipper4j.framework.limiter;

import com.slipper4j.framework.limiter.core.RateLimiterContext;
import com.slipper4j.framework.limiter.core.RateLimiterProvider;
import com.slipper4j.framework.limiter.core.RateLimiterApi;
import com.slipper4j.framework.limiter.core.constant.LimiterType;
import com.slipper4j.framework.limiter.core.util.RateLimiterUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author andanyang
 * @since 2023/5/11 14:53
 */
public class DefaultRateLimiterContext implements ApplicationContextAware, InitializingBean, RateLimiterContext {

    private ApplicationContext applicationContext;

    private final Map<String, RateLimiterApi> limiterServiceMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {

        applicationContext.getBeansOfType(RateLimiterProvider.class).values().forEach(i -> {

            limiterServiceMap.put(i.support().name(), i);
        });


        RateLimiterUtils.setRateLimiterContext(this);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public RateLimiterApi getByLimiterType(LimiterType limiterType) {
        return limiterServiceMap.get(limiterType.name());
    }
}
