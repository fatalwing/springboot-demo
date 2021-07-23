package com.townmc.boot.service;

/**
 * 锁服务定义
 */
public interface LockService {

    /**
     * 尝试获取一次锁，不管成功失败，都立即返回true、false
     * @param lockKey 等待上锁的对象
     * @return true/false
     */
    boolean tryLock(String lockKey);

    /**
     * 在timeout时间内阻塞式地获取锁，成功返回true，超时返回false，同时立即处理interrupt信息，并抛出异常
     * @param lockKey 等待上锁的对象
     * @param timeout 尝试获取锁的超时时间，单位为毫秒
     * @return true/false
     */
    boolean tryLock(String lockKey, long timeout);

    /**
     * 释放锁
     * @param lock 等待释放锁的对象
     */
    void unlock(String lock);
}
