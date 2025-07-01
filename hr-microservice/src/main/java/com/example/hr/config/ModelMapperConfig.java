package com.example.hr.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hr.domain.Employee;
import com.example.hr.dto.response.EmployeeResponse;


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
	@Bean
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.addConverter(EmployeeEntity2EmployeeResponseConverter, Employee.class, EmployeeResponse.class);
		return modelMapper;
	}
}
