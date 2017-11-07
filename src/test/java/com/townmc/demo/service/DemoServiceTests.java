package com.townmc.demo.service;

import com.townmc.demo.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DemoServiceTests {
    @Autowired private DemoService demoService;

    @Test
    public void testAdd() {
        demoService.add("t1", "自动测试2");
    }
}
