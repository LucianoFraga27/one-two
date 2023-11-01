package com.stoica.onetwo.api.resource.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIntrospectResponse {

	String id;
	String login;
	String role;
	boolean enable;
	
}
