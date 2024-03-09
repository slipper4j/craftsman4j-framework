package com.craftsman4j.framework.quartz.core.mapper;

import com.craftsman4j.framework.common.pojo.PageResult;
import com.craftsman4j.framework.mybatis.core.mapper.BaseMapperX;
import com.craftsman4j.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.craftsman4j.framework.quartz.core.pojo.JobDO;
import com.craftsman4j.framework.quartz.core.vo.JobPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务 Mapper
 *
 * @author craftsman4j
 */
@Mapper
public interface JobMapper extends BaseMapperX<JobDO> {

    default JobDO selectByHandlerName(String handlerName) {
        return selectOne(JobDO::getHandlerName, handlerName);
    }

    default PageResult<JobDO> selectPage(JobPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<JobDO>()
                .likeIfPresent(JobDO::getName, reqVO.getName())
                .eqIfPresent(JobDO::getStatus, reqVO.getStatus())
                .likeIfPresent(JobDO::getHandlerName, reqVO.getHandlerName())
        );
    }

}
