package com.stoica.onetwo.domain.musica.persistence;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stoica.onetwo.api.resource.musica.dto.MusicaPostDTO;
import com.stoica.onetwo.core.upload.ArmazenarUpload;
import com.stoica.onetwo.core.upload.ArmazenarUpload.NovoArquivo;
import com.stoica.onetwo.domain.musica.GeneroEnum;
import com.stoica.onetwo.domain.musica.MusicaModel;
import com.stoica.onetwo.domain.musica.MusicaService;
import com.stoica.onetwo.domain.usuario.UsuarioModel;
import com.stoica.onetwo.domain.usuario.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class MusicaServiceImpl implements MusicaService{
	
	MusicaRepository musicaRepository;
	
	ArmazenarUpload armazenarUpload;
	
	UsuarioService usuarioService;
	
	
	@Override
	public List<MusicaModel> findAll() {
		return musicaRepository.findAll();
	}


	@Override
	public MusicaModel findById(Long id) {
		return musicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Falha ao encontrar musica de ID"+id));
	}
	
	@Transactional
	@Override
	public MusicaModel post(MusicaPostDTO musica, InputStream dadosImagem, InputStream dadosAudio) {
		
		usuarioService.findById(musica.getId_usuario());
		
		String capa = armazenarUpload.gerarNomeArquivo(musica.getCapa().getOriginalFilename());
		String audio = armazenarUpload.gerarNomeArquivo(musica.getAudio().getOriginalFilename());
		
		MusicaModel musicaModel = new MusicaModel();
		
		UsuarioModel usuarioModel = usuarioService.findById(musica.getId_usuario());

		musicaModel.setUsuario(usuarioModel);
		
		musicaModel.setTitulo(musica.getTitulo());
		
		musicaModel.setAudio(audio);
		
		musicaModel.setCapa(capa);
		
		musicaModel.setGenero(musica.getGenero());
		
		var save = musicaRepository.save(musicaModel);
		
		musicaRepository.flush();
		
		armazenarUpload.armazenar(NovoArquivo.builder().nomeArquivo(capa).inputStream(dadosImagem).contentType(musica.getCapa().getContentType()).build());
		armazenarUpload.armazenar(NovoArquivo.builder().nomeArquivo(audio).inputStream(dadosAudio).contentType(musica.getAudio().getContentType()).build());
		
		return save;
	}

	@Override
	@Transactional
	public void remove(Long id) {
		MusicaModel entityExists = findById(id);
		musicaRepository.deleteById(id);
		musicaRepository.flush();
		armazenarUpload.remover(entityExists.getAudio());
		armazenarUpload.remover(entityExists.getCapa());
	}


	@Override
	public List<MusicaModel> listByGenero(GeneroEnum generoEnum) {
		List<MusicaModel> musicasDoGeneroUsuario = musicaRepository.findByGenero(generoEnum);

        // Etapa 2: Obtenha todas as músicas restantes ordenadas por gênero
        List<MusicaModel> musicasRestantes = musicaRepository.findAllByOrderByGenero();

        // Combine as duas listas de músicas
        musicasRestantes.removeAll(musicasDoGeneroUsuario); // Remova as músicas duplicadas
        musicasDoGeneroUsuario.addAll(musicasRestantes);

        return musicasDoGeneroUsuario;
		}

		 public void curtirMusica(Long musicaId, Long usuarioId) {
        MusicaModel musica = findById(musicaId);

        UsuarioModel usuario = usuarioService.findById(usuarioId);

        musica.getUsuariosCurtiram().add(usuario);
        musicaRepository.save(musica);
    }

	public void descurtirMusica(Long musicaId, Long usuarioId) {
        MusicaModel musica = findById(musicaId);
        UsuarioModel usuario = usuarioService.findById(usuarioId);
        musica.getUsuariosCurtiram().remove(usuario);
        musicaRepository.save(musica);
    }
	
	public long contarCurtidas(Long musicaId) {
        return musicaRepository.countCurtidas(musicaId);
    }	

	public String curtirOuDescurtirMusica(Long musicaId, Long usuarioId) {
        MusicaModel musica = findById(musicaId);
        UsuarioModel usuario = usuarioService.findById(usuarioId);

        if (musica.getUsuariosCurtiram().contains(usuario)) {
            descurtirMusica(musica.getId(), usuario.getId());
			return "Música descurtida com sucesso.";
        } else {
            curtirMusica(musica.getId(), usuario.getId());
			return "Música curtida com sucesso.";
        }
    }


	@Override
	public List<MusicaModel> listarMusicasDoUsuario(Long usuarioId) {
        UsuarioModel usuario = usuarioService.findById(usuarioId);
		return musicaRepository.findByUsuario(usuario);
    }

	@Override
	public List<MusicaModel> pesquisarPorTermo(String termo) {
        return musicaRepository.pesquisarPorTermo(termo);
    }


	@Override
	public void deletar(Long musicaId) {
		MusicaModel m = findById(musicaId);
		armazenarUpload.remover(m.getAudio());
		armazenarUpload.remover(m.getCapa());
		musicaRepository.deleteById(musicaId);
	}

	@Override
	public Boolean curtiuOuNao(Long musicaId, Long usuarioId) {
		MusicaModel musica = findById(musicaId);
		UsuarioModel usuario = usuarioService.findById(usuarioId);
		return musica.getUsuariosCurtiram().contains(usuario);
	}

	@Override
    public List<MusicaModel> listarMusicasCurtidas(Long usuarioId) {
        UsuarioModel usuario = usuarioService.findById(usuarioId);
        return musicaRepository.findByUsuariosCurtiramContaining(usuario);
    }

}
