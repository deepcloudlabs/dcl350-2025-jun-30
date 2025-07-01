package com.example.hr.dto.request;

import java.util.List;

import com.example.hr.domain.Department;
import com.example.hr.domain.FiatCurrency;
import com.example.hr.domain.JobStyle;

public record HireEmployeeRequest(
		String identityNo,
		String firstName,
		String lastName,
		String email,
		String iban,
		double salary,
		FiatCurrency currency,
		JobStyle jobStyle,
		List<Department> departments,
		String photo
		) {

}
