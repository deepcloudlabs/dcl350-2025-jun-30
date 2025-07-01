package com.example.hr.domain;

import java.util.Objects;

import com.example.ddd.ValueObject;

@ValueObject
public record Salary(double value, FiatCurrency currency) {
	private static final double MIN_WAGE = 25_000;

	public static Salary valueOf(double value) {
		return valueOf(value, FiatCurrency.TL);
	}
	
	public static Salary valueOf(double value, FiatCurrency currency) {
		// 1. Validation
		// 2. Business Rule
		// 3. Invariants
		// 4. Constraints
		if (value <= 0)
			throw new IllegalArgumentException("Salary cannot be negative (VALIDATION-200)");
		Objects.requireNonNull(currency, "Currency cannot be null");
		// 5. Regulations
		if (value < MIN_WAGE)
			throw new IllegalArgumentException("Salary cannot be less than Minimum Wage (REG-100)");
		// 6. Policies
		
		return new Salary(value, currency);
	}

	public Salary multiply(double d) {
		return Salary.valueOf(value*d, currency);
	}

	public boolean lessThan(Salary other) {
		return this.value < other.value;
	}
}
