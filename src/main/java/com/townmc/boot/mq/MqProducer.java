package com.townmc.boot.mq;

import com.townmc.boot.model.po.Demo;
import com.townmc.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqProducer {
    @Autowired private AmqpTemplate template;

    /**
     * 发送email邮件
     * @param demo
     */
    public void demo(Demo demo) {
        template.convertAndSend("demo", JsonUtil.object2Json(demo));
    }

}
