package com.townmc.boot.dao;

import com.townmc.boot.domain.po.Demo;

/**
 * !!!!示例程序!!!!
 * 主要用于处理一些复杂的查询和只能使用sql的情况
 */
public interface SqlDao {
    public Demo findOne(String id);
}
