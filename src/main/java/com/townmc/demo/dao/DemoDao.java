package com.townmc.demo.dao;

import com.townmc.demo.domain.po.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoDao extends JpaRepository<Demo, String> {
}
