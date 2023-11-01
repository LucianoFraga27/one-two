package com.stoica.onetwo.domain.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthorizationService {
	
	UserDetails findByLogin(String login);
	AuthModel registerUser(AuthModel user);
	
}
