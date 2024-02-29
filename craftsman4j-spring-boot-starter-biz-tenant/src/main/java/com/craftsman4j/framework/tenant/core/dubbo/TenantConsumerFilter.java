package com.craftsman4j.framework.tenant.core.dubbo;

import com.craftsman4j.framework.tenant.core.context.TenantContextHolder;
import org.apache.dubbo.rpc.*;

/**
 * dubbo消费者过滤器
 *
 * @author zhougang
 */
public class TenantConsumerFilter implements Filter {

    public static final String TENANT_ID = "tenant-id";

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (TenantContextHolder.getTenantId() != null) {
            RpcContext.getServiceContext().setObjectAttachment(TENANT_ID, TenantContextHolder.getTenantId());
        }
        // 实际的rpc调用
        return invoker.invoke(invocation);
    }
}
