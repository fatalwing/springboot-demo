package com.townmc.boot.service;

/**
 * 锁服务定义
 *
 * @author Yoshi
 * @since 31 October 2017
 */
public interface LockService {

    /**
     * 尝试获取分布式锁
     * @param lock 等待上锁的对象
     * @return true/false
     */
    boolean tryLock(Object lock);

    /**
     * 尝试获取分布式锁
     * @param lock 等待上锁的对象
     * @param timeout 尝试获取锁的超时时间
     * @return true/false
     */
    boolean tryLock(Object lock, long timeout);

    /**
     * 尝试获取分布式锁
     * @param lock 等待上锁的对象
     * @param timeout 尝试获取锁的超时时间
     * @param tryInterval 多少ms尝试获取一次锁
     * @return true/false
     */
    boolean tryLock(Object lock, long timeout, long tryInterval);

    /**
     * 尝试获取分布式锁
     * @param lock 等待上锁的对象
     * @param timeout 尝试获取锁的超时时间
     * @param tryInterval 多少ms尝试获取一次锁
     * @param lockExpireTime 获取成功后锁的过期时间
     * @return true/false
     */
    boolean tryLock(Object lock, long timeout, long tryInterval, long lockExpireTime);

    /**
     * 释放锁
     * @param lock 等待释放锁的对象
     */
    void release(Object lock);
}
