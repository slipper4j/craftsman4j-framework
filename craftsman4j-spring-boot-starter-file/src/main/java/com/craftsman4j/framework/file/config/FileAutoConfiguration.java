package com.craftsman4j.framework.file.config;

import com.craftsman4j.framework.file.core.biz.mapper.FileConfigMapper;
import com.craftsman4j.framework.file.core.biz.mapper.FileMapper;
import com.craftsman4j.framework.file.core.biz.service.FileConfigService;
import com.craftsman4j.framework.file.core.biz.service.FileConfigServiceImpl;
import com.craftsman4j.framework.file.core.biz.service.FileServiceImpl;
import com.craftsman4j.framework.file.core.client.FileClientFactory;
import com.craftsman4j.framework.file.core.client.FileClientFactoryImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.validation.Validator;

/**
 * 文件配置类
 *
 * @author craftsman4j
 */
@AutoConfiguration
@MapperScan(value = "com.craftsman4j.framework.file.core.biz.mapper")
public class FileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

    @Bean(name = "com.craftsman4j.framework.file.core.biz.service.FileConfigServiceImpl")
    public FileConfigServiceImpl fileConfigService(FileClientFactory fileClientFactory,
                                                   FileConfigMapper fileConfigMapper,
                                                   Validator validator) {
        return new FileConfigServiceImpl(fileClientFactory, fileConfigMapper, validator);
    }

    @Bean(name = "com.craftsman4j.framework.file.core.biz.service.FileServiceImpl")
    public FileServiceImpl fileService(FileConfigService fileConfigService,
                                       FileMapper fileMapper) {
        return new FileServiceImpl(fileConfigService, fileMapper);
    }
}
