package com.example.hr.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.hr.dto.response.EmployeeQLResponse;
import com.example.hr.service.HrService;

@Controller
public class EmployeeController {
	private final HrService hrService;
	
	public EmployeeController(HrService hrService) {
		this.hrService = hrService;
	}

	@QueryMapping
	public EmployeeQLResponse employeeById(@Argument String identity) {
			return hrService.getEmployeeById(identity);
	}
}
