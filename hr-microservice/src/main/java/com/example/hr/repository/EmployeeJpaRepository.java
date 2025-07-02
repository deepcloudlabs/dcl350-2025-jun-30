package com.example.hr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hr.entity.EmployeeEntity;

public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, String> {
     Optional<EmployeeEntity> findOneByEmail(String email);
     Optional<EmployeeEntity> findOneByIban(String iban);
     List<EmployeeEntity> findAllByBirthYearBetween(int fromYear,int toYear);
     Optional<EmployeeEntity> findTopByOrderByBirthYearAsc();
     Optional<EmployeeEntity> findTopByOrderBySalaryDesc();
     List<EmployeeEntity> findFirst10ByOrderBySalaryDesc();
}
