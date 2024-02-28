package com.slipper4j.framework.limiter.key;

import com.slipper4j.framework.limiter.core.RateLimiterKeyGenerate;
import com.slipper4j.framework.web.core.util.WebFrameworkUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author andanyang
 * @since 2023/5/12 10:04
 */
@RequiredArgsConstructor
public class UserRateLimiterKeyGenerate implements RateLimiterKeyGenerate {

    @Override
    public void generateKey(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, StringBuilder keyBuilder) {

        keyBuilder.append(request.getRequestURI())
                .append(":U")
                .append(WebFrameworkUtils.getLoginUserId());
    }
}
