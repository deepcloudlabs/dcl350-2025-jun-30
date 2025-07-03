package com.example.lottery.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableFeignClients(basePackages = "com.example.lottery.client.service")
public class LotteryConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotteryConsumerApplication.class, args);
	}

}
