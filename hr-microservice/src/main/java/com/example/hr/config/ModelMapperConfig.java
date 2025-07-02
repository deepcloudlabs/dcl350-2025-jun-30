package com.example.hr.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hr.domain.Employee;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;
import com.example.hr.entity.EmployeeEntity;


@Configuration
public class ModelMapperConfig {

	private static final Converter<Employee,EmployeeResponse> EmployeeEntity2EmployeeResponseConverter = 
		context -> {
			var employee = context.getSource();
			return new EmployeeResponse(
					employee.getIdentity().getValue(), 
					employee.getFullName().firstName(), 
					employee.getFullName().lastName(), 
					employee.getEmail().value(), 
					employee.getIban().getValue(), 
					employee.getSalary().value(), 
					employee.getSalary().currency(), 
					employee.getJobStyle(), 
					employee.getDepartments().getDepartments()
			); 
			
		};
		
	private static final Converter<Employee,HireEmployeeResponse> Employee2HireEmployeeResponseConverter = 
			context -> {
				var employee = context.getSource();
				return new HireEmployeeResponse(
						employee.getIdentity().getValue(), 
						employee.getFullName().firstName(), 
						employee.getFullName().lastName(), 
						employee.getEmail().value(), 
						employee.getIban().getValue(), 
						employee.getSalary().value(), 
						employee.getSalary().currency(), 
						employee.getJobStyle(), 
						employee.getDepartments().getDepartments()
				); 
				
			};		

	private static final Converter<HireEmployeeRequest,Employee> HireEmployeeRequest2EmployeeConverter =
	  context -> {
		  var request = context.getSource();
		  return new Employee.Builder()
			.identity(request.identityNo())
			.fullName(request.firstName(), request.lastName())
			.birthYear(request.birthYear())
			.departments(request.departments())
			.jobStyle(request.jobStyle())	
			.salary(request.salary(), request.currency())
			.email(request.email())
			.iban(request.iban())
			.photo(request.photo())
			.build();			  
	  };
	
	private static final Converter<Employee,EmployeeEntity> Employee2EmployeeEntityConverter = 
			context -> {
				var employee = context.getSource();
				var entity = new EmployeeEntity();
				entity.setIdentity(employee.getIdentity().getValue()); 
				entity.setFirstName(employee.getFullName().firstName());
				entity.setLastName(employee.getFullName().lastName());
				entity.setEmail(employee.getEmail().value());
				entity.setIban(employee.getIban().getValue()); 
				entity.setSalary(employee.getSalary().value());
				entity.setCurrency(employee.getSalary().currency()); 
				entity.setJobStyle(employee.getJobStyle());
				entity.setDepartments(employee.getDepartments().getDepartments());
				return entity;		
			};		
			
	private static final Converter<EmployeeEntity,Employee> EmployeeEntity2EmployeeConverter =
			  context -> {
				  var entity = context.getSource();
				  return new Employee.Builder()
					.identity(entity.getIdentity())
					.fullName(entity.getFirstName(), entity.getLastName())
					.birthYear(entity.getBirthYear())
					.departments(entity.getDepartments())
					.jobStyle(entity.getJobStyle())	
					.salary(entity.getSalary(), entity.getCurrency())
					.email(entity.getEmail())
					.iban(entity.getIban())
					.photo(entity.getPhoto())
					.build();			  
			  };
	  
	@Bean
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.addConverter(EmployeeEntity2EmployeeResponseConverter, Employee.class, EmployeeResponse.class);
		modelMapper.addConverter(HireEmployeeRequest2EmployeeConverter, HireEmployeeRequest.class, Employee.class);
		modelMapper.addConverter(Employee2HireEmployeeResponseConverter, Employee.class, HireEmployeeResponse.class);
		modelMapper.addConverter(Employee2EmployeeEntityConverter, Employee.class, EmployeeEntity.class);
		modelMapper.addConverter(EmployeeEntity2EmployeeConverter, EmployeeEntity.class, Employee.class);
		return modelMapper;
	}
}
