package com.stoica.onetwo.api.resource.musica.v1;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stoica.onetwo.api.resource.musica.dto.MusicaMapper;
import com.stoica.onetwo.api.resource.musica.dto.MusicaPostDTO;
import com.stoica.onetwo.domain.musica.MusicaModel;
import com.stoica.onetwo.domain.musica.MusicaService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/musica")
@AllArgsConstructor
public class MusicaResource {
	
	MusicaService musicaService;
	MusicaMapper musicaMapper;
	@GetMapping
	public List<MusicaModel> findAll() {
		return musicaMapper.findAll();
	}
	
	@GetMapping("/{id}")
	public MusicaModel findById(@PathVariable(value="id") Long id) {
		return musicaMapper.findById(id);
	}
	
	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public MusicaModel postMusica(MusicaPostDTO musicaModel) throws IOException {
		return musicaService.post(musicaModel, musicaModel.getCapa().getInputStream() ,musicaModel.getAudio().getInputStream());
	}
	
	@PutMapping
	public void edit() {
	
	}
	
	@DeleteMapping
	public void remove() {
	
	}
}
