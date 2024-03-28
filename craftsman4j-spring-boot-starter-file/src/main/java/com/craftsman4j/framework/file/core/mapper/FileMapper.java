package com.craftsman4j.framework.file.core.mapper;

import com.craftsman4j.framework.common.pojo.PageResult;
import com.craftsman4j.framework.file.core.pojo.FileDO;
import com.craftsman4j.framework.file.core.vo.file.FilePageReqVO;
import com.craftsman4j.framework.mybatis.core.mapper.BaseMapperX;
import com.craftsman4j.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件操作 Mapper
 *
 * @author craftsman4j
 */
@Mapper
public interface FileMapper extends BaseMapperX<FileDO> {

    default PageResult<FileDO> selectPage(FilePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FileDO>()
                .likeIfPresent(FileDO::getPath, reqVO.getPath())
                .likeIfPresent(FileDO::getType, reqVO.getType())
                .betweenIfPresent(FileDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FileDO::getId));
    }

}
