package com.example.hr.dto.request;

import java.util.List;

import com.example.hr.domain.Department;
import com.example.hr.domain.FiatCurrency;
import com.example.hr.domain.JobStyle;
import com.example.validation.Base64Encoded;
import com.example.validation.Iban;
import com.example.validation.TcKimlikNo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HireEmployeeRequest(
		@TcKimlikNo
		String identityNo,
		@NotBlank
		String firstName,
		@NotBlank
		String lastName,
		@Email
		String email,
		@Max(2010)
		int birthYear,
		@Iban
		String iban,
		@Min(25_000)
		double salary,
		@NotNull
		FiatCurrency currency,
		@NotNull
		JobStyle jobStyle,
		List<Department> departments,
		@Base64Encoded
		String photo
		) {

}
