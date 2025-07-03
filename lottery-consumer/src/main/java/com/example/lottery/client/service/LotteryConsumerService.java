package com.example.lottery.client.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Service
@ConditionalOnProperty(name = "clientSideLoadBalancing",havingValue = "custom")
public class LotteryConsumerService {
	private static final String LOTTERY_API_URL = "http://%s:%d/api/v1/numbers?column=%d";
	private final RestTemplate restTemplate;
	private final DiscoveryClient discoveryClient;
	private List<ServiceInstance> instances;
	private AtomicInteger counter = new AtomicInteger(0);
	
	public LotteryConsumerService(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
	}

	@PostConstruct
	@Scheduled(fixedRate = 60_000)
	public void retrieveInstanceList() {
		this.instances = discoveryClient.getInstances("lottery");
		this.instances.forEach( instance -> {
			System.out.println("%s:%d".formatted(instance.getHost(),instance.getPort()));
		});
	}

	@Scheduled(fixedRate = 5_000)
	public void makeRestApiCall() {
		try {
			var instance = getNextInstance();
			String url = LOTTERY_API_URL.formatted(instance.getHost(),instance.getPort(),3);
			System.err.println("Sending request to %s...".formatted(url));
			var response = restTemplate.getForEntity(url, String.class);
			System.out.println(response.getBody());
		}catch (Exception e) {
			System.err.println("Rest call has failed: %s".formatted(e.getMessage()));
			retrieveInstanceList();
		}
	}

	private ServiceInstance getNextInstance() {
		if (instances.isEmpty())
			throw new IllegalStateException("There are no instane available!");
		return instances.get(counter.getAndIncrement() % instances.size());
	}
}
