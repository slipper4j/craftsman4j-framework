package com.craftsman4j.framework.dict.core.annotations;

import com.craftsman4j.framework.dict.core.jackson.DictSerializer;
import com.craftsman4j.framework.dict.core.biz.pojo.DictTypeDO;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 字典格式化
 * <p>
 * 实现将字典数据的值，格式化成字典数据的标签
 */

@JsonSerialize(using = DictSerializer.class)
@JacksonAnnotationsInside
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Dict {

    /**
     * 例如说，{@link DictTypeDO .getName()}
     *
     * @return 字典类型
     */
    String type();

    /**
     * 目标属性
     */
    String targetFiled();

}
