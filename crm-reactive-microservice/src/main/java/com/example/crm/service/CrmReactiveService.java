package com.example.crm.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.example.crm.document.Customer;
import com.example.crm.events.CustomerAcquiredEvent;
import com.example.crm.events.CustomerReleasedEvent;
import com.example.crm.repository.CustomerReactiveRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CrmReactiveService {
	private final CustomerReactiveRepository customerReactiveRepository;
	private final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;
	private final ObjectMapper objectMapper;
	private final ReactiveWebSocketService reactiveWebSocketService;
	
	public CrmReactiveService(CustomerReactiveRepository customerReactiveRepository, ObjectMapper objectMapper, ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate, ReactiveWebSocketService reactiveWebSocketService) {
		this.customerReactiveRepository = customerReactiveRepository;
		this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
		this.objectMapper = objectMapper;
		this.reactiveWebSocketService = reactiveWebSocketService;
	}

	public Mono<Customer> findById(String identity) {
		return customerReactiveRepository.findById(identity);
	}

	public Flux<Customer> findAllByPage(int pageNo, int pageSize) {
		return customerReactiveRepository.findAll(PageRequest.of(pageNo, pageSize));
	}

	public Mono<Customer> acquire(Customer customer) {
		return customerReactiveRepository.insert(customer)
				                         .doOnSuccess(_ -> sendCustomerAcquiredEvent(customer.getEmail()));
	}


	public Mono<Customer> update(String identity, Customer customer) {
		return customerReactiveRepository.save(customer);
	}

	public Mono<Customer> release(String identity) {
		return customerReactiveRepository.findById(identity)
				                         .doOnSuccess( customer -> {
				                        	 customerReactiveRepository.deleteById(identity)
				                        	                           .doOnSuccess( _ -> sendCustomerReleasedEvent(customer.getEmail()));
				                         });
	}

	private void sendCustomerAcquiredEvent(String email) {
		try {
			var event = new CustomerAcquiredEvent(email);
			var eventAsJson = objectMapper.writeValueAsString(event);
			reactiveKafkaProducerTemplate.send("crm-events",eventAsJson)
			                             .doOnSuccess( _ -> reactiveWebSocketService.reactiveSendMessage(eventAsJson).subscribe())
			                             .subscribe( _ -> {
			                            	 System.out.println("The event has been sent successfully to the Kafka server.");
			                             });
		}catch (Exception e) {
			System.out.println("An error has occured: %s".formatted(e.getMessage()));
		}
	}
	
	private void sendCustomerReleasedEvent(String email) {
		try {
			var event = new CustomerReleasedEvent(email);
			var eventAsJson = objectMapper.writeValueAsString(event);
			reactiveKafkaProducerTemplate.send("crm-events",eventAsJson)
			.doOnSuccess( _ -> reactiveWebSocketService.reactiveSendMessage(eventAsJson).subscribe())
			.subscribe( _ -> {
				System.out.println("The event has been sent successfully to the Kafka server.");
			});
		}catch (Exception e) {
			System.out.println("An error has occured: %s".formatted(e.getMessage()));
		}
	}
}
