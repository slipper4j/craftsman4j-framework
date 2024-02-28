package com.slipper4j.framework.limiter.core.exception;

/**
 * @author andanyang
 * @since 2023/5/11 17:05
 */
public class RateLimiterException extends RuntimeException {

    public RateLimiterException() {

        super("Too Many Request");
    }
}
