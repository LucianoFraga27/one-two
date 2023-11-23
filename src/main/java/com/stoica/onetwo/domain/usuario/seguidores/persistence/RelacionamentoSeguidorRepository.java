package com.stoica.onetwo.domain.usuario.seguidores.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.seguidores.RelacionamentoSeguidor;

interface RelacionamentoSeguidorRepository extends JpaRepository<RelacionamentoSeguidor, Long> {

    List<RelacionamentoSeguidor> findBySeguidor(UsuarioModel seguidor);

    List<RelacionamentoSeguidor> findBySeguido(UsuarioModel seguido);

    Optional<RelacionamentoSeguidor> findBySeguidorAndSeguido(UsuarioModel seguidor, UsuarioModel seguido);
    
    long countBySeguido(UsuarioModel seguido);

    long countBySeguidor(UsuarioModel seguidor);

    @Query("SELECT rs.seguido FROM RelacionamentoSeguidor rs WHERE rs.seguidor.id = :userId")
    List<UsuarioModel> findSeguidosBySeguidorId(@Param("userId") Long userId);
}