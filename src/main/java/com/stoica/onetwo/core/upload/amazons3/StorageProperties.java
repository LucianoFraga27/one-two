package com.stoica.onetwo.core.upload.amazons3;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("onetwo-storage")
public class StorageProperties {
	
	private Local local = new Local();
	private S3 s3 = new S3();
	
	@Getter
	@Setter
	public class Local {
		private Path diretorio;
	}
	
	@Getter
	@Setter
	public class S3 {
		private String idChaveAcesso;
		private String idChaveAcessoSecreta;
		private String bucket;
		private Regions regiao;
		private String diretorio;
		
	}
	
}