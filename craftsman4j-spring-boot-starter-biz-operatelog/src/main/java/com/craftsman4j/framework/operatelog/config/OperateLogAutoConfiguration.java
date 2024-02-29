package com.craftsman4j.framework.operatelog.config;

import com.craftsman4j.framework.operatelog.core.service.OperateLogFrameworkService;
import com.craftsman4j.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import com.craftsman4j.framework.operatelog.core.aop.OperateLogAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class OperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService() {
        return new OperateLogFrameworkServiceImpl();
    }

}
