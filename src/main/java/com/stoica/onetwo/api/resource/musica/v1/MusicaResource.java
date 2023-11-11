package com.stoica.onetwo.api.resource.musica.v1;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stoica.onetwo.api.resource.musica.dto.MusicaMapper;
import com.stoica.onetwo.api.resource.musica.dto.MusicaPostDTO;
import com.stoica.onetwo.api.resource.musica.dto.MusicaResponseCurtidaDTO;
import com.stoica.onetwo.api.resource.musica.dto.MusicaResponseDTO;
import com.stoica.onetwo.api.resource.musica.dto.PesquisaMusicaDTO;
import com.stoica.onetwo.domain.musica.GeneroEnum;
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
	public List<MusicaResponseDTO> findAll() {
		return musicaMapper.findAll();
	}
	
	@GetMapping("/{id}")
	public MusicaResponseDTO findById(@PathVariable(value="id") Long id) {
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

	@GetMapping("/usuario/{usuarioId}")
	public List<MusicaResponseCurtidaDTO> findByUsuario(@PathVariable Long usuarioId) {
		return musicaMapper.findByUsuario(usuarioId);
	}

	@GetMapping("/genero/{genero}")
	public List<MusicaModel> listByGenero(@PathVariable GeneroEnum genero) {
		return musicaService.listByGenero(genero);
	}

	@PostMapping("/{musicaId}/curtir/{usuarioId}")
    public ResponseEntity<String> curtirMusica(@PathVariable Long musicaId, @PathVariable Long usuarioId) {
        musicaService.curtirMusica(musicaId, usuarioId);
        return ResponseEntity.ok("Música curtida com sucesso.");
    }

	@DeleteMapping("/{musicaId}/curtir/{usuarioId}")
    public ResponseEntity<String> descurtir(@PathVariable Long musicaId, @PathVariable Long usuarioId) {
        musicaService.descurtirMusica(musicaId, usuarioId);
        return ResponseEntity.ok("Música descurtida com sucesso.");
    }
	
	@PostMapping("/{musicaId}/curtirOuDescurtir/{usuarioId}")
    public ResponseEntity<String> curtirOuDescurtir(@PathVariable Long musicaId, @PathVariable Long usuarioId) {
       return ResponseEntity.ok(musicaService.curtirOuDescurtirMusica(musicaId, usuarioId));
    }

	@GetMapping("/pesquisar/{termo}")
    public ResponseEntity<List<PesquisaMusicaDTO>> pesquisarPorTermo(@PathVariable String termo) {
        List<PesquisaMusicaDTO> musicasEncontradas = musicaMapper.pesquisarPorTermo(termo);
        return ResponseEntity.ok(musicasEncontradas);
    }

	@GetMapping("/top")
    public ResponseEntity<List<PesquisaMusicaDTO>> getTop() {
      return ResponseEntity.ok(musicaMapper.top());
    }
}
