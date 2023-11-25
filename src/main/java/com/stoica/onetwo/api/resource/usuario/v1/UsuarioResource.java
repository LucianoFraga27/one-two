package com.stoica.onetwo.api.resource.usuario.v1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stoica.onetwo.api.resource.usuario.dto.AddUsernameAndFotoDTO;
import com.stoica.onetwo.api.resource.usuario.dto.GetUsuarioResponseDTO;
import com.stoica.onetwo.api.resource.usuario.dto.UsuarioMapper;
import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.UsuarioService;
import com.stoica.onetwo.domain.usuario.seguidores.RelacionamentoSeguidorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/usuario")
@AllArgsConstructor
public class UsuarioResource {

	private UsuarioService usuarioService;
	private UsuarioMapper usuarioMapper;
	private RelacionamentoSeguidorService relacionamentoSeguidorService;
    private RelacionamentoSeguidorService seguidorService;

	@GetMapping
	public List<GetUsuarioResponseDTO> findAll() {
		return usuarioMapper.findAll();
	}

	@GetMapping("/{id}")
	public GetUsuarioResponseDTO findById(@PathVariable(value = "id") Long id) {
		return usuarioMapper.findById(id);
	}

	@PostMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void addUsernameAndFoto(@PathVariable(value = "id") Long id, AddUsernameAndFotoDTO post) throws IOException {
		usuarioService.addUsernameAndFoto(id, post, post.fotoPerfil().getInputStream());
	}

	@PostMapping("/{seguidorId}/seguir/{seguidoId}")
    public ResponseEntity<String> seguirUsuario(@PathVariable Long seguidorId, @PathVariable Long seguidoId) {
        // Busque os usuários do banco de dados (você pode fazer isso com seu serviço de usuário)
        UsuarioModel seguidor = usuarioService.findById(seguidorId);
        UsuarioModel seguido = usuarioService.findById(seguidoId);

        relacionamentoSeguidorService.seguirUsuario(seguidor, seguido);

        return ResponseEntity.ok("Usuário seguido com sucesso.");
    }

    @PostMapping("/{seguidorId}/deixar-de-seguir/{seguidoId}")
    public ResponseEntity<String> deixarDeSeguirUsuario(@PathVariable Long seguidorId, @PathVariable Long seguidoId) {
        // Busque os usuários do banco de dados (você pode fazer isso com seu serviço de usuário)
        UsuarioModel seguidor = usuarioService.findById(seguidorId);
        UsuarioModel seguido = usuarioService.findById(seguidoId);

        relacionamentoSeguidorService.deixarDeSeguirUsuario(seguidor, seguido);

        return ResponseEntity.ok("Usuário deixou de ser seguido com sucesso.");
    }

    @GetMapping("/{usuarioId}/seguidores")
    public ResponseEntity<List<?>> listarSeguidores(@PathVariable Long usuarioId) {
        
        return ResponseEntity.ok(usuarioMapper.listarSeguidores(usuarioId));
    }

    @GetMapping("/{usuarioId}/seguindo")
    public ResponseEntity<List<?>> listarSeguindo(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioMapper.listarSeguindo(usuarioId));
    }

	@PostMapping("/{seguidorId}/seguir2/{seguidoId}")
	public ResponseEntity<String> seguirOuDeixarDeSeguirUsuario(@PathVariable Long seguidorId, @PathVariable Long seguidoId) {
    // Busque os usuários do banco de dados (você pode fazer isso com seu serviço de usuário)
    UsuarioModel seguidor = usuarioService.findById(seguidorId);
    UsuarioModel seguido = usuarioService.findById(seguidoId);

    // Verifica se o usuário já é seguido
    boolean jaSegue = relacionamentoSeguidorService.verificarSeUsuarioSegue(seguidor, seguido);

    if (jaSegue) {
        // Se já está seguindo, deixe de seguir
        relacionamentoSeguidorService.deixarDeSeguirUsuario(seguidor, seguido);
        return ResponseEntity.ok("Usuário deixou de ser seguido com sucesso.");
    } else {
        // Se não está seguindo, siga
        relacionamentoSeguidorService.seguirUsuario(seguidor, seguido);
        return ResponseEntity.ok("Usuário seguido com sucesso.");
    }
}

@GetMapping("/{seguidorID}/verficaSeSegue/{seguidoID}")
public ResponseEntity<?> verificaSeJaSegue (@PathVariable Long seguidorID,@PathVariable  Long seguidoID) {
    UsuarioModel SEGUIDOR = usuarioService.findById(seguidorID);
    UsuarioModel SEGUIDO = usuarioService.findById(seguidoID);
    return ResponseEntity.ok(seguidorService.verificarSeUsuarioSegue(SEGUIDOR, SEGUIDO));
}


}
