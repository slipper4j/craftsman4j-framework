package com.slipper4j.framework.security.api;

import com.slipper4j.framework.security.core.ILoginUser;

/**
 * 用户令牌服务
 *
 * @author andanyang
 * @since 2023/5/30 13:27
 */
public interface UserTokenApi {

    /**
     * 根据令牌token, 获取登录用户
     *
     * @param token token
     * @return 登录用户
     */
    ILoginUser getLoginUser(String token);
}
