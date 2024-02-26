package com.app.eazybank.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.eazybank.entity.Customer;
import com.app.eazybank.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
		
	private PasswordEncoder passwordEncoder;

	public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		super();
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Customer createCustomer(Customer customer) {
		if(customerRepository.existsByEmail(customer.getEmail()))
			throw new IllegalArgumentException("Email already exist");
		
		try {
			customer.setPwd(passwordEncoder.encode(customer.getPwd()));
			Customer savedCustomer = customerRepository.save(customer);
			log.info("Customer created successfully: "+ savedCustomer.getId());
			return savedCustomer;
		}
		catch (RuntimeException ex) {
			log.error("Error creating customer: " + ex.getMessage(), ex);
			throw new IllegalArgumentException("An error occurred while creating the customer", ex);
		} 
	}
}
