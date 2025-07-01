package com.example.hr.domain.event;

import com.example.hr.domain.TcKimlikNo;

public final class EmployeeHiredEvent extends HrEvent {

	public EmployeeHiredEvent(TcKimlikNo identity) {
		super(HrEventType.EMPLOYEE_HIRED_EVENT, identity);
	}

}
