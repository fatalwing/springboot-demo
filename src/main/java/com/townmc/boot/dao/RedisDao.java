package com.townmc.boot.dao;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 封装使用redis存储的数据访问
 * @author meng
 */
public interface RedisDao {
    /**
     * redis存值
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间，单位为秒
     * @return
     */
    boolean set(String key, Object value, Long expireTime);

    /**
     * redis取值
     * @param key 键
     * @return
     */
    Object get(String key);

    /**
     * 删除值
     * @param key
     * @return
     */
    boolean del(String key);

    /**
     * key值在指定的时间内递增
     * @param key
     * @param expireSeconds 单位：秒
     * @return
     */
    long increment(String key, int expireSeconds);

    /**
     *获取缓存数据
     * @param key
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    <T, R> R getCache(String key, Function<T, R> function);


    /**
     * 获取集合
     *
     * @param key
     * @return
     */
    <T> Set<T> getSetByKey(String key);

    /**
     * 初始化集合
     *
     * @param key
     * @param set
     * @param time
     * @param timeUnit
     */
    <T> void initSet(String key, Set<T> set, Long time, TimeUnit timeUnit);
}
