package com.stoica.onetwo.api.resource.musica.v1;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.stoica.onetwo.domain.autenticacao.AuthModel;
import com.stoica.onetwo.domain.musica.GeneroEnum;
import com.stoica.onetwo.domain.musica.MusicaModel;
import com.stoica.onetwo.domain.musica.MusicaService;
import javax.security.auth.Subject;
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
    public MusicaResponseDTO findById(@PathVariable(value = "id") Long id) {
        return musicaMapper.findById(id);
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> postMusica(MusicaPostDTO musicaModel) throws IOException {
        musicaService.post(musicaModel, musicaModel.getCapa().getInputStream(),
                musicaModel.getAudio().getInputStream());
        return ResponseEntity.ok("Musica adicionada com sucesso");
    }

    @PutMapping
    public void edit() {

    }

    @DeleteMapping("/{musicaId}")
    public void remove(@PathVariable Long musicaId) {
        musicaService.remove(musicaId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<MusicaResponseCurtidaDTO> findByUsuario(@PathVariable Long usuarioId) {
        return musicaMapper.findByUsuario(usuarioId);
    }

    @GetMapping("/genero/{genero}")
    public List<MusicaResponseDTO> listByGenero(@PathVariable GeneroEnum genero) {
        return musicaMapper.listByGenero(genero);
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

    @GetMapping("/curtiuounao/{musicaId}")
    public ResponseEntity<Boolean> curtiuOuNao(Authentication authentication, @PathVariable Long musicaId) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            AuthModel authModel = (AuthModel) userDetails;
            Long usuarioId = authModel.getId();
            Boolean curtiu = musicaService.curtiuOuNao(musicaId, usuarioId);
            return ResponseEntity.ok(curtiu);

        }
        return ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/listarMusicasCurtidas")
    public ResponseEntity<List<?>> listarMusicasCurtidas(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails instanceof AuthModel) {
                AuthModel authModel = (AuthModel) userDetails;
                Long usuarioId = authModel.getId();
                List<?> musicasCurtidas = musicaMapper.listarMusicasCurtidas(usuarioId);
                return ResponseEntity.ok(musicasCurtidas);
            }
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/listarMusicasCurtidas/{usuarioid}")
    public ResponseEntity<List<?>> listarMusicasCurtidas(@PathVariable Long usuarioid) {
        try {
            List<?> musicasCurtidas = musicaMapper.listarMusicasCurtidas(usuarioid);
            return ResponseEntity.ok(musicasCurtidas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
