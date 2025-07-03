package com.example.lottery.client.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "clientSideLoadBalancing",havingValue = "feign")
public class LotteryConsumerFeignClientService {
	private final LotteryService lotteryService;
	
	public LotteryConsumerFeignClientService(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}

	@Scheduled(fixedRate = 5_000)
	public void makeRestApiCall() {
		System.out.println(lotteryService.getLotteryNumbers(3));
	}

}
