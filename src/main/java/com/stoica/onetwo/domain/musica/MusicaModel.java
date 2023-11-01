package com.stoica.onetwo.domain.musica;

import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import com.stoica.onetwo.domain.usuario.UsuarioModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name="musica")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MusicaModel {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	
	private String capa;
	
	private String audio;
	
	private GeneroEnum genero;
	
	private Long usuario;
	
}
