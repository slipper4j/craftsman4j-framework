package com.craftsman4j.framework.lock4j.core.util;


import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.executor.LockExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * 分布式锁工具类
 *
 * @author zhougang
 * @since 2023/11/30 10:33
 */
@Slf4j
public class Lock4jUtils {

    private static LockTemplate LOCK_TEMPLATE;

    /**
     * 设置默认执行器
     *
     * @param lockTemplate 锁模板方法
     */
    public static void init(LockTemplate lockTemplate) {
        Lock4jUtils.LOCK_TEMPLATE = lockTemplate;
        log.info("[init][初始化 Lock4jUtils 成功]");
    }

    /**
     * 尝试加锁，key默认30秒过期，获取锁超时时间5秒
     *
     * @param key      锁的key
     * @param supplier 运行方法
     * @param <T>      返回类型
     * @return 返回值
     */
    public static <T> T tryLock(String key, Supplier<T> supplier) {
        return tryLock(key, 0, 5000, supplier);
    }

    /**
     * 尝试加锁
     *
     * @param key            锁的key
     * @param expire         过期时间 单位：毫秒 expire <= 0 默认30秒
     * @param acquireTimeout 获取锁超时时间 单位：毫秒 acquireTimeout < 0 默认3秒
     * @param supplier       运行方法
     * @param <T>
     * @return
     */
    public static <T> T tryLock(String key, long expire, long acquireTimeout, Supplier<T> supplier) {
        return tryLock(key, expire, acquireTimeout, null, supplier);
    }

    /**
     * 尝试加锁
     *
     * @param key            锁的key
     * @param expire         过期时间 单位：毫秒<p>未设置则为默认时间30秒<p/>
     * @param acquireTimeout 获取锁超时时间 单位：毫秒<p>未设置则为默认时间3秒<p/>
     * @param executor       lock 执行器
     * @param supplier       运行方法
     * @param <T>            supplier 返回类型
     * @return supplier 返回值
     */
    public static <T> T tryLock(String key, long expire, long acquireTimeout,
                                Class<? extends LockExecutor<?>> executor,
                                Supplier<T> supplier) {
        LockInfo lockInfo = null;
        try {
            lockInfo = LOCK_TEMPLATE.lock(key, expire, acquireTimeout, executor);
            if (null == lockInfo) {
                return null;
            }
            return supplier.get();
        } finally {
            if (null != lockInfo) {
                final boolean releaseLock = LOCK_TEMPLATE.releaseLock(lockInfo);
                if (!releaseLock) {
                    log.error("releaseLock fail,lockKey={}, lockValue={}", lockInfo.getLockKey(), lockInfo.getLockValue());
                }
            }
        }
    }

    /**
     * 尝试加锁，key默认30秒过期，获取锁超时时间默认5秒
     *
     * @param key      锁的key
     * @param runnable 运行方法
     * @param <T>      返回类型
     * @return 返回值
     */
    public static boolean tryLock(String key, Runnable runnable) {
        return tryLock(key, 0, 5000, runnable);
    }

    /**
     * 尝试加锁
     *
     * @param key            锁的key
     * @param expire         过期时间 单位：毫秒 expire <= 0 默认30秒
     * @param acquireTimeout 获取锁超时时间 单位：毫秒 acquireTimeout < 0 默认3秒
     * @param runnable       运行方法
     * @param <T>
     * @return
     */
    public static boolean tryLock(String key, long expire, long acquireTimeout, Runnable runnable) {
        return tryLock(key, expire, acquireTimeout, null, runnable);
    }

    /**
     * 尝试加锁
     *
     * @param key            锁的key
     * @param expire         过期时间 单位：毫秒<p>未设置则为默认时间30秒<p/>
     * @param acquireTimeout 获取锁超时时间 单位：毫秒<p>未设置则为默认时间3秒<p/>
     * @param executor       lock 执行器
     * @param runnable       运行方法
     * @param <T>            supplier 返回类型
     * @return supplier 返回值
     */
    public static boolean tryLock(String key, long expire, long acquireTimeout,
                                  Class<? extends LockExecutor<?>> executor,
                                  Runnable runnable) {
        LockInfo lockInfo = null;
        try {
            lockInfo = LOCK_TEMPLATE.lock(key, expire, acquireTimeout, executor);
            if (null == lockInfo) {
                return false;
            }
            runnable.run();
            return true;
        } finally {
            if (null != lockInfo) {
                final boolean releaseLock = LOCK_TEMPLATE.releaseLock(lockInfo);
                if (!releaseLock) {
                    log.error("releaseLock fail, lockKey={}, lockValue={}", lockInfo.getLockKey(), lockInfo.getLockValue());
                }
            }
        }
    }
}
