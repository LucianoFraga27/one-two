package com.stoica.onetwo.api.config.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.stoica.onetwo.api.resource.usuario.dto.GetUsuarioResponseDTO;
import com.stoica.onetwo.api.resource.usuario.dto.UsuarioMapper;
import com.stoica.onetwo.domain.autenticacao.AuthModel;
import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.UsuarioService;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret ;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioMapper usuarioMapper;
	
	public Map<String, String> generateToken(AuthModel user) {
		try {
		    
			Algorithm algorithm = Algorithm.HMAC256(secret);
		    
			GetUsuarioResponseDTO usuarioModel = usuarioMapper.findById(user.getId());
			
			Map responseToken = new HashMap<>();
			
			responseToken.put("id", usuarioModel.getId());
			responseToken.put("email", usuarioModel.getEmail());
			responseToken.put("username", usuarioModel.getUsername());
			responseToken.put("foto", usuarioModel.getFotoPerfil());
			
			String token = JWT.create()
		        .withIssuer("one-two")
		        .withSubject(user.getLogin())
		        .withExpiresAt(expiresIn())
		        .withClaim("user", responseToken)
		        .sign(algorithm);
		    
		    Map response = new HashMap<>();
		    response.put("access_token", token);
		    response.put("expires_in", 18000);
		    response.put("token_type", "Bearer");
		    // response.put("refresh_token", null);
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
		return LocalDateTime.now().plusHours(5).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
