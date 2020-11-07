package com.townmc.boot.dao.mybatis;

import com.townmc.boot.domain.po.Demo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemoMapper {

    List<Demo> listAllByName(String name);
}
