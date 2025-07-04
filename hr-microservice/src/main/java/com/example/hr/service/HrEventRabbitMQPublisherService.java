package com.example.hr.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.hr.domain.event.HrEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@ConditionalOnProperty(name = "messagingStrategy", havingValue = "rabbit")
public class HrEventRabbitMQPublisherService {
	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;
	private final String exchangeName;
	
	public HrEventRabbitMQPublisherService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, 
			@Value("${hrEventExchangeName}") String exchangeName) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
		this.exchangeName = exchangeName;
	}

	@EventListener
	public void handleHrEvent(HrEvent event) {
		try {
			var eventAsJson = objectMapper.writeValueAsString(event);
			rabbitTemplate.convertAndSend(exchangeName, "",eventAsJson);
		} catch (JsonProcessingException e) {
			System.err.println("An error has occured while serializing hr event to json: %s".formatted(e.getMessage()));
		}	
	}
}
