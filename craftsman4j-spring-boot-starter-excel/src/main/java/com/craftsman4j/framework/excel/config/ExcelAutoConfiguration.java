package com.craftsman4j.framework.excel.config;

import com.alibaba.excel.read.listener.ReadListener;
import com.craftsman4j.framework.excel.core.ExcelWriteLifecycle;
import com.craftsman4j.framework.excel.core.util.ExcelUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.util.List;

/**
 * @author zhougang
 * @since 2023/7/24 15:40
 */
@AutoConfiguration
public class ExcelAutoConfiguration implements InitializingBean {

    @Autowired(required = false)
    private ExcelWriteLifecycle excelWriteLifecycle;

    @Autowired(required = false)
    private List<ReadListener<Object>> readListeners;

    @Override
    public void afterPropertiesSet() throws Exception {
        ExcelUtils.init(excelWriteLifecycle, readListeners);
    }

}
