package com.townmc.boot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.townmc.boot.dao.mybatis.DemoMapper;
import com.townmc.boot.domain.po.Demo;
import com.townmc.boot.service.DemoService;
import com.townmc.utils.PageWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author meng
 */
@Service
public class DemoServiceImpl implements DemoService {

    private final DemoMapper demoMapper;
    public DemoServiceImpl(DemoMapper demoMapper) {
        this.demoMapper = demoMapper;
    }

    @Override
    public PageWrapper<Demo> pageByName(Integer page, Integer pageSize, String name) {

        // 分页设置
        PageHelper.startPage(page, pageSize);
        List<Demo> demoList = demoMapper.listAllByName(name);

        // 获得分页数据
        PageInfo<Demo> pageData = new PageInfo<>(demoList);

        // 包装成与JPA统一的分页对象返回，便于调用者处理
        PageWrapper<Demo> pageWrapper = new PageWrapper<>(page, pageSize, pageData.getList(), pageData.getTotal());

        return pageWrapper;
    }
}
