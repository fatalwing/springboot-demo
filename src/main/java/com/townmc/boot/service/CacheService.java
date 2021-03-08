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
    public boolean set(String key, Object value, Long expireTime);

    /**
     * 取值
     * @param key 键
     * @return
     */
    public Object get(String key);

    /**
     * key值在指定的时间内递增
     * @param key
     * @param expireSeconds 单位：秒
     * @return
     */
    public long increment(String key, int expireSeconds);

}
