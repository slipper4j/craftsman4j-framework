package com.craftsman4j.framework.websocket.core.security;

import com.craftsman4j.framework.security.core.ILoginUser;
import com.craftsman4j.framework.security.core.filter.DefaultTokenAuthenticationFilter;
import com.craftsman4j.framework.security.core.util.SecurityFrameworkUtils;
import com.craftsman4j.framework.websocket.core.util.WebSocketFrameworkUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * 登录用户的 {@link HandshakeInterceptor} 实现类
 *
 * 流程如下：
 * 1. 前端连接 websocket 时，会通过拼接 ?token={token} 到 ws:// 连接后，这样它可以被 {@link DefaultTokenAuthenticationFilter} 所认证通过
 * 2. {@link LoginUserHandshakeInterceptor} 负责把 {@link ILoginUser} 添加到 {@link WebSocketSession} 中
 *
 * @author craftsman4j
 */
public class LoginUserHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        ILoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser != null) {
            WebSocketFrameworkUtils.setLoginUser(loginUser, attributes);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // do nothing
    }

}
