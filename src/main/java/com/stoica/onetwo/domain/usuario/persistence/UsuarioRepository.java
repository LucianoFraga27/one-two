package com.stoica.onetwo.domain.usuario.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stoica.onetwo.domain.usuario.UsuarioModel;

interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{

}
