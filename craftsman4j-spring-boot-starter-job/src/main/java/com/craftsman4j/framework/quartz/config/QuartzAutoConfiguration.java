package com.craftsman4j.framework.quartz.config;

import com.craftsman4j.framework.quartz.core.biz.mapper.JobLogMapper;
import com.craftsman4j.framework.quartz.core.biz.mapper.JobMapper;
import com.craftsman4j.framework.quartz.core.scheduler.SchedulerManager;
import com.craftsman4j.framework.quartz.core.biz.service.JobLogServiceImpl;
import com.craftsman4j.framework.quartz.core.biz.service.JobServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Optional;

/**
 * 定时任务 Configuration
 * @author zhougang
 */
@AutoConfiguration
@EnableScheduling // 开启 Spring 自带的定时任务
@Slf4j
@MapperScan(value = "com.craftsman4j.framework.quartz.core.biz.mapper")
public class QuartzAutoConfiguration {

    @Bean
    public JobServiceImpl jobService(JobMapper jobMapper, SchedulerManager schedulerManager) {
        return new JobServiceImpl(jobMapper, schedulerManager);
    }

    @Bean
    public JobLogServiceImpl jobLogService(JobLogMapper jobLogMapper) {
        return new JobLogServiceImpl(jobLogMapper);
    }

    @Bean
    public SchedulerManager schedulerManager(Optional<Scheduler> scheduler) {
        if (!scheduler.isPresent()) {
            log.info("[定时任务 - 已禁用]");
            return new SchedulerManager(null);
        }
        return new SchedulerManager(scheduler.get());
    }

}
