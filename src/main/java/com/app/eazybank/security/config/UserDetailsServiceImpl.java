package com.app.eazybank.security.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.eazybank.repository.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return customerRepository.findByEmail(username)
						  .map(customer -> new User(customer.getEmail(), customer.getPwd(), List.of(new SimpleGrantedAuthority(customer.getRole()))))
						  .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
	}
}
