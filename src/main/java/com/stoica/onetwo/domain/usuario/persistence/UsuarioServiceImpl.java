package com.stoica.onetwo.domain.usuario.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.UsuarioService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{
	
	UsuarioRepository usuarioRepository;

	@Override
	public List<UsuarioModel> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public UsuarioModel findById(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Erro ao encontrar usuario com o Id "+id));
	}
	
	@Override
	public UsuarioModel save(UsuarioModel usuario) {
		return usuarioRepository.save(usuario);
	}

	
	
	
}
