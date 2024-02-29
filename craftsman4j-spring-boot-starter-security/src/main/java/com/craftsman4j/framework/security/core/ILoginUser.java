package com.craftsman4j.framework.security.core;

import com.craftsman4j.framework.common.enums.UserTypeEnum;

import java.util.Set;

/**
 * LoginUserApi
 *
 * @author craftsman4j
 */
public interface ILoginUser {

    /**
     * 用户ID
     */
    Long getId();

    /**
     * 用户类型
     * <p>
     * 关联 {@link UserTypeEnum}
     */
    Integer getUserType();

    /**
     * 租户
     *
     * @return
     */
    default Long getTenantId() {
        return 0L;
    }

    /**
     * 权限列表
     */
    Set<String> getPermissions();

}
