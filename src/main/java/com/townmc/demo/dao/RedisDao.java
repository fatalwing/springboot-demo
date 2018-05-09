package com.townmc.demo.dao;

/**
 * !!!!示例程序!!!!
 * 封装使用redis存储的数据访问
 */
public interface RedisDao {
    public boolean set(String key, Object value, Long expireTime);

    public Object get(String key);
}
