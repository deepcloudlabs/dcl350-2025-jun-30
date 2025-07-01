package com.example.hr.domain;

import java.util.Objects;

import com.example.ddd.ValueObject;

@ValueObject
public record FullName(String firstName, String lastName) {

	public static FullName of(String firstName, String lastName) {
		Objects.requireNonNull(firstName);
		Objects.requireNonNull(lastName);
		return new FullName(firstName, lastName);
	}
}
