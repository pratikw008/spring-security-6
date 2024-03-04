package com.app.eazybank.security.config;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.eazybank.entity.Customer;
import com.app.eazybank.repository.CustomerRepository;

@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

	private CustomerRepository customerRepository;
	
	private PasswordEncoder passwordEncoder;
	
	public EazyBankUsernamePwdAuthenticationProvider(CustomerRepository customerRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		Optional<Customer> customer = customerRepository.findByEmail(username);
		if(customer.isPresent()) {
			if(passwordEncoder.matches(pwd, customer.get().getPwd())) {
				List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(customer.get().getRole()));
				return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
			} else {
				throw new BadCredentialsException("Invalid Password");
			}
			
		}
		else {
			throw new BadCredentialsException("No Customer registered with this details");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
