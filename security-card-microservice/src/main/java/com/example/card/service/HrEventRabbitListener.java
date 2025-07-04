package com.example.card.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.card.dto.HrEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HrEventRabbitListener {
	private final ObjectMapper objectMapper;
	
	public HrEventRabbitListener(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@RabbitListener(queues = "${hrEventsQueue}")
	public void listenHrEvent(String eventAsJson) {
		System.err.println("New event has arrived from RabbitMQ queue: %s".formatted(eventAsJson));
		try {
			var hrEvent = objectMapper.readValue(eventAsJson, HrEvent.class);
			System.err.println(hrEvent);
		} catch (JsonProcessingException e) {
			System.err.println("Error has occured while converting json to object: %s".formatted(e.getMessage()));
		}
	}
}
