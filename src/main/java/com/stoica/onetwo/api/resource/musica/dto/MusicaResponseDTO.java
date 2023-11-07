package com.stoica.onetwo.api.resource.musica.dto;

import com.stoica.onetwo.domain.musica.GeneroEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicaResponseDTO {
    Long id;
    String titulo;
    String capa;
    String audio;
    GeneroEnum genero;
    UsuarioMusicaDTO autor;
    Long curtidas;
}
