package com.stoica.onetwo.domain.musica.persistence;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stoica.onetwo.api.resource.musica.dto.MusicaPostDTO;
import com.stoica.onetwo.core.upload.ArmazenarUpload;
import com.stoica.onetwo.core.upload.ArmazenarUpload.NovoArquivo;
import com.stoica.onetwo.domain.musica.MusicaModel;
import com.stoica.onetwo.domain.musica.MusicaService;
import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.UsuarioService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class MusicaServiceImpl implements MusicaService{
	
	MusicaRepository musicaRepository;
	
	ArmazenarUpload armazenarUpload;
	
	UsuarioService usuarioService;
	
	
	@Override
	public List<MusicaModel> findAll() {
		return musicaRepository.findAll();
	}


	@Override
	public MusicaModel findById(Long id) {
		return musicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Falha ao encontrar musica de ID"+id));
	}
	
	@Transactional
	@Override
	public MusicaModel post(MusicaPostDTO musica, InputStream dadosImagem, InputStream dadosAudio) {
		
		usuarioService.findById(musica.getId_usuario());
		
		String capa = armazenarUpload.gerarNomeArquivo(musica.getCapa().getOriginalFilename());
		String audio = armazenarUpload.gerarNomeArquivo(musica.getAudio().getOriginalFilename());
		
		MusicaModel musicaModel = new MusicaModel();
		
		musicaModel.setUsuario(musica.getId_usuario());
		
		musicaModel.setTitulo(musica.getTitulo());
		
		musicaModel.setAudio(audio);
		
		musicaModel.setCapa(capa);
		
		musicaModel.setGenero(musica.getGenero());
		
		var save = musicaRepository.save(musicaModel);
		
		musicaRepository.flush();
		
		armazenarUpload.armazenar(NovoArquivo.builder().nomeArquivo(capa).inputStream(dadosImagem).contentType(musica.getCapa().getContentType()).build());
		armazenarUpload.armazenar(NovoArquivo.builder().nomeArquivo(audio).inputStream(dadosAudio).contentType(musica.getAudio().getContentType()).build());
		
		return save;
	}

	@Override
	@Transactional
	public void remove(Long id) {
		MusicaModel entityExists = findById(id);
		musicaRepository.deleteById(id);
		musicaRepository.flush();
		armazenarUpload.remover(entityExists.getAudio());
		armazenarUpload.remover(entityExists.getCapa());
	}

	

}
