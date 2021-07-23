package com.townmc.boot.service;

import com.townmc.boot.utils.PageWrapper;
import com.townmc.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoServiceTests {
    @Autowired private DemoService demoService;

    @Test
    public void pageTest() {
        PageWrapper pageData = demoService.pageByName(1, 2, "a");

        log.info(JsonUtil.object2Json(pageData));
    }
}
