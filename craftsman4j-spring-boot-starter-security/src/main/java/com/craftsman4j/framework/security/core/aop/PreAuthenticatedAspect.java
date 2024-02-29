package com.craftsman4j.framework.security.core.aop;

import com.craftsman4j.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.craftsman4j.framework.common.exception.util.ServiceExceptionUtils;
import com.craftsman4j.framework.security.core.util.SecurityFrameworkUtils;
import com.craftsman4j.framework.security.core.annotations.PreAuthenticated;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static com.craftsman4j.framework.common.exception.util.ServiceExceptionUtils.exception;

@Aspect
@Slf4j
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated) throws Throwable {
        if (SecurityFrameworkUtils.getLoginUser() == null) {
            throw ServiceExceptionUtils.exception(GlobalErrorCodeConstants.UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }

}
