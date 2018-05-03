package com.townmc.demo.service;

import com.townmc.demo.domain.po.Demo;

import java.util.List;

/**
 * 演示service接口
 */
public interface DemoService {

    /**
     * 获得所有记录
     * @return
     */
    public List<Demo> listAll();

    /**
     * 根据id查询记录
     * @param id
     * @return
     */
    public Demo findOne(String id);

    /**
     * 添加记录
     * @param id
     * @param name
     * @return
     */
    public Demo add(String id, String name);

    /**
     * 修改记录
     * @param id
     * @param name
     * @return
     */
    public Demo update(String id, String name);
}
