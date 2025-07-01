package com.example.hr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.hexagonal.Adapter;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;
import com.example.hr.service.HrService;

@RestController
@RequestScope
@RequestMapping("/employees")
@CrossOrigin
@Validated
@Adapter
public class HrRestController {
	private final HrService hrService;

	public HrRestController(HrService hrService) {
		this.hrService = hrService;
	}

	// GET http://localhost:7100/hr/api/v1/employees/11111111110
	@GetMapping("{identityNo}")
	public EmployeeResponse getEmployeeInformation(@PathVariable String identityNo) {
		return hrService.findEmployeeById(identityNo);
	}

	@PostMapping
	public HireEmployeeResponse hireEmployee(@RequestBody HireEmployeeRequest request) {
		return hrService.hireEmployee(request);
	}

	@DeleteMapping("{identityNo}")
	public EmployeeResponse fireEmployee(@PathVariable String identityNo) {
		return hrService.fireEmployee(identityNo);
	}
}
