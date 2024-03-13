package com.craftsman4j.framework.security.core.dubbo;

import com.craftsman4j.framework.security.core.LoginUser;
import com.craftsman4j.framework.security.core.util.SecurityFrameworkUtils;
import com.craftsman4j.framework.web.core.util.WebFrameworkUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

/**
 * dubbo提供者过滤器
 *
 * @author zhougang
 */
public class SecurityProviderFilter implements Filter {

    private static final String LOGIN_USER_ID = "login-user-id";

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        LoginUser loginUser = (LoginUser) RpcContext.getServiceContext().getObjectAttachment(LOGIN_USER_ID);
        if (loginUser != null) {
            SecurityFrameworkUtils.setLoginUser(loginUser, WebFrameworkUtils.getRequest());
        }
        // 实际的rpc调用
        return invoker.invoke(invocation);
    }
}
