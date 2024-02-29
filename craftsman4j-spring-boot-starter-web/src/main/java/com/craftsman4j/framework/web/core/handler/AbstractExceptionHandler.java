package com.craftsman4j.framework.web.core.handler;

import cn.hutool.extra.spring.SpringUtil;

/**
 * @author craftsman4j
 * @since 2023/3/13 14:51
 */
public abstract class AbstractExceptionHandler {

    public void publishGlobalExceptionEvent(Throwable e) {
        SpringUtil.getApplicationContext().publishEvent(new GlobalExceptionEvent("GlobalException", e));
    }
}
