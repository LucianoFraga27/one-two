package com.stoica.onetwo.api.resource.auth.dto;

import com.stoica.onetwo.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {

}
