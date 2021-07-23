package com.townmc.boot.service;

import com.townmc.boot.domain.po.Demo;
import com.townmc.boot.utils.PageWrapper;

/**
 * @author meng
 */
public interface DemoService {

    /**
     * 根据名称查询
     * 获得分页对象
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    PageWrapper<Demo> pageByName(Integer page, Integer pageSize, String name);
}
