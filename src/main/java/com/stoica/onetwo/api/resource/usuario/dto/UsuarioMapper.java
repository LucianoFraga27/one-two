package com.stoica.onetwo.api.resource.usuario.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.stoica.onetwo.core.upload.ArmazenarUpload;
import com.stoica.onetwo.core.upload.ArmazenarUpload.ArquivoRecuperado;
import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.UsuarioService;
import com.stoica.onetwo.domain.usuario.seguidores.RelacionamentoSeguidor;
import com.stoica.onetwo.domain.usuario.seguidores.RelacionamentoSeguidorService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioMapper {
   
	UsuarioService usuarioService;
	ArmazenarUpload amazenarUpload;
    RelacionamentoSeguidorService relacionamentoSeguidorService;
	
	public List<GetUsuarioResponseDTO> findAll() {
        List<UsuarioModel> usuarios = usuarioService.findAll();
        List<GetUsuarioResponseDTO> usuariosDto = new ArrayList<>();

        for (UsuarioModel usuario : usuarios) {
            Long seguidoresCount = relacionamentoSeguidorService.contarSeguidores(usuario);
            Long seguindoCount = relacionamentoSeguidorService.contarSeguindo(usuario);

            usuariosDto.add(new GetUsuarioResponseDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getFotoPerfil(),
                usuario.getGeneroFavorito(),
                seguidoresCount,
                seguindoCount
            ));
        }

        return usuariosDto;
    }
	
	public GetUsuarioResponseDTO findById(Long id)  {
		UsuarioModel usuarioModel = usuarioService.findById(id);
		usuarioModel.setFotoPerfil(amazenarUpload.recuperar(usuarioModel.getFotoPerfil()).getUrl());
		Long seguidoresCount = relacionamentoSeguidorService.contarSeguidores(usuarioModel);
            Long seguindoCount = relacionamentoSeguidorService.contarSeguindo(usuarioModel);
		return new GetUsuarioResponseDTO(
            usuarioModel.getId(),
            usuarioModel.getEmail(),
            usuarioModel.getUsername(),
            usuarioModel.getFotoPerfil(),
            usuarioModel.getGeneroFavorito(),
            seguidoresCount,
            seguindoCount
        );
	} 

   
    public List<GetUsuarioResponseDTO> listarSeguidores( Long usuarioId) {
        UsuarioModel usuario = usuarioService.findById(usuarioId);
        List<UsuarioModel> seguidores = relacionamentoSeguidorService.listarSeguidores(usuario);

        List<GetUsuarioResponseDTO> usuariosDto = new ArrayList<>();

        for (UsuarioModel u : seguidores) {

            usuariosDto.add(new GetUsuarioResponseDTO(
                u.getId(),
                u.getEmail(),
                u.getUsername(),
                amazenarUpload.recuperar(u.getFotoPerfil()).getUrl(),
                u.getGeneroFavorito(),
                0L,
                0L
            ));
        }

        return usuariosDto;
    }

    
    public List<GetUsuarioResponseDTO> listarSeguindo( Long usuarioId) {
        UsuarioModel usuario = usuarioService.findById(usuarioId);
        List<UsuarioModel> seguindo = relacionamentoSeguidorService.listarSeguindo(usuario);
        List<GetUsuarioResponseDTO> usuariosDto = new ArrayList<>();

        for (UsuarioModel u : seguindo) {

            usuariosDto.add(new GetUsuarioResponseDTO(
                u.getId(),
                u.getEmail(),
                u.getUsername(),
                amazenarUpload.recuperar(u.getFotoPerfil()).getUrl(),
                u.getGeneroFavorito(),
                0L,
                0L
            ));
        }

        return usuariosDto;
    }

	
}
