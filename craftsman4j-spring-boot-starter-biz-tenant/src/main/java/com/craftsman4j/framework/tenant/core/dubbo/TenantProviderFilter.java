package com.craftsman4j.framework.tenant.core.dubbo;

import com.craftsman4j.framework.tenant.core.context.TenantContextHolder;
import org.apache.dubbo.rpc.*;

/**
 * dubbo提供者过滤器
 *
 * @author zhougang
 */
public class TenantProviderFilter implements Filter {

    public static final String TENANT_ID = "tenant-id";

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Long tenantId = (Long) RpcContext.getServiceContext().getObjectAttachment(TENANT_ID);
        if (tenantId != null) {
            TenantContextHolder.setTenantId(tenantId);
        }
        // 实际的rpc调用
        return invoker.invoke(invocation);
    }
}
