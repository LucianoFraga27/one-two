package com.stoica.onetwo.domain.user.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stoica.onetwo.domain.user.User;
import com.stoica.onetwo.domain.user.UserAuthorizationService;

@Service
public class UserAuthorizationImpl implements UserDetailsService, UserAuthorizationService{
	
	@Autowired
	UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByLogin(username);
	}

	@Override
	public UserDetails findByLogin(String login) {
		return repository.findByLogin(login);
	}

	@Override
	public User registerUser(User user) {
		repository.save(user);
		return user;
	}

}
