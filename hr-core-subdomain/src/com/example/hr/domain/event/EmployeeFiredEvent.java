package com.example.hr.domain.event;

import com.example.hr.domain.Employee;

public final class EmployeeFiredEvent extends HrEvent {
	private final Employee firedEmployee;

	public EmployeeFiredEvent(Employee removedEmployee) {
		super(HrEventType.EMPLOYEE_FIRED_EVENT, removedEmployee.getIdentity());
		this.firedEmployee = removedEmployee;
	}

	public Employee getFiredEmployee() {
		return firedEmployee;
	}

}
