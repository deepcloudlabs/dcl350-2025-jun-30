package com.example.hr.controller;

import org.springframework.transaction.annotation.Transactional;
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
import com.example.validation.TcKimlikNo;

@RestController // Meta-Annotation
@RequestScope
@RequestMapping("/employees")
@CrossOrigin
@Validated
@Adapter(using = HrService.class)
public class HrRestController {
	private final HrService hrService;

	public HrRestController(HrService hrService) {
		this.hrService = hrService;
		System.err.println(hrService.getClass().getName());
	}

	// GET http://localhost:7100/hr/api/v1/employees/11111111110
	@GetMapping("{identityNo}")
	public EmployeeResponse getEmployeeInformation(@PathVariable @TcKimlikNo String identityNo) {
		return hrService.findEmployeeById(identityNo);
	}

	@PostMapping
	public HireEmployeeResponse hireEmployee(@RequestBody @Validated HireEmployeeRequest request) {
		return hrService.hireEmployee(request);
	}

	@DeleteMapping(value = "{identityNo}")
	@Transactional
	public EmployeeResponse fireEmployee(@PathVariable @Validated String identityNo) {
		return hrService.fireEmployee(identityNo);
	}

}
