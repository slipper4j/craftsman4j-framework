package com.slipper4j.framework.security.core.aop;

import com.slipper4j.framework.security.core.annotations.PreAuthenticated;
import com.slipper4j.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static com.slipper4j.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static com.slipper4j.framework.common.exception.util.ServiceExceptionUtils.exception;

@Aspect
@Slf4j
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated) throws Throwable {
        if (SecurityFrameworkUtils.getLoginUser() == null) {
            throw exception(UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }

}
