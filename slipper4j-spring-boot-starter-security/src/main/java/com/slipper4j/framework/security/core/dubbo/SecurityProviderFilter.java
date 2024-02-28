package com.slipper4j.framework.security.core.dubbo;

import com.slipper4j.framework.security.core.ILoginUser;
import com.slipper4j.framework.security.core.util.SecurityFrameworkUtils;
import com.slipper4j.framework.web.core.util.WebFrameworkUtils;
import org.apache.dubbo.rpc.*;

/**
 * dubbo提供者过滤器
 *
 * @author zhougang
 */
public class SecurityProviderFilter implements Filter {

    private static final String LOGIN_USER_ID = "login-user-id";

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        ILoginUser loginUser = (ILoginUser) RpcContext.getServiceContext().getObjectAttachment(LOGIN_USER_ID);
        if (loginUser != null) {
            SecurityFrameworkUtils.setLoginUser(loginUser, WebFrameworkUtils.getRequest());
        }
        // 实际的rpc调用
        return invoker.invoke(invocation);
    }
}
