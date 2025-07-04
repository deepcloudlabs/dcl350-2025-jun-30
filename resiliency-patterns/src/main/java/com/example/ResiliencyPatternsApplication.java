package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ResiliencyPatternsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResiliencyPatternsApplication.class, args);
	}

}
