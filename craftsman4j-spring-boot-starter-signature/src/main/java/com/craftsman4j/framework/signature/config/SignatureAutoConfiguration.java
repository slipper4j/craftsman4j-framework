package com.craftsman4j.framework.signature.config;

import com.craftsman4j.framework.common.enums.WebFilterOrderEnum;
import com.craftsman4j.framework.signature.core.SignatureApi;
import com.craftsman4j.framework.signature.core.filter.SignatureCacheRequestBodyFilter;
import com.craftsman4j.framework.signature.core.interceptor.SignatureInterceptor;
import com.craftsman4j.framework.signature.core.properties.SignatureProperties;
import com.craftsman4j.framework.signature.core.service.DefaultSignatureStrategy;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author zhougang
 * @since 2023/11/10 15:10
 */
@AutoConfiguration
@ConditionalOnClass(name = {"org.springframework.web.servlet.HandlerInterceptor"})
@EnableConfigurationProperties(SignatureProperties.class)
@ConditionalOnProperty(prefix = "taste.signature", name = "enable", matchIfMissing = true)
public class SignatureAutoConfiguration {

    @Bean
    public SignatureInterceptor signatureInterceptor() {
        return new SignatureInterceptor();
    }

    @Bean
    public SignatureWebMvcConfigurer signatureWebMvcConfigurer(SignatureInterceptor signatureInterceptor) {
        return new SignatureWebMvcConfigurer(signatureInterceptor);
    }

    @Bean
    public DefaultSignatureStrategy defaultSignatureStrategy(StringRedisTemplate stringRedisTemplate,
                                                             SignatureProperties signatureProperties,
                                                             SignatureApi signatureApi) {
        return new DefaultSignatureStrategy(stringRedisTemplate, signatureProperties, signatureApi);
    }

    /**
     * 创建 RequestBodyCacheFilter Bean，可重复读取请求内容
     */
    @Bean
    @ConditionalOnMissingClass("com.craftsman4j.framework.web.core.filter.CacheRequestBodyFilter")
    public FilterRegistrationBean<SignatureCacheRequestBodyFilter> signatureCacheRequestBodyFilter() {
        FilterRegistrationBean<SignatureCacheRequestBodyFilter> bean = new FilterRegistrationBean<>(new SignatureCacheRequestBodyFilter());
        bean.setOrder(WebFilterOrderEnum.REQUEST_BODY_CACHE_FILTER + 1);
        return bean;
    }

}
