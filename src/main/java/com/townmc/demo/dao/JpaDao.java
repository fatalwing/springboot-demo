package com.townmc.demo.dao;

import com.townmc.demo.domain.po.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * !!!!示例程序!!!!
 * 数据库的基本操作和简单操作通过jpa完成，它无需自定义实现类
 */
public interface JpaDao extends JpaRepository<Demo, String> {
}
