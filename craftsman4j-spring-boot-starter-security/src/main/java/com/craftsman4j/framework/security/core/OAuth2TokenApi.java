package com.craftsman4j.framework.security.core;

/**
 * 用户令牌服务
 *
 * @author zhougang
 * @since 2023/5/30 13:27
 */
public interface OAuth2TokenApi {

    /**
     * 根据令牌token, 获取登录用户
     *
     * @param token token
     * @return 登录用户
     */
    LoginUser getLoginUser(String token);
}
