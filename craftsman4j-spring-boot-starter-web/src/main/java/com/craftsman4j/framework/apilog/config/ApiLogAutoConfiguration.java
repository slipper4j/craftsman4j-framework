package com.craftsman4j.framework.apilog.config;

import com.craftsman4j.framework.apilog.core.filter.ApiAccessLogFilter;
import com.craftsman4j.framework.apilog.core.service.ApiAccessLogFrameworkService;
import com.craftsman4j.framework.apilog.core.service.ApiAccessLogFrameworkServiceImpl;
import com.craftsman4j.framework.apilog.core.service.ApiErrorLogFrameworkService;
import com.craftsman4j.framework.apilog.core.service.ApiErrorLogFrameworkServiceImpl;
import com.craftsman4j.framework.common.enums.WebFilterOrderEnum;
import com.craftsman4j.framework.web.config.WebAutoConfiguration;
import com.craftsman4j.framework.web.config.WebProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

@AutoConfiguration(after = WebAutoConfiguration.class)
public class ApiLogAutoConfiguration {

    @Bean
    public ApiAccessLogFrameworkService apiAccessLogFrameworkService() {
        return new ApiAccessLogFrameworkServiceImpl();
    }

    @Bean
    public ApiErrorLogFrameworkService apiErrorLogFrameworkService() {
        return new ApiErrorLogFrameworkServiceImpl();
    }

    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
    @Bean
    @ConditionalOnProperty(prefix = "craftsman4j.access-log", value = "enable", matchIfMissing = true)
    // 允许使用 craftsman4j.access-log.enable=false 禁用访问日志
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName,
                                                                         ApiAccessLogFrameworkService apiAccessLogFrameworkService) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogFrameworkService);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

}
