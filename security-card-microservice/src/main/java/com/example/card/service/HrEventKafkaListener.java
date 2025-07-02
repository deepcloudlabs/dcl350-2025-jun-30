package com.example.card.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class HrEventKafkaListener {

	@KafkaListener(topics = "${hrEventsTopic}")
	public void listenHrEvent(String eventAsJson) {
		System.err.println("New event has arrived from Kafka topic: %s".formatted(eventAsJson));
	}
}
