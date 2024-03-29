package com.craftsman4j.framework.web.core.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全局错误事件
 *
 * @author zhougang
 * @since 2020/5/12 17:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalExceptionEvent {

    /**
     * 异常类型
     */
    private String name;

    private Throwable e;


    public GlobalExceptionEvent(Exception e) {
        this.e = e;
    }
}
