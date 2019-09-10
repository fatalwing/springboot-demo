package com.townmc.boot.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * 定时处理任务
 */
@Slf4j
@Component
public class ScheduledService {
    @Autowired private EntityManager entityManager;

    @Value("${scheduling.enabled.demo}")
    private Boolean enabledDemo;

    /**
     * demo
     */
    @Async
    @Scheduled(cron="0 5 0 * * ?")
    @Transactional
    public void demo() {
        if (!enabledDemo) return;

    }
}
