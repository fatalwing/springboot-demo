package com.townmc.boot.service.impl;

import com.townmc.boot.dao.RedisDao;
import com.townmc.boot.service.CacheService;
import org.springframework.stereotype.Service;

/**
 * @author meng
 */
@Service
public class CacheServiceImpl implements CacheService {
    private final RedisDao redisDao;
    public CacheServiceImpl(RedisDao redisDao) {
        this.redisDao = redisDao;
    }

    @Override
    public boolean set(String key, Object value, Long expireTime) {
        return redisDao.set(key, value, expireTime);
    }

    @Override
    public Object get(String key) {
        return redisDao.get(key);
    }

    @Override
    public long increment(String key, int expireSeconds) {
        return redisDao.increment(key, expireSeconds);
    }
}
