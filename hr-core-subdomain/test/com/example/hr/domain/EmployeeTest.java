package com.example.hr.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class EmployeeTest {

	@ParameterizedTest
	@CsvFileSource(resources = "invalid-employees.csv")
	void createEmployeeFails(String identity,String firstName,String lastName,double salary,String currency,String iban,int birthYear,String jobStyle,String departments,String email,String base64Image) {
		assertThrows(IllegalStateException.class, () ->{
			new Employee.Builder()
					.identity(identity)
					.fullName(firstName, lastName)
					.birthYear(birthYear)
					.departments(Arrays.stream(departments.split(":")).map(value -> Department.valueOf(value)).toList())
					.jobStyle(jobStyle)	
					.salary(salary, currency)
					.email(email)
					.iban(iban)
					.photo(base64Image)
					.build();			
		});	
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "valid-employees.csv")
	void createEmployeeSuccessful(String identity,String firstName,String lastName,double salary,String currency,String iban,int birthYear,String jobStyle,String departments,String email,String base64Image) {
		var employee = new Employee.Builder()
			.identity(identity)
			.fullName(firstName, lastName)
			.birthYear(birthYear)
			.departments(Arrays.stream(departments.split(":")).map(value -> Department.valueOf(value)).toList())
			.jobStyle(jobStyle)
			.salary(salary, currency)
			.iban(iban)
			.email(email)
			.photo(base64Image)
			.build();			
		assertAll(
			() -> assertNotNull(employee),
			() -> assertEquals(identity, employee.getIdentity().getValue()),
			() -> assertEquals(firstName, employee.getFullName().firstName()),
			() -> assertEquals(email, employee.getEmail().value()),
			() -> assertEquals(lastName, employee.getFullName().lastName())
		);
	}

}
