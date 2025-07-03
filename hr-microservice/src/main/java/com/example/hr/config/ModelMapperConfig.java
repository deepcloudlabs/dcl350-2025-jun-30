package com.example.hr.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hr.document.EmployeeDocument;
import com.example.hr.domain.Employee;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeQLResponse;
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
				entity.setBirthYear(employee.getBirthYear().value());
				entity.setDepartments(employee.getDepartments().getDepartments());
				entity.setPhoto(employee.getPhoto().getValues());
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

	
	private static final Converter<Employee,EmployeeDocument> Employee2EmployeeDocumentConverter = 
			context -> {
				var employee = context.getSource();
				var entity = new EmployeeDocument();
				entity.setIdentity(employee.getIdentity().getValue()); 
				entity.setFirstName(employee.getFullName().firstName());
				entity.setLastName(employee.getFullName().lastName());
				entity.setEmail(employee.getEmail().value());
				entity.setIban(employee.getIban().getValue()); 
				entity.setSalary(employee.getSalary().value());
				entity.setCurrency(employee.getSalary().currency()); 
				entity.setJobStyle(employee.getJobStyle());
				entity.setBirthYear(employee.getBirthYear().value());
				entity.setDepartments(employee.getDepartments().getDepartments());
				entity.setPhoto(employee.getPhoto().toBase64());
				return entity;		
			};		
			
	private static final Converter<EmployeeDocument,Employee> EmployeeDocument2EmployeeConverter =
			  context -> {
				  var document = context.getSource();
				  return new Employee.Builder()
					.identity(document.getIdentity())
					.fullName(document.getFirstName(), document.getLastName())
					.birthYear(document.getBirthYear())
					.departments(document.getDepartments())
					.jobStyle(document.getJobStyle())	
					.salary(document.getSalary(), document.getCurrency())
					.email(document.getEmail())
					.iban(document.getIban())
					.photo(document.getPhoto())
					.build();			  
			  };			  
			  
	private static final Converter<Employee,EmployeeQLResponse> Employee2EmployeeQLResponseConverter = 
			context -> {
				var employee = context.getSource();
				return new EmployeeQLResponse(
						employee.getIdentity().getValue(), 
						employee.getFullName().firstName(), 
						employee.getFullName().lastName(), 
						employee.getEmail().value(), 
						employee.getIban().getValue(), 
						employee.getSalary().value(), 
						employee.getSalary().currency(), 
						employee.getJobStyle(), 
						employee.getDepartments().getDepartments(),
						employee.getBirthYear().value(),
						employee.getPhoto().toBase64()
				); 
			};				  
			
	@Bean
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.addConverter(EmployeeEntity2EmployeeResponseConverter, Employee.class, EmployeeResponse.class);
		modelMapper.addConverter(HireEmployeeRequest2EmployeeConverter, HireEmployeeRequest.class, Employee.class);
		modelMapper.addConverter(Employee2HireEmployeeResponseConverter, Employee.class, HireEmployeeResponse.class);
		modelMapper.addConverter(Employee2EmployeeEntityConverter, Employee.class, EmployeeEntity.class);
		modelMapper.addConverter(EmployeeEntity2EmployeeConverter, EmployeeEntity.class, Employee.class);
		modelMapper.addConverter(Employee2EmployeeDocumentConverter, Employee.class, EmployeeDocument.class);
		modelMapper.addConverter(EmployeeDocument2EmployeeConverter, EmployeeDocument.class, Employee.class);
		modelMapper.addConverter(Employee2EmployeeQLResponseConverter, Employee.class, EmployeeQLResponse.class);
		return modelMapper;
	}
}
