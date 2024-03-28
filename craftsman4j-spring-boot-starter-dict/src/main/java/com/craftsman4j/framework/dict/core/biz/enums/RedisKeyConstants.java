package com.craftsman4j.framework.dict.core.biz.enums;

/**
 * System Redis Key 枚举类
 *
 * @author craftsman4j
 */
public interface RedisKeyConstants {

    /**
     * 指定字段的type与value
     * <p>
     * KEY 格式：dict_type_value:{type}+{value}
     * VALUE 数据类型：String label
     */
    String DICT_TYPE_VALUE = "dict_type_value";

    /**
     * 指定字段的type与label
     * <p>
     * KEY 格式：dict_type_value:{type}+{label}
     * VALUE 数据类型：String value
     */
    String DICT_TYPE_LABEL = "dict_type_label";
}
