package com.stoica.onetwo.domain.usuario.seguidores;

import com.stoica.onetwo.domain.usuario.UsuarioModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "relacionamento_seguidor")
public class RelacionamentoSeguidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seguidor_id", nullable = false)
    private UsuarioModel seguidor;

    @ManyToOne
    @JoinColumn(name = "seguido_id", nullable = false)
    private UsuarioModel seguido;

}