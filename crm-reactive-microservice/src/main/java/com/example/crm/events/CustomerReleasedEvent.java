package com.example.crm.events;

public class CustomerReleasedEvent extends CrmEvent {

	public CustomerReleasedEvent(String email) {
		super(CrmEventType.CUSTOMER_RELEASED_EVENT, email);
	}

}
