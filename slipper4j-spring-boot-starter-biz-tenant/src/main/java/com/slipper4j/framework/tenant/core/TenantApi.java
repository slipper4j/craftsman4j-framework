package com.slipper4j.framework.tenant.core;

import java.util.List;

/**
 * 多租户的 API 接口
 *
 * @author slipper4j
 */
public interface TenantApi {

    /**
     * 获得所有租户
     *
     * @return 租户编号数组
     */
    List<Long> getTenantIdList();

    /**
     * 校验租户是否合法
     *
     * @param id 租户编号
     */
    void validateTenant(Long id);

}
