package com.example.hr.domain;

import com.example.ddd.ValueObject;

@ValueObject
public record Rate(double value) {
	public static Rate of(double value) {
		if (value <= 0.)
			throw new IllegalArgumentException("Rate cannot be zero or negative: %d".formatted(value));
		return new Rate(value);
	}

	public double toPercent() {
		return (1.0 + value);
	}
}
