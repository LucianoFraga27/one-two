package com.stoica.onetwo.domain.usuario.persistence.errors;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long id) {
        super(String.format("Autor com o id ('%d') não foi encontrado.", id)); 
    }
    
}
