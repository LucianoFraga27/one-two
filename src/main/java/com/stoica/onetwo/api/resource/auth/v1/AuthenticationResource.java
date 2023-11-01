package com.stoica.onetwo.api.resource.auth.v1;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stoica.onetwo.api.config.security.TokenService;
import com.stoica.onetwo.api.resource.auth.dto.LoginDTO;
import com.stoica.onetwo.api.resource.auth.dto.RegisterDTO;
import com.stoica.onetwo.domain.user.User;
import com.stoica.onetwo.domain.user.UserAuthorizationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationResource {
	
	/**
	 * Endpoint de Login e Cadastro de usuários. Pretendo criar o /v2/auth no futuro, porém por hora devera ser assim.
	 *
	 * @author Luciano Fraga Lopes 
	 */
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserAuthorizationService userAuthorizationService;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login (@RequestBody @Valid LoginDTO loginDTO) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
		var auth = authenticationManager.authenticate(usernamePassword);
		return ResponseEntity.ok(tokenService.generateToken((User)auth.getPrincipal()));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register (@RequestBody @Valid RegisterDTO registerDTO) {
		if(this.userAuthorizationService.findByLogin(registerDTO.login()) != null) return ResponseEntity.badRequest().build();
		String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
		User newUser = new User(registerDTO.login(), encryptedPassword, registerDTO.role());
		userAuthorizationService.registerUser(newUser);
		return ResponseEntity.ok().body(newUser);
	}
	
	@Deprecated(since = "Método retornando corpo inválido; '/v1/auth/introspect' Não deve ser utilizado. Ao invés disso utilize /v1/userario/{id}")
	@GetMapping("/introspect")
	public ResponseEntity<?> userIntrospecUser (Principal principal) {
		 try {
			 return ResponseEntity.ok(principal);
		 } catch (Exception e) {
			 return ResponseEntity.notFound().build();
		}
	}
	
	
}
