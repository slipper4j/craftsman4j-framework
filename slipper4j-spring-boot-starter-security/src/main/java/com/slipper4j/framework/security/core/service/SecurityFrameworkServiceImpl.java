package com.slipper4j.framework.security.core.service;

import cn.hutool.core.collection.CollUtil;
import com.slipper4j.framework.security.api.PermissionApi;
import com.slipper4j.framework.security.core.ILoginUser;
import com.slipper4j.framework.security.core.util.SecurityFrameworkUtils;
import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * 默认的 {@link SecurityFrameworkService} 实现类
 *
 * @author slipper4j
 */
@AllArgsConstructor
public class SecurityFrameworkServiceImpl implements SecurityFrameworkService {

    private final PermissionApi permissionApi;

    @Override
    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {
        ILoginUser user = SecurityFrameworkUtils.getLoginUser();
        if (user == null) {
            return false;
        }
        // ILoginUser有permissions则优先用permission匹配
        if (CollUtil.isNotEmpty(user.getPermissions())) {
            return CollUtil.containsAny(user.getPermissions(), Arrays.asList(permissions));
        }
        return permissionApi.hasAnyPermissions(user.getId(), permissions);
    }

    @Override
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    @Override
    public boolean hasAnyRoles(String... roles) {
        return permissionApi.hasAnyRoles(SecurityFrameworkUtils.getLoginUserId(), roles);
    }

}
