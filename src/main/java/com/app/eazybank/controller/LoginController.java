package com.app.eazybank.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.eazybank.entity.Customer;
import com.app.eazybank.service.CustomerService;

@RestController
public class LoginController {
	
	private CustomerService customerService;

	public LoginController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	} 
	
	@PostMapping("/register")
	public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.createCustomer(customer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
								   .path("/{id}")
								   .buildAndExpand(savedCustomer.getId())
								   .toUri();
		return ResponseEntity.created(location).body(savedCustomer);
	}
}
