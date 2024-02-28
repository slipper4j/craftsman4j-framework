package com.slipper4j.framework.signature.core;

/**
 * 签名 API 接口
 *
 * @author TasteCode
 */
public interface SignatureApi {

    /**
     * 获取appSecret
     *
     * @return appSecret
     */
    String getAppSecret(String appId);

    String digestEncoder(String plainText);
}
