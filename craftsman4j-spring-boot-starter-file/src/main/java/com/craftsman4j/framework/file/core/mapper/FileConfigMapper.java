package com.craftsman4j.framework.file.core.mapper;

import com.craftsman4j.framework.common.pojo.PageResult;
import com.craftsman4j.framework.file.core.pojo.FileConfigDO;
import com.craftsman4j.framework.file.core.vo.config.FileConfigPageReqVO;
import com.craftsman4j.framework.mybatis.core.mapper.BaseMapperX;
import com.craftsman4j.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileConfigMapper extends BaseMapperX<FileConfigDO> {

    default PageResult<FileConfigDO> selectPage(FileConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FileConfigDO>()
                .likeIfPresent(FileConfigDO::getName, reqVO.getName())
                .eqIfPresent(FileConfigDO::getStorage, reqVO.getStorage())
                .betweenIfPresent(FileConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FileConfigDO::getId));
    }

    default FileConfigDO selectByMaster() {
        return selectOne(FileConfigDO::getMaster, true);
    }

}
