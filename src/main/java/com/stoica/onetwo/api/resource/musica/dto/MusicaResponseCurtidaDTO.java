package com.stoica.onetwo.api.resource.musica.dto;

import java.util.List;

import com.stoica.onetwo.domain.musica.GeneroEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicaResponseCurtidaDTO {
    Long id;
    String titulo;
    String capa;
    String audio;
    GeneroEnum genero;
    Long curtidas;
    List<UsuarioCurtiuDTO> usuariosCurtiram;
}
