package com.example.hr.application.business;

import java.util.Optional;
import java.util.function.Consumer;

import com.example.ddd.Application;
import com.example.hr.application.HrApplication;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.domain.event.EmployeeFiredEvent;
import com.example.hr.domain.event.EmployeeHiredEvent;
import com.example.hr.domain.event.HrEvent;
import com.example.hr.infra.EventPublisher;
import com.example.hr.repository.EmployeeRepository;

@Application(port = HrApplication.class)
public class StandardHrApplication implements HrApplication {
	private final EmployeeRepository employeeRepository;
	private final EventPublisher<HrEvent> eventPublisher;

	public StandardHrApplication(EmployeeRepository employeeRepository, EventPublisher<HrEvent> eventPublisher) {
		this.employeeRepository = employeeRepository;
		this.eventPublisher = eventPublisher;
	}

	@Override
	public Employee hireEmployee(Employee employee) {
		TcKimlikNo identity = employee.getIdentity();
		if (employeeRepository.exists(identity)) {
			throw new IllegalStateException("%s already exists.".formatted(identity.getValue()));
		}
		var persistedEmployee = employeeRepository.persist(employee);
		var event = new EmployeeHiredEvent(identity);
		eventPublisher.publish(event);
		return persistedEmployee;
	}

	@Override
	public Optional<Employee> fireEmployee(TcKimlikNo identity) {
		var employee = employeeRepository.findByIdentity(identity);
		Consumer<Employee> removeEmployeeAction = _ -> employeeRepository.remove(identity);
		employee.ifPresent(removeEmployeeAction.andThen( removedEmployee ->{
			var event = new EmployeeFiredEvent(removedEmployee);
			eventPublisher.publish(event);			
		}));
		return employee;
	}

	@Override
	public Optional<Employee> getEmployeeInformation(TcKimlikNo identity) {
		return employeeRepository.findByIdentity(identity);
	}

}
