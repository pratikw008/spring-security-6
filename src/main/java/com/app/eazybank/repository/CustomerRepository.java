package com.app.eazybank.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.eazybank.entity.Customer;


public interface CustomerRepository extends CrudRepository<Customer, Long>{
	
	Optional<Customer> findByEmail(String email);
	boolean existsByEmail(String email);
}
