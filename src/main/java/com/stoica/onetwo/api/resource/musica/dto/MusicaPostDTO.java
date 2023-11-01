package com.stoica.onetwo.api.resource.musica.dto;

import org.springframework.web.multipart.MultipartFile;

import com.stoica.onetwo.domain.musica.GeneroEnum;
import com.stoica.onetwo.domain.usuario.UsuarioModel;

import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class MusicaPostDTO {
	
	private Long id_usuario;
	
	private String titulo;
	
	private MultipartFile capa;
	
	private MultipartFile audio;
	
	private GeneroEnum genero;
}
