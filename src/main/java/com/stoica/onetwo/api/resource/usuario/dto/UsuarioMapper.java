package com.stoica.onetwo.api.resource.usuario.dto;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stoica.onetwo.core.upload.ArmazenarUpload;
import com.stoica.onetwo.core.upload.ArmazenarUpload.ArquivoRecuperado;
import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.UsuarioService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioMapper {
   
	UsuarioService usuarioService;
	ArmazenarUpload amazenarUpload;
	
	public List<UsuarioModel> findAll() {
	    List<UsuarioModel> users = usuarioService.findAll();
	    for (UsuarioModel user : users) {
	        user.setFotoPerfil(amazenarUpload.recuperar(user.getFotoPerfil()).getUrl());
	    }
	    return users;
	}
	
	public UsuarioModel findById(Long id)  {
		UsuarioModel user = usuarioService.findById(id);
		user.setFotoPerfil(amazenarUpload.recuperar(user.getFotoPerfil()).getUrl());
		return user;
	}
	
}
