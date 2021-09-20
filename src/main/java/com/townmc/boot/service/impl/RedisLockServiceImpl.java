package com.townmc.boot.service.impl;

import com.townmc.boot.service.LockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 基于Redis实现的分布式锁
 *
 * @author meng
 */
@Slf4j
@RequiredArgsConstructor
@Service("lockService")
public class RedisLockServiceImpl implements LockService {
    private static final String LOCKED_KEY_PREFIX = "SERVER_LOCKED_";
    private static final long DEFAULT_EXPIRE_UNUSED = 60000L;

    private final RedisLockRegistry redisLockRegistry;

    /**
     * 该锁会一直等待，即使调用线程的interrupt()也不会中断。除非锁的unlock()被调用
     * 类似悲观锁
     * @param lockKey
     */
    @Override
    public void lock(String lockKey) {
        Lock lock = obtainLock(lockKey);
        lock.lock();
    }

    /**
     * 该锁不会等待，当前锁被占用时将直接返回false
     * 类似乐观锁
     * @param lockKey
     * @return
     */
    @Override
    public boolean tryLock(String lockKey) {
        Lock lock = obtainLock(lockKey);
        return lock.tryLock();
    }

    /**
     * 该锁会在指定的时间内等待，超时没获取到将返回false
     * @param lockKey
     * @param seconds
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, long seconds) {
        Lock lock = obtainLock(lockKey);
        try {
            return lock.tryLock(seconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        try {
            Lock lock = obtainLock(lockKey);
            lock.unlock();
            redisLockRegistry.expireUnusedOlderThan(DEFAULT_EXPIRE_UNUSED);
        } catch (Exception e) {
            log.error("分布式锁 [{}] 释放异常", lockKey, e);
        }
    }

    private Lock obtainLock(String lockKey) {
        return redisLockRegistry.obtain(LOCKED_KEY_PREFIX + lockKey);
    }
}
