package com.stoica.onetwo.domain.usuario.seguidores.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.seguidores.RelacionamentoSeguidor;
import com.stoica.onetwo.domain.usuario.seguidores.RelacionamentoSeguidorService;

@Service
class RelacionamentoSeguidorServiceImpl implements RelacionamentoSeguidorService {

    @Autowired
    private RelacionamentoSeguidorRepository relacionamentoSeguidorRepository;

    @Override
    public void seguirUsuario(UsuarioModel seguidor, UsuarioModel seguido) {
        if (!relacionamentoSeguidorRepository.findBySeguidorAndSeguido(seguidor, seguido).isPresent()) {
            RelacionamentoSeguidor  relacionamento = new RelacionamentoSeguidor();
            relacionamento.setSeguidor(seguidor);
            relacionamento.setSeguido(seguido);
            relacionamentoSeguidorRepository.save(relacionamento);
        }
    }

    @Override
    public void deixarDeSeguirUsuario(UsuarioModel seguidor, UsuarioModel seguido) {
        relacionamentoSeguidorRepository.findBySeguidorAndSeguido(seguidor, seguido)
            .ifPresent(relacionamentoSeguidorRepository::delete);
    }

    @Override
    public List<UsuarioModel> listarSeguidores(UsuarioModel usuario) {
    List<RelacionamentoSeguidor> relacionamentos = relacionamentoSeguidorRepository.findBySeguido(usuario);

    List<UsuarioModel> seguidores = new ArrayList<>();
    for (RelacionamentoSeguidor relacionamento : relacionamentos) {
        seguidores.add(relacionamento.getSeguidor());
    }

    return seguidores;
}

@Override
public List<UsuarioModel> listarSeguindo(UsuarioModel usuario) {
    List<RelacionamentoSeguidor> relacionamentos = relacionamentoSeguidorRepository.findBySeguidor(usuario);
    
    List<UsuarioModel> seguindo = new ArrayList<>();
    for (RelacionamentoSeguidor relacionamento : relacionamentos) {
        seguindo.add(relacionamento.getSeguido());
    }

    return seguindo;
}

@Override
public boolean verificarSeUsuarioSegue(UsuarioModel seguidor, UsuarioModel seguido) {
    return relacionamentoSeguidorRepository.findBySeguidorAndSeguido(seguidor, seguido).isPresent();
}

public Long contarSeguidores(UsuarioModel usuario) {
    return relacionamentoSeguidorRepository.countBySeguido(usuario);
}

public Long contarSeguindo(UsuarioModel usuario) {
    return relacionamentoSeguidorRepository.countBySeguidor(usuario);
}

}