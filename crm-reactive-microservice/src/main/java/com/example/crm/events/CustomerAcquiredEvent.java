package com.example.crm.events;

public class CustomerAcquiredEvent extends CrmEvent {

	public CustomerAcquiredEvent(String email) {
		super(CrmEventType.CUSTOMER_ACQUIRED_EVENT, email);
	}

}
