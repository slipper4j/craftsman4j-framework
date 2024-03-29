package com.craftsman4j.framework.signature.core.service;

import com.craftsman4j.framework.signature.core.SignatureApi;
import com.craftsman4j.framework.signature.core.properties.SignatureProperties;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 签名的默认实现
 *
 * @author zhougang
 * @since 2023/10/11 13:39
 */
public class DefaultSignatureStrategy extends AbstractSignatureStrategy {

    public DefaultSignatureStrategy(StringRedisTemplate stringRedisTemplate,
                                    SignatureProperties signatureProperties,
                                    SignatureApi signatureApi) {
        super(stringRedisTemplate, signatureProperties, signatureApi);
    }
}
