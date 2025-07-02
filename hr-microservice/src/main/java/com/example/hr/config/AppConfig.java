package com.example.hr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hr.application.HrApplication;
import com.example.hr.application.business.StandardHrApplication;
import com.example.hr.domain.event.HrEvent;
import com.example.hr.infra.EventPublisher;
import com.example.hr.repository.EmployeeRepository;

@Configuration
public class AppConfig {

	@Bean
	// adapters: EmployeeRepositoryJpaAdapter, EventPublisherApplicationEventPublisherAdapter
	HrApplication createApplication(EmployeeRepository employeeRepository, EventPublisher<HrEvent> eventPublisher) {
		return new StandardHrApplication(employeeRepository, eventPublisher);
	}
}
