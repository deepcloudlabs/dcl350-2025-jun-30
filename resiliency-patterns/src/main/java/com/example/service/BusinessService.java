package com.example.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {
	private final IntegrationService integrationService;
	
	public BusinessService(IntegrationService integrationService) {
		this.integrationService = integrationService;
	}

	//@Scheduled(fixedRate = 3_000)
	public void remoteCall() {
		var result = integrationService.fun();
		System.out.println("fun: result is %d".formatted(result));
	}
	
	//@Scheduled(fixedRate = 80)
	public void remoteCall2() {
		var result = integrationService.gun();
		System.out.println("gun: result is %d".formatted(result));
	}
	
	@Scheduled(fixedRate = 10_000)
	public void remoteCall3() {
		integrationService.sun().thenAcceptAsync( result -> System.out.println("sun: result is %d".formatted(result)));
	}
}
