package com.example.hr.domain;

import java.util.List;

import com.example.ddd.Entity;
// Ubiquitous Language: Distilled Domain Knowledge -> Domain Expert
// TcKimlikNo, FullName, Iban, Salary, ...
// [Business] Domain -> Sub-domain(s)
//                      Core/Generic/Supporting
// Sub-domain --------> Core Sub-domain --- design --------> Bounded Context (BC)
//            1    1..*                            1    1..*                 
// Aggregate, Entity, Value-Object
// Entity: i. Identity -> TcKimlikNo ii. Persistent iii. Mutable iv. Behavior: increaseSalary, promote, ...
// 1. Validation
// 2. Business Rule
// 3. Invariants
// 4. Constraints
// 5. Regulations
@Entity(identity="identity")
public class Employee {
    private TcKimlikNo identity;
    private FullName firstName;
    private Iban iban;
    private Salary salary;
    private Departments department;
    private JobStyle jobStyle;
    private BirthYear birthYear;
    private Photo photo;
    
    public void increaseSalary(Rate rate) {}
    public void promote(Rate rate, Department department) {}
}
