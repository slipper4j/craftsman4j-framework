package com.slipper4j.framework.file.config;

import com.slipper4j.framework.file.core.client.FileClientFactory;
import com.slipper4j.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件配置类
 *
 * @author 芋道源码
 */
@AutoConfiguration
public class FileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
