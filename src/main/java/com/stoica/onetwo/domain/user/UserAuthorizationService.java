package com.stoica.onetwo.domain.user;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserAuthorizationService {
	
	UserDetails findByLogin(String login);
	User registerUser(User user);
	
}
