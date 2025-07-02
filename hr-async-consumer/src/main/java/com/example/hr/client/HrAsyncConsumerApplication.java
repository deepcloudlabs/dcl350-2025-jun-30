package com.example.hr.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HrAsyncConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrAsyncConsumerApplication.class, args);
	}

}
