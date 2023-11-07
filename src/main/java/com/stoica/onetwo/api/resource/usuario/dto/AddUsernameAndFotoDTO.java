package com.stoica.onetwo.api.resource.usuario.dto;

import org.springframework.web.multipart.MultipartFile;

import com.stoica.onetwo.domain.musica.GeneroEnum;

public record AddUsernameAndFotoDTO (String username, MultipartFile fotoPerfil, GeneroEnum generoFavorito){

}
