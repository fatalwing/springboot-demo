package com.townmc.boot.service.impl;

import com.townmc.boot.service.LockService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redis实现的分布式锁
 *
 */
@Service("lockService")
public class RedisLockServiceImpl implements LockService {

    @Autowired private StringRedisTemplate redisTemplate;

    private static final long timeout = 30000;
    private static final long tryInterval = 3000;
    private static final long lockExpireTime = 60000;


    private static final Log log = LogFactory.getLog(RedisLockServiceImpl.class);

    /**
     * 锁前缀，在redis中标识这是一条分布式锁数据
     */
    private static final String LOCK_PREFIX = "distributed-lock::";

    /**
     * 尝试获取分布式锁
     *
     * @param lock 等待上锁的对象
     * @return true/false
     */
    @Override
    public boolean tryLock(Object lock) {
        return tryLock(lock, timeout, tryInterval, lockExpireTime);
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lock 等待上锁的对象
     * @param timeout 尝试获取锁的超时时间 单位：毫秒
     * @return true/false
     */
    @Override
    public boolean tryLock(Object lock, long timeout) {
        return tryLock(lock, timeout, lockExpireTime);
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lock 等待上锁的对象
     * @param timeout 尝试获取锁的超时时间 单位：毫秒
     * @param tryInterval 多少ms尝试获取一次锁 单位：毫秒
     * @return true/false
     */
    @Override
    public boolean tryLock(Object lock, long timeout, long tryInterval) {
        return tryLock(lock, timeout, tryInterval, lockExpireTime);
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lock 等待上锁的对象
     * @param timeout 尝试获取锁的超时时间 单位：毫秒
     * @param tryInterval 多少ms尝试获取一次锁 单位：毫秒
     * @param lockExpireTime 获取成功后锁的过期时间 单位：毫秒
     * @return true/false
     *
     * @see ValueOperations#setIfAbsent(Object, Object)
     */
    @Override
    public boolean tryLock(Object lock, long timeout, long tryInterval, long lockExpireTime) {
        String key = serializable(lock);

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        try {

            long startTimeMillis = System.currentTimeMillis();

            while (true) {

                /*
                * 每次尝试获取锁前都要检查是否已经超时，
                * 如果当前循环开始时已经超时，则不需要再次浪费socket资源与redis进行通讯
                * */
                if (isTimeout(startTimeMillis, timeout)) {
                    if (log.isDebugEnabled()) {
                        log.debug(Thread.currentThread().getName() + " get redis distributed lock timeout, stop trying to get the lock again");
                    }
                    return false;
                }

                /*
                * 利用ValueOperations#setIfAbsent(Object, Object)
                * 如果key不存在则写入数据并返回true，如果存在则直接返回false特性来实现锁的争抢机制
                * */
                if (ops.setIfAbsent(key, key)) {
                    ops.set(key, key, lockExpireTime, TimeUnit.MILLISECONDS);
                    if (log.isDebugEnabled()) {
                        log.debug(Thread.currentThread().getName() + " get redis distributed lock success");
                    }
                    return true;
                }

                if (log.isDebugEnabled()) {
                    log.debug(Thread.currentThread().getName() + " lock exists");
                }

                /*
                * 每次尝试获取锁失败后再次检查是否已经超时，
                * 如果当前获取锁失败后已经超时，则不需要再次浪费cpu资源进行sleep
                * */
                if (isTimeout(startTimeMillis, timeout)) {
                    if (log.isDebugEnabled()) {
                        log.debug(Thread.currentThread().getName() + " get redis distributed lock timeout, stop trying to get the lock again");
                    }
                    return false;
                }

                Thread.sleep(tryInterval);
            }

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(Thread.currentThread().getName() + " get redis distributed lock error", e);
            }
            return false;
        }
    }

    /**
     * 释放锁
     *
     * @param lock 等待释放锁的对象
     *
     */
    @Override
    public void release(Object lock) {
        String key = serializable(lock);
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String value = ops.get(key);
        if (!StringUtils.isEmpty(value)) {
            redisTemplate.delete(key);
            if (log.isDebugEnabled()) {
                log.debug(Thread.currentThread().getName() + " release redis distributed lock success");
            }
        }
    }

    /**
     * 序列化待锁对象
     *
     * @param lock 锁对象
     * @return String
     *
     * @see SerializationUtils#serialize(Object)
     * @see Base64Utils#encodeToString(byte[])
     */
    private String serializable(Object lock) {
        Assert.notNull(lock, "lock object argument must be null");
        byte[] bytes = SerializationUtils.serialize(lock);
        return LOCK_PREFIX + Base64Utils.encodeToString(bytes);
    }

    /**
     * 检查是否超时
     * @param startTimeMillis 开始时间，单位：毫秒
     * @param timeout 尝试获取锁的超时时间
     * @return true/false
     */
    private boolean isTimeout(long startTimeMillis, long timeout) {
        return System.currentTimeMillis() - startTimeMillis > timeout;
    }
}
