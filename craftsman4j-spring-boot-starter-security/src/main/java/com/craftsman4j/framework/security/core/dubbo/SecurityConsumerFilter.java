package com.craftsman4j.framework.security.core.dubbo;

import com.craftsman4j.framework.security.core.ILoginUser;
import com.craftsman4j.framework.security.core.util.SecurityFrameworkUtils;
import org.apache.dubbo.rpc.*;

/**
 * dubbo消费者过滤器
 *
 * @author zhougang
 */
public class SecurityConsumerFilter implements Filter {

    private static final String LOGIN_USER_ID = "login-user-id";

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        ILoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser != null) {
            RpcContext.getServiceContext().setObjectAttachment(LOGIN_USER_ID, loginUser);
        }
        // 实际的rpc调用
        return invoker.invoke(invocation);
    }
}
