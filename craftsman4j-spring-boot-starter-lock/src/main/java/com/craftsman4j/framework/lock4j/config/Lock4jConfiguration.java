package com.craftsman4j.framework.lock4j.config;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.lock.LockFailureStrategy;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.spring.boot.autoconfigure.LockAutoConfiguration;
import com.craftsman4j.framework.lock4j.core.DefaultLockFailureStrategy;
import com.craftsman4j.framework.lock4j.core.util.Lock4jUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

@AutoConfiguration(before = LockAutoConfiguration.class)
@ConditionalOnClass(name = "com.baomidou.lock.annotation.Lock4j")
public class Lock4jConfiguration implements InitializingBean {

    @ConditionalOnMissingBean(LockFailureStrategy.class)
    public DefaultLockFailureStrategy lockFailureStrategy() {
        return new DefaultLockFailureStrategy();
    }

    @Override
    public void afterPropertiesSet() {
        Lock4jUtils.init(SpringUtil.getBean(LockTemplate.class));
    }

}
