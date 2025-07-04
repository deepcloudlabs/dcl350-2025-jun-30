package com.example.hr.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = { RabbitAutoConfiguration.class })
@ConditionalOnProperty(name = "messagingStrategy", havingValue = "kafka")
public class DisableAmqpAutoConfiguration {

}
