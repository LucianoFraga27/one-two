package com.stoica.onetwo.domain.usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
	
	List<UsuarioModel> findAll();
	UsuarioModel findById(Long id);
	
	
	UsuarioModel save (UsuarioModel usuario);
	
}
