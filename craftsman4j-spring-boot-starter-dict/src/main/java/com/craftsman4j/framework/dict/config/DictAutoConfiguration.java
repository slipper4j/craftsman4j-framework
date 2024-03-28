package com.craftsman4j.framework.dict.config;

import com.craftsman4j.framework.common.util.object.BeanUtils;
import com.craftsman4j.framework.dict.core.DictDataApi;
import com.craftsman4j.framework.dict.core.biz.dto.DictDataRespDTO;
import com.craftsman4j.framework.dict.core.biz.mapper.DictDataMapper;
import com.craftsman4j.framework.dict.core.biz.mapper.DictTypeMapper;
import com.craftsman4j.framework.dict.core.biz.pojo.DictDataDO;
import com.craftsman4j.framework.dict.core.biz.service.DictDataService;
import com.craftsman4j.framework.dict.core.biz.service.DictDataServiceImpl;
import com.craftsman4j.framework.dict.core.biz.service.DictTypeService;
import com.craftsman4j.framework.dict.core.biz.service.DictTypeServiceImpl;
import com.craftsman4j.framework.dict.core.util.DictFrameworkUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author zhougang
 */
@AutoConfiguration
@MapperScan(value = "com.craftsman4j.framework.dict.core.biz.mapper")
public class DictAutoConfiguration {

    @Bean
    public DictTypeServiceImpl dictTypeService(DictTypeMapper dictDataMapper) {
        return new DictTypeServiceImpl(dictDataMapper);
    }

    @Bean
    public DictDataServiceImpl dictDataService(DictTypeService dictTypeService,
                                               DictDataMapper dictDataMapper) {
        return new DictDataServiceImpl(dictTypeService, dictDataMapper);
    }

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public DictFrameworkUtils dictUtils(@Autowired(required = false) DictDataApi dictDataApi,
                                        DictDataService dictDataService) {
        if (dictDataApi == null) {
            dictDataApi = new DictDataApi() {
                @Override
                public DictDataRespDTO getDictData(String type, String value) {
                    DictDataDO dictData = dictDataService.getDictData(type, value);
                    return BeanUtils.toBean(dictData, DictDataRespDTO.class);
                }

                @Override
                public DictDataRespDTO parseDictData(String type, String label) {
                    DictDataDO dictData = dictDataService.parseDictData(type, label);
                    return BeanUtils.toBean(dictData, DictDataRespDTO.class);
                }
            };
        }
        DictFrameworkUtils.init(dictDataApi);
        return new DictFrameworkUtils();
    }

}
