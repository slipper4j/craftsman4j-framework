package com.craftsman4j.framework.operatelog.core.service;

import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志 Framework Service 实现类
 * <p>
 * 基于 {@link ApplicationEventPublisher} 实现，记录操作日志
 *
 * @author craftsman4j
 */
@RequiredArgsConstructor
public class OperateLogFrameworkServiceImpl implements OperateLogFrameworkService {

    @Override
    @Async
    public void createOperateLog(OperateLog operateLog) {
        // 只负责发送事件，不负责消费事件，具体时间由使用者实现
        SpringUtil.getApplicationContext().publishEvent(operateLog);
    }

}
