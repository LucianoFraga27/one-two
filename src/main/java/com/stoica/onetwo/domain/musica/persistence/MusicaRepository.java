package com.stoica.onetwo.domain.musica.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stoica.onetwo.domain.musica.GeneroEnum;
import com.stoica.onetwo.domain.musica.MusicaModel;
import com.stoica.onetwo.domain.usuario.UsuarioModel;


interface MusicaRepository extends JpaRepository<MusicaModel, Long>{

    @Query("SELECT COUNT(u) FROM musica m JOIN m.usuariosCurtiram u WHERE m.id = :musicaId")
    long countCurtidas(@Param("musicaId") Long musicaId);
    
    List<MusicaModel> findByGenero(GeneroEnum genero);
    
    List<MusicaModel> findAllByOrderByGenero();

    List<MusicaModel> findByUsuario(UsuarioModel usuario);

    @Query("SELECT m FROM musica m WHERE " +
           "m.titulo LIKE CONCAT('%', :termo, '%') OR " +
           "m.usuario.username LIKE CONCAT('%', :termo, '%')")
    List<MusicaModel> pesquisarPorTermo(@Param("termo") String termo);
    List<MusicaModel>  findByUsuariosCurtiramContaining (UsuarioModel usuario);
}
