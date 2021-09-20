package com.townmc.boot.dao;

import com.townmc.boot.model.po.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Demo 数据库操作
 */
public interface DemoJpaDao extends JpaRepository<Demo, String> {

    Demo findByIdAndDeleted(String id, int deleted);
}
