package com.example.crm.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ReactiveWebSocketService implements WebSocketHandler {
	private final Map<String,WebSocketSession> sessions = new ConcurrentHashMap<>();
	
	@Override
	public Mono<Void> handle(WebSocketSession session) {
		String sessionId = session.getId();
		sessions.put(sessionId , session);
		session.receive().doFinally( sigType -> {
			switch (sigType) {
				case ON_COMPLETE -> {
					sessions.remove(sessionId);
				}
				case ON_NEXT -> {
					Flux.fromIterable(sessions.entrySet())
					    .filter(entry -> !entry.getKey().equals(sessionId))
					    .parallel()
					    .runOn(Schedulers.boundedElastic())
					    .doOnNext( entry -> {
					    	entry.getValue().send(Mono.just(session.textMessage("Hello Mars!"))).subscribe();
					    });
				}
				default -> {
					throw new IllegalArgumentException("Unexpected value: " + sigType);				
				}
			}
		});
		return Mono.empty();
	}

	public Mono<Void> reactiveSendMessage(String message){
		Flux.fromIterable(sessions.entrySet())
	    .parallel()
	    .runOn(Schedulers.boundedElastic())
	    .doOnNext( entry -> {
	    	var session = entry.getValue();
	    	entry.getValue().send(Mono.just(session.textMessage(message))).subscribe();
	    });
		return Mono.empty();
	}
}
