package com.craftsman4j.framework.dict.core;

import com.craftsman4j.framework.dict.core.dto.DictDataRespDTO;

/**
 * 字典数据 API 接口
 *
 * @author craftsman4j
 */
public interface DictDataApi {

    /**
     * 获得指定的字典数据，从缓存中
     *
     * @param type  字典类型
     * @param value 字典数据值
     * @return 字典数据
     */
    DictDataRespDTO getDictData(String type, String value);

    /**
     * 解析获得指定的字典数据，从缓存中
     *
     * @param type  字典类型
     * @param label 字典数据标签
     * @return 字典数据
     */
    DictDataRespDTO parseDictData(String type, String label);

}
