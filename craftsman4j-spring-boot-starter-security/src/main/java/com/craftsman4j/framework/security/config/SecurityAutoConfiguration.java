package com.craftsman4j.framework.security.config;

import com.craftsman4j.framework.security.core.PermissionApi;
import com.craftsman4j.framework.security.core.UserTokenApi;
import com.craftsman4j.framework.security.core.aop.PreAuthenticatedAspect;
import com.craftsman4j.framework.security.core.context.TransmittableThreadLocalSecurityContextHolderStrategy;
import com.craftsman4j.framework.security.core.filter.AbstractTokenAuthenticationFilter;
import com.craftsman4j.framework.security.core.filter.DefaultTokenAuthenticationFilter;
import com.craftsman4j.framework.security.core.handler.AccessDeniedHandlerImpl;
import com.craftsman4j.framework.security.core.handler.AuthenticationEntryPointImpl;
import com.craftsman4j.framework.security.core.service.SecurityFrameworkService;
import com.craftsman4j.framework.security.core.service.SecurityFrameworkServiceImpl;
import com.craftsman4j.framework.web.core.handler.GlobalExceptionHandler;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;

/**
 * Spring Security 自动配置类，主要用于相关组件的配置
 * <p>
 * 注意，不能和 {@link WebSecurityConfigurerAdapter} 用一个，原因是会导致初始化报错。
 * 参见 https://stackoverflow.com/questions/53847050/spring-boot-delegatebuilder-cannot-be-null-on-autowiring-authenticationmanager 文档。
 *
 * @author craftsman4j
 */
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {

    @Resource
    private SecurityProperties securityProperties;

    /**
     * 处理用户未登录拦截的切面的 Bean
     */
    @Bean
    public PreAuthenticatedAspect preAuthenticatedAspect() {
        return new PreAuthenticatedAspect();
    }

    /**
     * 认证失败处理类 Bean
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    /**
     * 权限不够处理器 Bean
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    /**
     * Spring Security 加密器
     * 考虑到安全性，这里采用 BCryptPasswordEncoder 加密器
     *
     * @see <a href="http://stackabuse.com/password-encoding-with-spring-security/">Password Encoding with Spring Security</a>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
    }

    /**
     * Token 认证过滤器 Bean
     */
    @Bean
    @ConditionalOnMissingBean(AbstractTokenAuthenticationFilter.class)
    public AbstractTokenAuthenticationFilter authenticationTokenFilter(GlobalExceptionHandler globalExceptionHandler,
                                                                       UserTokenApi userTokenApi) {
        return new DefaultTokenAuthenticationFilter(securityProperties, globalExceptionHandler, userTokenApi);
    }

    @Bean("ss") // 使用 Spring Security 的缩写，方便使用
    public SecurityFrameworkService securityFrameworkService(PermissionApi permissionApi) {
        return new SecurityFrameworkServiceImpl(permissionApi);
    }

    /**
     * 声明调用 {@link SecurityContextHolder#setStrategyName(String)} 方法，
     * 设置使用 {@link TransmittableThreadLocalSecurityContextHolderStrategy} 作为 Security 的上下文策略
     */
    @Bean
    public MethodInvokingFactoryBean securityContextHolderMethodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(TransmittableThreadLocalSecurityContextHolderStrategy.class.getName());
        return methodInvokingFactoryBean;
    }

}
