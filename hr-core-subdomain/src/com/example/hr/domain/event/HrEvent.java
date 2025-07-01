package com.example.hr.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.hr.domain.TcKimlikNo;

public sealed abstract class HrEvent permits EmployeeHiredEvent,EmployeeFiredEvent {
	private final HrEventType eventType;
	private final String eventId = UUID.randomUUID().toString();
	private final LocalDateTime timestamp = LocalDateTime.now();
	private final TcKimlikNo identity;

	public HrEvent(HrEventType eventType, TcKimlikNo identity) {
		this.eventType = eventType;
		this.identity = identity;
	}

	public HrEventType getEventType() {
		return eventType;
	}

	public String getEventId() {
		return eventId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public TcKimlikNo getIdentity() {
		return identity;
	}

}
