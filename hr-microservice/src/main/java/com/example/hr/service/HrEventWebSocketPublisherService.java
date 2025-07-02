package com.example.hr.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.hr.domain.event.HrEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HrEventWebSocketPublisherService implements WebSocketHandler {
	private final Map<String,WebSocketSession> sessions = new ConcurrentHashMap<>();
	private final ObjectMapper objectMapper;
	
	public HrEventWebSocketPublisherService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.put(session.getId(), session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
		sessions.remove(session.getId());
		System.err.println("An error has occured while communicating with the session (%s): %s".formatted(session.getId(),e.getMessage()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		sessions.remove(session.getId());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	@EventListener
	public void handleHrEvent(HrEvent event) {
		try {
			var eventAsJson = objectMapper.writeValueAsString(event);
			var message = new TextMessage(eventAsJson);
			sessions.forEach((_,session) -> {
				try {
					session.sendMessage(message);
				} catch (IOException e) {
					System.err.println("An error has occured while sending the hr event to ws client: %s".formatted(e.getMessage()));
				}
			});
		} catch (JsonProcessingException e) {
			System.err.println("An error has occured while serializing hr event to json: %s".formatted(e.getMessage()));
		}
	}

}
