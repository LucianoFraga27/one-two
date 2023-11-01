package com.stoica.onetwo.domain.usuario.persistence;

import org.springframework.stereotype.Service;

import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.UsuarioService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{
	
	UsuarioRepository usuarioRepository;

	@Override
	public UsuarioModel save(UsuarioModel usuario) {
		return usuarioRepository.save(usuario);
	}
	
	
}
