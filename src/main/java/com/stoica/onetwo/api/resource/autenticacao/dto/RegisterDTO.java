package com.stoica.onetwo.api.resource.autenticacao.dto;

import com.stoica.onetwo.domain.autenticacao.AuthRole;

public record RegisterDTO(String login, String password, AuthRole role) {

}
