package com.stoica.onetwo.domain.musica;

import jakarta.persistence.Entity;


public enum GeneroEnum {
	
	ALTERNATIVE("alternativa"),
    BLUES("blues"),
    COUNTRY("country"),
    ELETRONICA("eletrônica"),
    FUNK("funk"),
    GOSPEL("gospel"),
    HIP_HOP("hip hop"),
    INDIE("indie"),
    JAZZ("jazz"),
    METAL("metal"),
    POP("pop"),
    PUNK("punk"),
    RAP("rap"),
    REGGAE("reggae"),
    RNB("r&b"),
    ROCK("rock"),
    SERTANEJO("sertanejo"),
    SOUL("soul"),
    TRAP("trap"),
    CLASSICA("clássica");
	
	private String genero;
	
	GeneroEnum(String genero) {
		this.genero = genero;
	}
	
	public String getRole() {
		return genero;
	}
}
