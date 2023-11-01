package com.stoica.onetwo.api.config.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.stoica.onetwo.domain.user.User;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret ;
	
	public Map<String, String> generateToken(User user) {
		try {
		    
			Algorithm algorithm = Algorithm.HMAC256(secret);
		    String token = JWT.create()
		        .withIssuer("one-two")
		        .withSubject(user.getLogin())
		        .withExpiresAt(expiresIn())
		        .sign(algorithm);
		    Map<String, String> response = new HashMap<>();
		    response.put("access_token", token);
		    response.put("id", user.getId().toString());
		    return response;
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Erro ao criar JWT TOKEN");
		}
	}
	
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("one-two")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			 return "invalid token";
		}
	}
	
	private Instant expiresIn() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
