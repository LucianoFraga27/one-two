package com.stoica.onetwo.domain.usuario;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stoica.onetwo.domain.autenticacao.AuthModel;
import com.stoica.onetwo.domain.musica.GeneroEnum;
import com.stoica.onetwo.domain.musica.MusicaModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "usuario")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioModel {

    @EqualsAndHashCode.Include
    @Id
    private Long id;

    @Column(nullable = false, length = 200)
    private String email;
    private String username;
    private String fotoPerfil;
    private GeneroEnum generoFavorito;

    @JsonIgnore
    @ManyToMany(mappedBy = "usuariosCurtiram")
    private List<MusicaModel> musicasCurtidas;
}
