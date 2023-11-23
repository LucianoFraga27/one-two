package com.stoica.onetwo.domain.usuario.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stoica.onetwo.domain.usuario.UsuarioModel;

interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{

}
