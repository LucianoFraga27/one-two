package com.stoica.onetwo.domain.autenticacao;

public enum AuthRole {
	
	ADMIN("admin"),
	USER("user");
	
	private String role;
	
	AuthRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
}
