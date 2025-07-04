package com.example.hr.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = { KafkaAutoConfiguration.class })
@ConditionalOnProperty(name = "messagingStrategy", havingValue = "rabbit")
public class DisableKafkaAutoConfiguration {

}
