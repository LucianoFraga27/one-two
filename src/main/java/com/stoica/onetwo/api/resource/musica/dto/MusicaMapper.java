package com.stoica.onetwo.api.resource.musica.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.stoica.onetwo.api.resource.autenticacao.dto.MusicaCurtidaDTO;
import com.stoica.onetwo.core.upload.ArmazenarUpload;
import com.stoica.onetwo.domain.musica.GeneroEnum;
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
			dto.setAutor(musica.getUsuario().getUsername());
			dto.setTitulo(musica.getTitulo());
			dto.setCapa(amazenarUpload.recuperar(musica.getCapa()).getUrl());
			dto.setAudio(amazenarUpload.recuperar(musica.getAudio()).getUrl());
			dto.setGenero(musica.getGenero());
			dto.setCurtidas(musicaService.contarCurtidas(musica.getId()));
			
			for(UsuarioModel usuario: musica.getUsuariosCurtiram()){
				UsuarioCurtiuDTO usuarioCurtiu = new UsuarioCurtiuDTO();
				usuarioCurtiu.setId(usuario.getId());
				usuarioCurtiu.setUsername(usuario.getUsername());
				if(usuario.getFotoPerfil() == null) {
					usuarioCurtiu.setFotoPerfil(null);
				} else {
					usuarioCurtiu.setFotoPerfil(amazenarUpload.recuperar(usuario.getFotoPerfil()).getUrl());
				}

				usuariosCurtiram.add(usuarioCurtiu);
			}
			dto.setUsuariosCurtiram(usuariosCurtiram);			
			response.add(dto);

	    }
	    return response;
	}

	public List<PesquisaMusicaDTO> pesquisarPorTermo(String termo) {
		List<PesquisaMusicaDTO> response = new ArrayList<>();
		UsuarioMusicaDTO autor = new UsuarioMusicaDTO();
		List<MusicaModel> musicasEncontradas = musicaService.pesquisarPorTermo(termo);

		for (MusicaModel m : musicasEncontradas) {
			PesquisaMusicaDTO dto = new PesquisaMusicaDTO();
			dto.setIdMusica(m.getId());
			dto.setTitulo(m.getTitulo());
			dto.setCapa(amazenarUpload.recuperar(m.getCapa()).getUrl());
			dto.setAudio(amazenarUpload.recuperar(m.getAudio()).getUrl());
			dto.setGenero(m.getGenero());
			dto.setCurtidas(musicaService.contarCurtidas(m.getId()));
			autor.setId(m.getUsuario().getId());
			autor.setUsername(m.getUsuario().getUsername());
			dto.setAutor(autor);
			response.add(dto);
		}


		return response;
	}

	public List<PesquisaMusicaDTO> top() {
		List<PesquisaMusicaDTO> response = new ArrayList<>();
		List<MusicaModel> musicas = musicaService.findAll();
	
		for (MusicaModel m : musicas) {
			PesquisaMusicaDTO dto = new PesquisaMusicaDTO();
			dto.setIdMusica(m.getId());
			dto.setTitulo(m.getTitulo());
			dto.setCapa(amazenarUpload.recuperar(m.getCapa()).getUrl());
			dto.setAudio(amazenarUpload.recuperar(m.getAudio()).getUrl());
			dto.setGenero(m.getGenero());
			dto.setCurtidas(musicaService.contarCurtidas(m.getId()));
	
			UsuarioMusicaDTO autor = new UsuarioMusicaDTO(); // Criando um novo autor para cada música
			autor.setId(m.getUsuario().getId());
			autor.setUsername(m.getUsuario().getUsername());
			dto.setAutor(autor);
	
			response.add(dto);
		}
		
		Collections.sort(response, Comparator.comparingLong(PesquisaMusicaDTO::getCurtidas).reversed());
	
		return response;
	}
	

	public List<MusicaResponseDTO> listByGenero( GeneroEnum genero) {
		List<MusicaModel> musicas = musicaService.listByGenero(genero);
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

	public List<MusicaResponseDTO> listarMusicasCurtidas(Long usuarioId) {
		List<MusicaModel> musicas = musicaService.listarMusicasCurtidas(usuarioId);
		return musicas.stream().map(this::mapToMusicaResponseDTO).collect(Collectors.toList());
	}
	
	private MusicaResponseDTO mapToMusicaResponseDTO(MusicaModel musica) {
		MusicaResponseDTO dto = new MusicaResponseDTO();
		dto.setId(musica.getId());
		dto.setTitulo(musica.getTitulo());
		dto.setAudio(amazenarUpload.recuperar(musica.getAudio()).getUrl());
		dto.setCapa(amazenarUpload.recuperar(musica.getCapa()).getUrl());
		// dto.setCurtidas(musicaService.contarCurtidas(musica.getId()));
		dto.setGenero(musica.getGenero());
		UsuarioModel usuario = usuarioService.findById(musica.getUsuario().getId());
		UsuarioMusicaDTO usuarioMusica = new UsuarioMusicaDTO();
		usuarioMusica.setId(usuario.getId());
		usuarioMusica.setUsername(usuario.getUsername());
		dto.setAutor(usuarioMusica);
		return dto;
	}
}
