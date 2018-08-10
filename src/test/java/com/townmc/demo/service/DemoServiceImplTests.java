package com.townmc.demo.service;

import com.townmc.demo.Application;
import com.townmc.demo.domain.po.Demo;
import com.townmc.demo.service.impl.DemoServiceImpl;
import com.townmc.demo.utils.annotations.RetryOnOptimisticLockingFailure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DemoServiceImplTests {
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private DemoService demoService;

    @Test
    public void testAdd() {
        jdbcTemplate.update("delete from demo where demo_id=?", new Object[]{"t2"});
        demoService.add("t2", "自动测试4");
    }

    @Test
    @RetryOnOptimisticLockingFailure
    public void testUpdate() {
        demoService.update("t2", "自动测试修改45");
    }

    @Test
    public void testFindOne() {
        Demo demo = demoService.findOneBySqlDao("a2");
        System.out.println(demo.getDemoName());
    }
}
