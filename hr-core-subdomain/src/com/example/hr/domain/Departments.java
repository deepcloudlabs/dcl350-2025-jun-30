package com.example.hr.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.example.ddd.ValueObject;

@ValueObject
public final class Departments {
	private final List<Department> values;

	private Departments(Collection<Department> values) {
		this.values = List.copyOf(values);
	}

	public static Departments valueOf(Department... values) {
		if (values.length == 0) {
			throw new IllegalArgumentException("Department list cannot be empty!");
		}
		return new Departments(List.of(values));
	}

	public static Departments valueOf(Collection<Department> values) {
		if (values.isEmpty()) {
			throw new IllegalArgumentException("Department list cannot be empty!");
		}
		return new Departments(values);
	}

	public List<Department> getDepartments() {
		return this.values;
	}

	public Departments addDepartment(Department department) {
		var newDepartments = new ArrayList<Department>(this.values);
		newDepartments.add(department);
		return new Departments(newDepartments);
	}

	public Departments removeDepartment(Department department) {
		return new Departments(this.values.stream().filter(dept -> !dept.equals(department)).toList());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(values);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departments other = (Departments) obj;
		return Objects.equals(values, other.values);
	}

	@Override
	public String toString() {
		return "Departments [values=" + values + "]";
	}

	public boolean contains(Department department) {
		return this.values.contains(department);
	}

}
