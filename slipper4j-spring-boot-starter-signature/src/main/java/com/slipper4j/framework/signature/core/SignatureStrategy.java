package com.slipper4j.framework.signature.core;

import com.slipper4j.framework.signature.core.annotations.Signature;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zhougang
 * @since 2023/11/10 9:42
 */
public interface SignatureStrategy {

    /**
     * 验证签名是否通过
     *
     * @return 是否限速
     */
    boolean verify(Signature signature, HttpServletRequest request) throws IOException;
}
