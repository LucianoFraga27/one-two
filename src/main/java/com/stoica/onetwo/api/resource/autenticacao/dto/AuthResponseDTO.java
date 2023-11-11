package com.stoica.onetwo.api.resource.autenticacao.dto;

import java.util.Map;

public record AuthResponseDTO (Map<String, String> token) {

}
