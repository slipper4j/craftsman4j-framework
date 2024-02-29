package com.craftsman4j.framework.apilog.core.service;

import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * API 访问日志 Framework Service 实现类
 * <p>
 * 基于 {@link } 服务，记录访问日志
 *
 * @author craftsman4j
 */
@RequiredArgsConstructor
public class ApiAccessLogFrameworkServiceImpl implements ApiAccessLogFrameworkService {

    @Override
    @Async
    public void createApiAccessLog(ApiAccessLog apiAccessLog) {
        // 只负责发送事件，不负责消费事件，具体时间由使用者实现
        SpringUtil.getApplicationContext().publishEvent(apiAccessLog);
    }

}
