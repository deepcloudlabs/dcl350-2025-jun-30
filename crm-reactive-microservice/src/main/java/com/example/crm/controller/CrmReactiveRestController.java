package com.example.crm.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crm.document.Customer;
import com.example.crm.service.CrmReactiveService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
@CrossOrigin
public class CrmReactiveRestController {
	private final CrmReactiveService crmReactiveService;
	
	public CrmReactiveRestController(CrmReactiveService crmReactiveService) {
		this.crmReactiveService = crmReactiveService;
	}

	// Query
	@GetMapping("{identity}")
	public Mono<Customer> getCustomerByIdentity(@PathVariable String identity){
		return crmReactiveService.findById(identity);
	}
	
	@GetMapping(params = {"pageNo","pageSize"})
	public Flux<Customer> getCustomersByPage(@RequestParam int pageNo,@RequestParam int pageSize){
		return crmReactiveService.findAllByPage(pageNo,pageSize);
	}
	
	// Command
	@PostMapping
	public Mono<Customer> acquireCustomer(@RequestBody Customer customer){
		return crmReactiveService.acquire(customer);
	}
	
	@PutMapping("{identity}")
	public Mono<Customer> updateCustomer(@PathVariable String identity,@RequestBody Customer customer){
		return crmReactiveService.update(identity,customer);
	}
	
	@DeleteMapping("{identity}")
	public Mono<Customer> releaseCustomer(@PathVariable String identity){
		return crmReactiveService.release(identity);
	}
}
