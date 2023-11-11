package com.stoica.onetwo.domain.usuario.seguidores.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.seguidores.RelacionamentoSeguidor;

interface RelacionamentoSeguidorRepository extends JpaRepository<RelacionamentoSeguidor, Long> {

    List<RelacionamentoSeguidor> findBySeguidor(UsuarioModel seguidor);

    List<RelacionamentoSeguidor> findBySeguido(UsuarioModel seguido);

    Optional<RelacionamentoSeguidor> findBySeguidorAndSeguido(UsuarioModel seguidor, UsuarioModel seguido);
    
    long countBySeguido(UsuarioModel seguido);

    long countBySeguidor(UsuarioModel seguidor);
}