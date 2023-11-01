package com.stoica.onetwo.domain.usuario;

import com.stoica.onetwo.domain.auth.AuthModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name="usuario")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioModel {
	
	@EqualsAndHashCode.Include
	@Id
	private Long id;
	
	@Column(nullable = false, length = 200)
	private String email;
	
	private String username;

}
