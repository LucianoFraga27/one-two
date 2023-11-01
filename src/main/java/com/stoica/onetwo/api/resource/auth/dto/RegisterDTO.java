package com.stoica.onetwo.api.resource.auth.dto;

import com.stoica.onetwo.domain.auth.AuthRole;

public record RegisterDTO(String login, String password, AuthRole role) {

}
