package com.stoica.onetwo.api.resource.musica.dto;

import com.stoica.onetwo.domain.musica.GeneroEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisaMusicaDTO {
    Long idMusica;
    String titulo;
    String capa;
    String audio;
    GeneroEnum genero;
    Long curtidas;
    UsuarioMusicaDTO autor;
}
