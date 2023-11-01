package com.stoica.onetwo.core.upload;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface ArmazenarUpload {
	ArquivoRecuperado recuperar(String nomeArquivo);

	void armazenar(NovoArquivo novaFoto);

	default void substituir(String nomeArquivoAntigo, NovoArquivo novoArquivo) {
		this.armazenar(novoArquivo);
		if (nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}

	void remover(String nomeArquivo);

	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}

	@Builder
	@Getter
	public class NovoArquivo {
		private String nomeArquivo;
		private InputStream inputStream;
		private String contentType;

	}

	@Builder
	@Getter
	public class ArquivoRecuperado {
		private InputStream inputStream;
		private String url;

		public boolean temUrl() {
			return url != null;
		}

		public boolean temInputStream() {
			return inputStream != null;
		}
	}
}
