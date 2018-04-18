package com.townmc.demo.service;

import com.townmc.demo.dao.DemoDao;
import com.townmc.demo.domain.po.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("merchantService")
public class DemoService {
    @Autowired private DemoDao demoDao;

    public List<Demo> listAll() {

        return demoDao.findAll();
    }

    public Demo findOne(String id) {
        return demoDao.findById(id).get();
    }

    public Demo add(String id, String name) {
        Demo data = new Demo();
        data.setDemoId(id);
        data.setDemoName(name);
        Demo result = demoDao.save(data);
        return result;
    }

    public Demo update(String id, String name) {
        Demo data = demoDao.findById(id).get();
        data.setDemoName(name);
        Demo result = demoDao.save(data);
        return result;
    }
}
