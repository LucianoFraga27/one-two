package com.stoica.onetwo.domain.usuario.seguidores;



import java.util.List;
import java.util.Optional;

import com.stoica.onetwo.domain.usuario.UsuarioModel;

public interface RelacionamentoSeguidorService {
    void seguirUsuario(UsuarioModel seguidor, UsuarioModel seguido);

    void deixarDeSeguirUsuario(UsuarioModel seguidor, UsuarioModel seguido);

    List<UsuarioModel> listarSeguidores(UsuarioModel usuario);

    List<UsuarioModel> listarSeguindo(UsuarioModel usuario);

    boolean verificarSeUsuarioSegue(UsuarioModel seguidor, UsuarioModel seguido);
    
    Long contarSeguidores(UsuarioModel usuario);

    Long  contarSeguindo(UsuarioModel usuario);
}
