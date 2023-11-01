package com.stoica.onetwo.api.resource.musica.dto;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stoica.onetwo.core.upload.ArmazenarUpload;
import com.stoica.onetwo.domain.musica.MusicaModel;
import com.stoica.onetwo.domain.musica.MusicaService;
import com.stoica.onetwo.domain.usuario.UsuarioModel;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MusicaMapper {
	
	ArmazenarUpload amazenarUpload;
	MusicaService musicaService;
	
	public List<MusicaModel> findAll() {
	    List<MusicaModel> musicas = musicaService.findAll();
	    for (MusicaModel musica : musicas) {
	    	musica.setCapa(amazenarUpload.recuperar(musica.getCapa()).getUrl());
			musica.setAudio(amazenarUpload.recuperar(musica.getAudio()).getUrl());
	    }
	    return musicas;
	}
	
	public MusicaModel findById(Long id)  {
		MusicaModel musica = musicaService.findById(id);
		musica.setCapa(amazenarUpload.recuperar(musica.getCapa()).getUrl());
		musica.setAudio(amazenarUpload.recuperar(musica.getAudio()).getUrl());
		return musica;
	}
	
}
