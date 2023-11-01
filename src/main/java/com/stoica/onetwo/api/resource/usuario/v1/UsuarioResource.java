package com.stoica.onetwo.api.resource.usuario.v1;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stoica.onetwo.api.resource.usuario.dto.AddUsernameAndFotoDTO;
import com.stoica.onetwo.api.resource.usuario.dto.UsuarioMapper;
import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.UsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/usuario")
@AllArgsConstructor
public class UsuarioResource {

	UsuarioService usuarioService;
	UsuarioMapper usuarioMapper;
	
	@GetMapping
	public List<UsuarioModel> findAll() {
		return usuarioMapper.findAll();
	}
	
	@GetMapping("/{id}")
	public UsuarioModel findById(@PathVariable(value="id") Long id) {
		return usuarioMapper.findById(id);
	}
	
	@PostMapping(value = "/{id}",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void addUsernameAndFoto (@PathVariable(value="id") Long id, AddUsernameAndFotoDTO post) throws IOException {
		usuarioService.addUsernameAndFoto(id,post, post.fotoPerfil().getInputStream());
	}
	
}
