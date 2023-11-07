package com.stoica.onetwo.domain.usuario;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import com.stoica.onetwo.api.resource.usuario.dto.AddUsernameAndFotoDTO;
import com.stoica.onetwo.domain.musica.GeneroEnum;

public interface UsuarioService {
	
	List<UsuarioModel> findAll();
	
	UsuarioModel findById(Long id);
	
	UsuarioModel save (UsuarioModel usuario);
	
	void addUsernameAndFoto(Long id, AddUsernameAndFotoDTO usuario, InputStream dadosImagem);
}
