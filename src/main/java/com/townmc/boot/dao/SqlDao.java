package com.townmc.boot.dao;

import com.townmc.boot.domain.po.Demo;

/**
 * !!!!示例程序!!!!
 * 直接执行sql查询。
 * 主要用于处理不需要定义对应的数据库实体，但是又需要查询数据的情况
 * @author meng
 */
public interface SqlDao {
    Demo findOne(String id);
}
