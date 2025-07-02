package com.example.hr.adapter;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.hexagonal.Adapter;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.entity.EmployeeEntity;
import com.example.hr.repository.EmployeeJpaRepository;
import com.example.hr.repository.EmployeeRepository;

@Repository
@Adapter(to = EmployeeRepository.class, using = EmployeeJpaRepository.class)
public class EmployeeRepositoryJpaAdapter implements EmployeeRepository {
	private final EmployeeJpaRepository employeeJpaRepository;
	private final ModelMapper modelMapper;

	public EmployeeRepositoryJpaAdapter(EmployeeJpaRepository employeeJpaRepository, ModelMapper modelMapper) {
		this.employeeJpaRepository = employeeJpaRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public boolean exists(TcKimlikNo identity) {
		return employeeJpaRepository.existsById(identity.getValue());
	}

	@Override
	@Transactional
	public Employee persist(Employee employee) {
		var entity = modelMapper.map(employee, EmployeeEntity.class);
		var persistedEntity = employeeJpaRepository.save(entity);
		return modelMapper.map(persistedEntity, Employee.class);
	}

	@Override
	public Optional<Employee> findByIdentity(TcKimlikNo identity) {
		return employeeJpaRepository.findById(identity.getValue()).map( entity -> modelMapper.map(entity, Employee.class));
	}

	@Override
	@Transactional
	public Optional<Employee> remove(TcKimlikNo identity) {
		var entity = employeeJpaRepository.findById(identity.getValue());
		entity.ifPresent(employeeJpaRepository::delete);				           
		return entity.map(deleteEntity -> modelMapper.map(deleteEntity, Employee.class));
	}

}
