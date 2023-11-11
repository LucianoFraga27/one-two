package com.stoica.onetwo.api.resource.usuario.dto;

import com.stoica.onetwo.domain.musica.GeneroEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUsuarioResponseDTO {
    private Long id;
    private String email;
    private String username;
    private String fotoPerfil;
    private GeneroEnum generoFavorito;
    private int seguidoresCount;
    private int seguindoCount;
}
