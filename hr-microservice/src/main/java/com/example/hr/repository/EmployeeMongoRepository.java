package com.example.hr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.hr.document.EmployeeDocument;

public interface EmployeeMongoRepository extends MongoRepository<EmployeeDocument, String> {
     Optional<EmployeeDocument> findOneByEmail(String email);
     Optional<EmployeeDocument> findOneByIban(String iban);
     List<EmployeeDocument> findAllByBirthYearBetween(Pageable pageable, int fromYear,int toYear);
     Optional<EmployeeDocument> findTopByOrderByBirthYearAsc();
     Optional<EmployeeDocument> findTopByOrderBySalaryDesc();
     List<EmployeeDocument> findFirst10ByOrderBySalaryDesc();
     // MongoDB Native Query
     @Query("""		
 		{'birthYear': {$gte: ?1, $lt: ?2}}
     """)
     List<EmployeeDocument> bulGetir(Pageable pageable,int fromYear,int toYear);
}
