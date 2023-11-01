package com.stoica.onetwo.api.resource.auth.dto;

import java.util.Map;

public record AuthResponseDTO (Map<String, String> token) {

}
