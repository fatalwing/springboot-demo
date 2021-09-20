package com.townmc.boot.service;

/**
 * @author meng
 */
public interface CacheService {

    /**
     * 缓存值
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间，单位为秒
     * @return
     */
    boolean set(String key, Object value, Long expireTime);

    /**
     * 删除值
     * @param key
     * @return
     */
    boolean del(String key);

    /**
     * 取值
     * @param key 键
     * @return
     */
    Object get(String key);

    /**
     * key值在指定的时间内递增
     * @param key
     * @param expireSeconds 单位：秒
     * @return
     */
    long increment(String key, int expireSeconds);

}
