package com.craftsman4j.framework.security.core.filter;

import com.craftsman4j.framework.security.core.LoginUser;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Token抽象过滤器，验证token的有效性
 * 验证通过后，获得 {@link LoginUser} 信息，并加入到 Spring Security 上下文
 *
 * @author TasteCode
 */
public abstract class AbstractTokenAuthenticationFilter extends OncePerRequestFilter {

}
