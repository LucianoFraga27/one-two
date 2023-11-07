package com.stoica.onetwo.api.resource.musica.dto;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stoica.onetwo.core.upload.ArmazenarUpload;
import com.stoica.onetwo.domain.musica.MusicaModel;
import com.stoica.onetwo.domain.musica.MusicaService;
import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.UsuarioService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MusicaMapper {
	
	ArmazenarUpload amazenarUpload;
	MusicaService musicaService;
	UsuarioService usuarioService;
	
	public List<MusicaModel> findAll() {
	    List<MusicaModel> musicas = musicaService.findAll();
	    for (MusicaModel musica : musicas) {
	    	musica.setCapa(amazenarUpload.recuperar(musica.getCapa()).getUrl());
			musica.setAudio(amazenarUpload.recuperar(musica.getAudio()).getUrl());
	    }
	    return musicas;
	}
	
	public MusicaResponseDTO findById(Long id)  {
		MusicaModel musica = musicaService.findById(id);
		MusicaResponseDTO musicaResponse = new MusicaResponseDTO();
		
		UsuarioModel usuario = usuarioService.findById(musica.getUsuario().getId());
		
		musicaResponse.setId(musica.getId());
		musicaResponse.setTitulo(musica.getTitulo());
		musicaResponse.setCurtidas(musicaService.contarCurtidas(id));
		musicaResponse.setCapa(amazenarUpload.recuperar(musica.getCapa()).getUrl());
		musicaResponse.setAudio(amazenarUpload.recuperar(musica.getAudio()).getUrl());
		musicaResponse.setGenero(musica.getGenero());

		UsuarioMusicaDTO usuarioMusica = new UsuarioMusicaDTO();
		usuarioMusica.setId(usuario.getId());
		usuarioMusica.setUsername(usuario.getUsername());
		musicaResponse.setAutor(usuarioMusica);
		
		return musicaResponse;
	}
	
}
