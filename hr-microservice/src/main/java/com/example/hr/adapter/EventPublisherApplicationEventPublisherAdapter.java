package com.example.hr.adapter;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.hexagonal.Adapter;
import com.example.hr.domain.event.HrEvent;
import com.example.hr.infra.EventPublisher;

@Service
@Adapter(to = EventPublisher.class, using=ApplicationEventPublisher.class )
public class EventPublisherApplicationEventPublisherAdapter implements EventPublisher<HrEvent> {
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public EventPublisherApplicationEventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publish(HrEvent event) {
		applicationEventPublisher.publishEvent(event);
	}

}
