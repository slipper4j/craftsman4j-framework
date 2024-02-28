package com.slipper4j.framework.signature.core.interceptor;

import cn.hutool.extra.spring.SpringUtil;
import com.slipper4j.framework.common.exception.ServiceException;
import com.slipper4j.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.slipper4j.framework.signature.core.SignatureStrategy;
import com.slipper4j.framework.signature.core.annotations.Signature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhougang
 * @since 2023/11/10 9:51
 */
@Slf4j
public class SignatureInterceptor implements HandlerInterceptor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public SignatureInterceptor() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Signature signature = handlerMethod.getMethodAnnotation(Signature.class);

        if (signature == null) {
            Class<?> beanType = handlerMethod.getBeanType();
            Signature annotation = beanType.getAnnotation(Signature.class);
            if (annotation == null) {
                return true;
            }
            signature = annotation;
        }

        if (!signature.enable()) {
            return true;
        }

        SignatureStrategy signatureStrategy = SpringUtil.getBean(signature.signatureClass());
        if (!signatureStrategy.verify(signature, request)) {
            log.info("[URI({}) 签名失败]", request.getRequestURI());
            throw new ServiceException(GlobalErrorCodeConstants.SIGNATURE_REQUEST);
        }
        return true;
    }
}
