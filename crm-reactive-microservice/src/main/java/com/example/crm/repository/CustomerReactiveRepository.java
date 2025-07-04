package com.example.crm.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.crm.document.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerReactiveRepository extends ReactiveMongoRepository<Customer, String>{
	Flux<Customer> findAllByAddressesCountry(Pageable page,String country);
	Mono<Customer> findOneByEmail(String email);
	@Query("{}")
	Flux<Customer> findAll(PageRequest page);
}
