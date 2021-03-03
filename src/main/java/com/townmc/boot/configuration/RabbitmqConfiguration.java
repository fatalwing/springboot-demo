package com.townmc.boot.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author meng
 */
@Configuration
public class RabbitmqConfiguration {

    @Bean
    public Queue queueDemo() {
        return new Queue("demo");
    }

}
