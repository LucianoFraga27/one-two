package com.stoica.onetwo.domain.musica;

import java.io.InputStream;
import java.util.List;

import com.stoica.onetwo.api.resource.musica.dto.MusicaPostDTO;

public interface MusicaService {
	
	List<MusicaModel> findAll();
	List<MusicaModel> listByGenero(GeneroEnum generoEnum);
	MusicaModel findById(Long id);
	MusicaModel post(MusicaPostDTO musica, InputStream dadosImagem, InputStream dadosAudio);
	void remove(Long id);
	public void curtirMusica(Long musicaId, Long usuarioId);
	public void descurtirMusica(Long musicaId, Long usuarioId);
	public long contarCurtidas(Long musicaId);
}
