package com.townmc.demo.service;

import com.townmc.demo.Application;
import com.townmc.demo.dao.RedisDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisDaoTests {
    @Autowired private RedisDao redisDao;

    @Test
    public void testSet() {
        String key = "test_key_001";
        String value = "abcdefg12345中文";

        redisDao.set(key, value, 300L);
    }

    @Test
    public void testGet() {
        String key = "test_key_001";
        Object re = redisDao.get(key);

        System.out.println((String)re);
    }
}
