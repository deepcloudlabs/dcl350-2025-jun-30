package com.example.crm.events;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class CrmEvent {
	private final CrmEventType eventType;
	private final String eventId = UUID.randomUUID().toString();
	private final LocalDateTime timestamp = LocalDateTime.now();
	private final String email;

	public CrmEvent(CrmEventType eventType, String email) {
		this.eventType = eventType;
		this.email = email;
	}

	public CrmEventType getEventType() {
		return eventType;
	}

	public String getEventId() {
		return eventId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getEmail() {
		return email;
	}

}
