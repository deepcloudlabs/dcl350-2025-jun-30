package com.example.hr.dto.response;

import java.util.List;

import com.example.hr.domain.Department;
import com.example.hr.domain.FiatCurrency;
import com.example.hr.domain.JobStyle;

public record HireEmployeeResponse(		
		String identityNo,
		String firstName,
		String lastName,
		String email,
		String iban,
		double salary,
		FiatCurrency currency,
		JobStyle jobStyle,
		List<Department> departments) {

}
