package com.stoica.onetwo.domain.auth.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stoica.onetwo.domain.auth.AuthModel;
import com.stoica.onetwo.domain.auth.AuthorizationService;

@Service
public class AuthAuthorizationImpl implements UserDetailsService, AuthorizationService{
	
	@Autowired
	AuthRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByLogin(username);
	}

	@Override
	public UserDetails findByLogin(String login) {
		return repository.findByLogin(login);
	}

	@Override
	public AuthModel registerUser(AuthModel user) {
		repository.save(user);
		return user;
	}

}
