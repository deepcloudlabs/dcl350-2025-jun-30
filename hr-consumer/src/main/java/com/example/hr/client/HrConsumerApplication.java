package com.example.hr.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HrConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrConsumerApplication.class, args);
	}

}
