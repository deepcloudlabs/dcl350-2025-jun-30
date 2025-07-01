package com.example.hr.domain;

import java.util.List;
import java.util.Objects;

import com.example.ddd.Entity;
// Ubiquitous Language: Distilled Domain Knowledge -> Domain Expert -> Analysis Model
// TcKimlikNo, FullName, Iban, Salary, ...
// [Business] Domain -> Sub-domain(s)
//                      Core/Generic/Supporting
// Sub-domain --------> Core Sub-domain --- design --------> Bounded Context (BC) --------> Aggregate
//            1    1..*                            1    1..*                      1    1..*
// Aggregate, Entity, Value-Object
// Entity: i. Identity -> TcKimlikNo ii. Persistent iii. Mutable iv. Behavior: increaseSalary, promote, ...
// 1. Validation
// 2. Business Rule
// 3. Invariants
// 4. Constraints
// 5. Regulations
// 6. Policy

// Aggregate -> Entity Root
// Bounded Context -> Aggregate
@Entity(identity = "identity", aggregate = true)
public class Employee {
	private static final Salary MIN_WAGE = Salary.valueOf(25_000);
	private TcKimlikNo identity;
	private FullName fullName;
	private Email email;
	private Iban iban;
	private Salary salary;
	private Departments departments;
	private JobStyle jobStyle;
	private BirthYear birthYear;
	private Photo photo;

	private Employee(Builder builder) {
		this.identity = builder.identity;
		this.fullName = builder.fullName;
		this.iban = builder.iban;
		this.salary = builder.salary;
		this.email = builder.email;
		this.departments = builder.departments;
		this.jobStyle = builder.jobStyle;
		this.birthYear = builder.birthYear;
		this.photo = builder.photo;
	}

	public TcKimlikNo getIdentity() {
		return identity;
	}

	public FullName getFullName() {
		return fullName;
	}

	public Iban getIban() {
		return iban;
	}

	public Salary getSalary() {
		return salary;
	}

	public Departments getDepartments() {
		return departments;
	}

	public JobStyle getJobStyle() {
		return jobStyle;
	}

	public BirthYear getBirthYear() {
		return birthYear;
	}

	public Photo getPhoto() {
		return photo;
	}

	public Email getEmail() {
		return email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(identity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(identity, other.identity);
	}

	@Override
	public String toString() {
		return "Employee [identity=" + identity + ", fullName=" + fullName + ", iban=" + iban + ", salary=" + salary
				+ ", departments=" + departments + ", jobStyle=" + jobStyle + ", birthYear=" + birthYear + "]";
	}

	public static class Builder {
		private TcKimlikNo identity;
		private FullName fullName;
		private Iban iban;
		private Salary salary;
		private Departments departments;
		private JobStyle jobStyle;
		private BirthYear birthYear;
		private Photo photo;
		private Email email;

		public Builder identity(String value) {
			this.identity = TcKimlikNo.valueOf(value);
			return this;
		}

		public Builder fullName(String firstName, String lastName) {
			this.fullName = FullName.of(firstName, lastName);
			return this;
		}

		public Builder iban(String value) {
			this.iban = Iban.valueOf(value);
			return this;
		}

		public Builder salary(double value) {
			this.salary = Salary.valueOf(value, FiatCurrency.TL);
			return this;
		}

		public Builder email(String value) {
			this.email = new Email(value);
			return this;
		}

		public Builder salary(double value, FiatCurrency currency) {
			this.salary = Salary.valueOf(value, currency);
			return this;
		}

		public Builder salary(double value, String currency) {
			this.salary = Salary.valueOf(value, FiatCurrency.valueOf(currency));
			return this;
		}

		public Builder departments(List<Department> values) {
			this.departments = Departments.valueOf(values);
			return this;
		}

		public Builder departments(Department... values) {
			this.departments = Departments.valueOf(values);
			return this;
		}

		public Builder jobStyle(String value) {
			this.jobStyle = JobStyle.valueOf(value);
			return this;
		}

		public Builder jobStyle(JobStyle value) {
			this.jobStyle = value;
			return this;
		}

		public Builder photo(String base64Values) {
			this.photo = Photo.valueOf(base64Values);
			return this;
		}

		public Builder photo(byte[] values) {
			this.photo = Photo.valueOf(values);
			return this;
		}

		public Builder birthYear(int value) {
			this.birthYear = new BirthYear(value);
			return this;
		}

		public Employee build() {
			// 1. Validation
			// 2. Business Rule
			// 3. Invariants
			// 4. Constraints
			// 5. Regulations
			// 6. Policy
			// POLICY-101
			if (this.departments.contains(Department.IT)) {
				if (salary.lessThan(MIN_WAGE.multiply(5.0)) && this.jobStyle.equals(JobStyle.FULL_TIME)) {
					throw new IllegalStateException(
							"IT Department Employees cannot have salary less than 5 times the minimum wage (POL101)");
				}
			}
			return new Employee(this);
		}
	}

	public void increaseSalary(Rate rate) {
		var newSalary = Salary.valueOf(this.salary.value() * rate.toPercent(), this.salary.currency());
		// POLICY-101
		if (this.departments.contains(Department.IT)) {
			if (newSalary.lessThan(MIN_WAGE.multiply(5.0)) && this.jobStyle.equals(JobStyle.FULL_TIME)) {
				throw new IllegalStateException(
						"IT Department Employees cannot have salary less than 5 times the minimum wage (POL101)");
			}
		}
		this.salary = newSalary;
	}

	public void promote(Rate rate, Department department) {
		this.departments = this.departments.addDepartment(department);
		this.increaseSalary(rate);
	}
}
