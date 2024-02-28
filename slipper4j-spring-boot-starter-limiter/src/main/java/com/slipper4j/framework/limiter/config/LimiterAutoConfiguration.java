package com.slipper4j.framework.limiter.config;

import com.slipper4j.framework.limiter.DefaultRateLimiterContext;
import com.slipper4j.framework.limiter.core.RateLimiterContext;
import com.slipper4j.framework.limiter.core.RateLimiterKeyGenerate;
import com.slipper4j.framework.limiter.core.interceptor.RateLimitInterceptor;
import com.slipper4j.framework.limiter.core.key.DefaultRateLimiterKeyGenerate;
import com.slipper4j.framework.limiter.core.key.TenantRateLimiterKeyGenerate;
import com.slipper4j.framework.limiter.core.key.UserRateLimiterKeyGenerate;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author andanyang
 * @since 2023/5/11 15:10
 */
@Configuration
@ConditionalOnClass(name = {"org.springframework.web.servlet.HandlerInterceptor"})
public class LimiterAutoConfiguration implements ApplicationContextAware {

    @Bean
    public RateLimitInterceptor rateLimitInterceptor(RateLimiterContext rateLimiterContext) {
        return new RateLimitInterceptor(rateLimiterContext);
    }

    @Bean
    public RateLimiterContext rateLimiterContext() {
        return new DefaultRateLimiterContext();
    }

    @Bean
    public LimiterWebMvcConfigurer limiterWebMvcConfigurer(RateLimitInterceptor rateLimitInterceptor) {
        return new LimiterWebMvcConfigurer(rateLimitInterceptor);
    }

    @Bean
    @Primary
    public RateLimiterKeyGenerate rateLimiterKeyGenerate() {
        return new DefaultRateLimiterKeyGenerate();
    }

    @Bean
    @ConditionalOnClass(name = "com.slipper4j.framework.security.core.ILoginUser")
    public RateLimiterKeyGenerate userRateLimiterKeyGenerate() {
        return new UserRateLimiterKeyGenerate();
    }

    @Bean
    @ConditionalOnClass(name = "com.slipper4j.framework.tenant.api.TenantApi")
    public RateLimiterKeyGenerate tenantRateLimiterKeyGenerate() {
        return new TenantRateLimiterKeyGenerate();
    }

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //@PostConstruct
    //public void afterPropertiesSet() throws Exception {
    //    RateLimiterContext bean = applicationContext.getBean(RateLimiterContext.class);
    //    RateLimiterUtil.setRateLimiterContext(bean);
    //}
}
