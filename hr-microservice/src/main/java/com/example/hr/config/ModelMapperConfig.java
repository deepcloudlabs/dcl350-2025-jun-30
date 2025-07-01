package com.example.hr.config;

import java.util.Arrays;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;


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
	
	@Bean
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.addConverter(EmployeeEntity2EmployeeResponseConverter, Employee.class, EmployeeResponse.class);
		modelMapper.addConverter(HireEmployeeRequest2EmployeeConverter, HireEmployeeRequest.class, Employee.class);
		modelMapper.addConverter(Employee2HireEmployeeResponseConverter, Employee.class, HireEmployeeResponse.class);
		return modelMapper;
	}
}
