package com.stoica.onetwo.api.resource.usuario.dto;

import org.springframework.web.multipart.MultipartFile;

public record AddUsernameAndFotoDTO (String username, MultipartFile fotoPerfil){

}
