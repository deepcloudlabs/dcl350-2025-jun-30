package com.example.card.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import jakarta.annotation.PostConstruct;

@Service
public class HrEventWebSocketListener implements WebSocketHandler{
	private final WebSocketClient webSocketClient;
	private final String hrWebSocketServerUrl;
	public HrEventWebSocketListener(WebSocketClient webSocketClient, 
			@Value("${hrWebSocketServerUrl}") String hrWebSocketServerUrl) {
		this.webSocketClient = webSocketClient;
		this.hrWebSocketServerUrl = hrWebSocketServerUrl;
	}

	@PostConstruct
	public void connectoToHrApplication() {
		webSocketClient.execute(this, hrWebSocketServerUrl);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.err.println("Connection successfully established: %s".formatted(session.getId()));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.err.println("New event has arrived from WebSocket: %s".formatted(message.getPayload()));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
		System.err.println("An error has occured in WebSocket connection [%s]: %s".formatted(session.getId(),e.getMessage()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		System.err.println("WebSocket connection [%s] is closed.".formatted(session.getId()));
		webSocketClient.execute(this, hrWebSocketServerUrl);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
}
