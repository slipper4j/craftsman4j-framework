package com.slipper4j.framework.apilog.core.service;

import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * API 错误日志 Framework Service 实现类
 * <p>
 * 基于 {@link } 服务，记录错误日志
 *
 * @author slipper4j
 */
@RequiredArgsConstructor
public class ApiErrorLogFrameworkServiceImpl implements ApiErrorLogFrameworkService {

    @Override
    @Async
    public void createApiErrorLog(ApiErrorLog apiErrorLog) {
        // 只负责发送事件，不负责消费事件，具体时间由使用者实现
        SpringUtil.getApplicationContext().publishEvent(apiErrorLog);
    }

}
