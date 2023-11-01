package com.stoica.onetwo.domain.musica.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stoica.onetwo.domain.musica.MusicaModel;

interface MusicaRepository extends JpaRepository<MusicaModel, Long>{

}
