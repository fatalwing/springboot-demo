package com.townmc.demo.service.impl;

import com.townmc.demo.dao.DemoDao;
import com.townmc.demo.domain.po.Demo;
import com.townmc.demo.service.DemoService;
import com.townmc.demo.utils.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("merchantService")
public class DemoServiceImpl implements DemoService {
    @Autowired private DemoDao demoDao;

    public List<Demo> listAll() {

        return demoDao.findAll();
    }

    public Demo findOne(String id) {
        return demoDao.findById(id).orElse(null);
    }

    public Demo add(String id, String name) {
        Demo data = new Demo();
        data.setDemoId(id);
        data.setDemoName(name);
        Demo result = demoDao.save(data);
        return result;
    }

    public Demo update(String id, String name) {
        Demo data = demoDao.findById(id).orElseThrow(new LogicException("object_not_exists", "修改的对象不存在"));
        data.setDemoName(name);
        Demo result = demoDao.save(data);
        return result;
    }
}
