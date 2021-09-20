package com.townmc.boot.dao.impl;

import com.townmc.boot.dao.RedisDao;
import com.townmc.boot.model.enums.CacheKeyPrefixEnum;
import com.townmc.utils.JsonUtil;
import com.townmc.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author meng
 */
@Component
@Slf4j
public class RedisDaoImpl implements RedisDao {
    @Resource private RedisTemplate redisTemplate;

    @Override
    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object get(String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    @Override
    public boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public long increment(String key, int expireSeconds) {
        long count = redisTemplate.opsForValue().increment(key, 1);
        if(count == 1){
            //设置有效期秒
            redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
            return count;
        }else{
            long time = redisTemplate.getExpire(key,TimeUnit.SECONDS);
            if(time == -1){
                //设置失败重新设置过期时间
                redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
            }
        }
        return count;
    }

    @Override
    public <T, R> R getCache(String key, Function<T, R> function) {
        if (StringUtil.isBlank(key)) {
            key = "";
        }
        boolean match = false;
        for (CacheKeyPrefixEnum value : CacheKeyPrefixEnum.values()) {
            if (key.startsWith(value.getKey())) {
                match = true;
                break;
            }
        }
        if (!match) {
            log.info("非法缓存前缀{}", key);
            return null;
        }
        ValueOperations<Serializable, R> operations = redisTemplate.opsForValue();
        R r = operations.get(key);
        log.info("get cache：key{},value{}", key, JsonUtil.object2Json(r));
        if (r == null) {
            r = function.apply((T) key);
            operations.set(key, r);
            redisTemplate.expire(key, 1, TimeUnit.HOURS);
        }
        return r;
    }

    @Override
    public <T> Set<T> getSetByKey(String key) {
        SetOperations<String, T> setOperations = redisTemplate.opsForSet();
        return setOperations.members(key);
    }

    @Override
    public <T> void initSet(String key, Set<T> set, Long time, TimeUnit timeUnit) {
        SetOperations<String, T> setOperations = redisTemplate.opsForSet();
        setOperations.add(key, (T[]) set.toArray());
        redisTemplate.expire(key, time, timeUnit);
    }
}
