package com.stoica.onetwo.api.resource.musica.dto;

import java.util.ArrayList;
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
	
	public List<MusicaResponseDTO> findAll() {
	    List<MusicaModel> musicas = musicaService.findAll();
		List<MusicaResponseDTO> response = new ArrayList();
		
	    for (MusicaModel musica : musicas) {
			MusicaResponseDTO dto = new MusicaResponseDTO(); 
	    	dto.setCapa(amazenarUpload.recuperar(musica.getCapa()).getUrl());
			dto.setAudio(amazenarUpload.recuperar(musica.getAudio()).getUrl());
			dto.setCurtidas(musicaService.contarCurtidas(musica.getId()));
			UsuarioModel usuario = usuarioService.findById(musica.getUsuario().getId());
			UsuarioMusicaDTO usuarioMusica = new UsuarioMusicaDTO();
			usuarioMusica.setId(usuario.getId());
			usuarioMusica.setUsername(usuario.getUsername());
			dto.setAutor(usuarioMusica);
			dto.setId(musica.getId());
			dto.setTitulo(musica.getTitulo());
			dto.setGenero(musica.getGenero());
			response.add(dto);
	    }
	    return response;
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

	public List<MusicaResponseCurtidaDTO> findByUsuario(Long usuarioId) {
	    List<MusicaModel> musicas = musicaService.listarMusicasDoUsuario(usuarioId);
		List<MusicaResponseCurtidaDTO> response = new ArrayList();
		List<UsuarioCurtiuDTO> usuariosCurtiram = new ArrayList();
	    for (MusicaModel musica : musicas) {
			MusicaResponseCurtidaDTO dto = new MusicaResponseCurtidaDTO(); 
	    	dto.setId(musica.getId());
			dto.setTitulo(musica.getTitulo());
			dto.setCapa(amazenarUpload.recuperar(musica.getCapa()).getUrl());
			dto.setAudio(amazenarUpload.recuperar(musica.getAudio()).getUrl());
			dto.setGenero(musica.getGenero());
			dto.setCurtidas(musicaService.contarCurtidas(musica.getId()));
			
			for(UsuarioModel usuario: musica.getUsuariosCurtiram()){
				UsuarioCurtiuDTO usuarioCurtiu = new UsuarioCurtiuDTO();
				usuarioCurtiu.setId(usuario.getId());
				usuarioCurtiu.setUsername(usuario.getUsername());
				usuarioCurtiu.setFotoPerfil(usuario.getFotoPerfil());
				usuariosCurtiram.add(usuarioCurtiu);
			}
			dto.setUsuariosCurtiram(usuariosCurtiram);			
			response.add(dto);

	    }
	    return response;
	}
	
}
