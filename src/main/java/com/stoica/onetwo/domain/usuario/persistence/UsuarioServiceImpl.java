package com.stoica.onetwo.domain.usuario.persistence;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stoica.onetwo.api.resource.usuario.dto.AddUsernameAndFotoDTO;
import com.stoica.onetwo.core.upload.ArmazenarUpload;
import com.stoica.onetwo.core.upload.ArmazenarUpload.NovoArquivo;
import com.stoica.onetwo.domain.musica.GeneroEnum;
import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Service
@AllArgsConstructor
class UsuarioServiceImpl implements UsuarioService{
	
	UsuarioRepository usuarioRepository;
	
	ArmazenarUpload armazenarUpload;
	
	@Override
	public List<UsuarioModel> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public UsuarioModel findById(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Erro ao encontrar usuario com o Id "+id));
	}
	
	@Transactional
	@Override
	public UsuarioModel save(UsuarioModel usuario) {
		return usuarioRepository.save(usuario);
	}

	@Transactional
	@Override
	public void addUsernameAndFoto(Long id, AddUsernameAndFotoDTO post, InputStream dadosImagem) {
		UsuarioModel user = findById(id);
		user.setUsername(post.username());
		String capa = armazenarUpload.gerarNomeArquivo(post.fotoPerfil().getOriginalFilename());
		user.setFotoPerfil(capa);
		user.setGeneroFavorito(post.generoFavorito());
		usuarioRepository.save(user);
		armazenarUpload.armazenar(NovoArquivo.builder().nomeArquivo(capa).inputStream(dadosImagem).contentType(post.fotoPerfil().getContentType()).build());
	}
	


}

