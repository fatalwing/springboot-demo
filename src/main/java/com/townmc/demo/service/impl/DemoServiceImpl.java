package com.townmc.demo.service.impl;

import com.townmc.demo.dao.JpaDao;
import com.townmc.demo.dao.SqlDao;
import com.townmc.demo.domain.po.Demo;
import com.townmc.demo.service.DemoService;
import com.townmc.demo.utils.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("demoService")
public class DemoServiceImpl implements DemoService {
    @Autowired private JpaDao jpaDao;
    @Autowired private SqlDao sqlDao;

    public List<Demo> listAll() {

        return jpaDao.findAll();
    }

    public Demo findOne(String id) {
        return jpaDao.findById(id).orElse(null);
    }

    public Demo findOneBySqlDao(String id) {
        return sqlDao.findOne(id);
    }

    public Demo add(String id, String name) {
        Demo data = new Demo();
        data.setDemoId(id);
        data.setDemoName(name);
        Demo result = jpaDao.save(data);
        return result;
    }

    public Demo update(String id, String name) {
        Demo data = jpaDao.findById(id).orElseThrow(new LogicException("object_not_exists", "修改的对象不存在"));
        data.setDemoName(name);
        Demo result = jpaDao.save(data);
        return result;
    }
}
