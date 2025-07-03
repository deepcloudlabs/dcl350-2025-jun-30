package com.example.hr.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.hr.application.HrApplication;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeQLResponse;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;

@Service
public class HrService {
	private final HrApplication hrApplication;
	private final ModelMapper modelMapper;
	
	public HrService(HrApplication hrApplication, ModelMapper modelMapper) {
		this.hrApplication = hrApplication;
		this.modelMapper = modelMapper;
	}

	public EmployeeResponse findEmployeeById(String identityNo) {
		var employee = hrApplication.getEmployeeInformation(TcKimlikNo.valueOf(identityNo))
				.orElseThrow(() -> new IllegalArgumentException("Cannot find employee with the given identity no: %s".formatted(identityNo)));
		return modelMapper.map(employee, EmployeeResponse.class);
	}

	//@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY)
	public HireEmployeeResponse hireEmployee(HireEmployeeRequest request) {
		var employee = modelMapper.map(request, Employee.class);
		var hiredEmployee = hrApplication.hireEmployee(employee);
		return modelMapper.map(hiredEmployee, HireEmployeeResponse.class);
	}

	// @Transactional(propagation = Propagation.NEVER)
	public EmployeeResponse fireEmployee(String identityNo) {
		var firedEmployee = hrApplication.fireEmployee(TcKimlikNo.valueOf(identityNo))
				.orElseThrow(() -> new IllegalArgumentException("Cannot find employee to fire: %s".formatted(identityNo)));
		return modelMapper.map(firedEmployee, EmployeeResponse.class);
	}

	public EmployeeQLResponse getEmployeeById(String identity) {
		var employee = hrApplication.getEmployeeInformation(TcKimlikNo.valueOf(identity))
				.orElseThrow(() -> new IllegalArgumentException("Cannot find employee with the given identity no: %s".formatted(identity)));
		return modelMapper.map(employee, EmployeeQLResponse.class);
	}

}
