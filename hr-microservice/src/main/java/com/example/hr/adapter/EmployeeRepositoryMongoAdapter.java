package com.example.hr.adapter;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.example.hexagonal.Adapter;
import com.example.hr.document.EmployeeDocument;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.repository.EmployeeJpaRepository;
import com.example.hr.repository.EmployeeMongoRepository;
import com.example.hr.repository.EmployeeRepository;

@Repository
@Adapter(to = EmployeeRepository.class, using = EmployeeJpaRepository.class)
@ConditionalOnProperty(name="persistenceStrategy", havingValue = "mongodb")
public class EmployeeRepositoryMongoAdapter implements EmployeeRepository {
	private final EmployeeMongoRepository employeeMongoRepository;
	private final ModelMapper modelMapper;

	public EmployeeRepositoryMongoAdapter(EmployeeMongoRepository employeeMongoRepository, ModelMapper modelMapper) {
		this.employeeMongoRepository = employeeMongoRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public boolean exists(TcKimlikNo identity) {
		return employeeMongoRepository.existsById(identity.getValue());
	}

	@Override
	public Employee persist(Employee employee) {
		var entity = modelMapper.map(employee, EmployeeDocument.class);
		var persistedEntity = employeeMongoRepository.insert(entity);
		return modelMapper.map(persistedEntity, Employee.class);
	}

	@Override
	public Optional<Employee> findByIdentity(TcKimlikNo identity) {
		return employeeMongoRepository.findById(identity.getValue()).map( entity -> modelMapper.map(entity, Employee.class));
	}

	@Override
	public Optional<Employee> remove(TcKimlikNo identity) {
		var entity = employeeMongoRepository.findById(identity.getValue());
		entity.ifPresent(employeeMongoRepository::delete);				           
		return entity.map(deleteEntity -> modelMapper.map(deleteEntity, Employee.class));
	}

}
