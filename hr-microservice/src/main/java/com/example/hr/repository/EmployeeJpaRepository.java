package com.example.hr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hr.entity.EmployeeEntity;

public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, String> {
     Optional<EmployeeEntity> findOneByEmail(String email);
     Optional<EmployeeEntity> findOneByIban(String iban);
     List<EmployeeEntity> findAllByBirthYearBetween(Pageable pageable, int fromYear,int toYear);
     Optional<EmployeeEntity> findTopByOrderByBirthYearAsc();
     Optional<EmployeeEntity> findTopByOrderBySalaryDesc();
     List<EmployeeEntity> findFirst10ByOrderBySalaryDesc();
     // JPA Query Language (JPQL)
     @Query(nativeQuery = false, value = """		
 		select e from EmployeeEntity e 
 		where e.birthYear between :fromYear and :endYear
     """)
     List<EmployeeEntity> bulGetir(int fromYear,int toYear);
}
