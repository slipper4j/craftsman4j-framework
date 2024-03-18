package com.craftsman4j.framework.limiter.core;

import com.craftsman4j.framework.limiter.core.constant.LimiterType;
import com.craftsman4j.framework.limiter.core.util.RateLimiterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhougang
 * @since 2023/5/11 14:53
 */
@Slf4j
public class DefaultRateLimiterContext implements ApplicationContextAware, InitializingBean, RateLimiterContext {

    private ApplicationContext applicationContext;

    private final Map<String, RateLimiterApi> limiterServiceMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {

        applicationContext.getBeansOfType(RateLimiterProvider.class).values().forEach(i -> {

            limiterServiceMap.put(i.support().name(), i);
        });


        RateLimiterUtils.setRateLimiterContext(this);
        log.info("[init][初始化 RateLimiterUtils 成功]");
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
