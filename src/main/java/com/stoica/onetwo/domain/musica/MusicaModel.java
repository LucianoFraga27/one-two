package com.stoica.onetwo.domain.musica;

import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import com.stoica.onetwo.domain.usuario.UsuarioModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "musica")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MusicaModel {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String capa;
    private String audio;
    private GeneroEnum genero;

    @ManyToOne // Um usuário pode curtir várias músicas
    @JoinColumn(name = "usuario_id") // Coluna que faz a ligação entre MusicaModel e UsuarioModel
    private UsuarioModel usuario;

    @ManyToMany
    @JoinTable(name = "curtidas",
            joinColumns = @JoinColumn(name = "musica_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<UsuarioModel> usuariosCurtiram;
}