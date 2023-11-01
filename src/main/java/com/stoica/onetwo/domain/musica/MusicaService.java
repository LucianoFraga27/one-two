package com.stoica.onetwo.domain.musica;

import java.io.InputStream;
import java.util.List;

import com.stoica.onetwo.api.resource.musica.dto.MusicaPostDTO;

public interface MusicaService {
	
	List<MusicaModel> findAll();
	MusicaModel findById(Long id);
	MusicaModel post(MusicaPostDTO musica, InputStream dadosImagem, InputStream dadosAudio);
	void remove(Long id);
}
