package com.craftsman4j.framework.jackson.core.annotations;

import java.lang.annotation.*;

/**
 * 字典格式化
 * <p>
 * 实现将字典数据的值，格式化成字典数据的标签
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Dict {

    /**
     * 例如说，SysDictTypeConstants、InfDictTypeConstants
     *
     * @return 字典类型
     */
    String type();

    /**
     * 目标属性
     */
    String targetFiled();

}
