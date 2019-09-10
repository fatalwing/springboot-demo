package com.townmc.boot.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqConsumer {

    /**
     * 发送队列中的邮件
     * @param str
     */
    @RabbitListener(queues="sendEmailMsg")    //监听器监听指定的Queue
    public void demo(String str) {
        log.info("=== create user success. ");
    }

}
