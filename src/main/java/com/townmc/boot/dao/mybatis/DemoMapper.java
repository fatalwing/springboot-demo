package com.townmc.boot.dao.mybatis;

import com.townmc.boot.model.po.Demo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemoMapper {
    int insert(Demo record);

    List<Demo> selectAll();

    List<Demo> listAllByName(String name);
}
