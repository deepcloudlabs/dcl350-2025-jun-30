package com.example.hr.repository;

import java.util.Optional;

import com.example.ddd.Repository;
import com.example.hexagonal.Port;
import com.example.hexagonal.PortType;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;

@Port(PortType.DRIVEN_PORT)
@Repository
public interface EmployeeRepository {

	boolean exists(TcKimlikNo identity);

	Employee persist(Employee employee);

	Optional<Employee> findByIdentity(TcKimlikNo identity);
	
	Optional<Employee> remove(TcKimlikNo identity);
	
}
