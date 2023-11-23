package com.stoica.onetwo.core.upload.amazons3;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URL;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.stoica.onetwo.core.upload.ArmazenarUpload;

@Service
public class ArmazenarUpload_S3_impl implements ArmazenarUpload {

	@Autowired
	private AmazonS3 amazonS3;
	
	
	@Autowired
	private StorageProperties storageProperties;
	
	
	@Override
	public ArquivoRecuperado recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
		return ArquivoRecuperado.builder()
				.url(url.toString()).build();
	}

	
	@Override
	public void armazenar(NovoArquivo novaFoto) {
		
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(novaFoto.getContentType());
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					caminhoArquivo,
					novaFoto.getInputStream(),
					objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			System.out.println("ERRO < -------- > "+e.getLocalizedMessage());
			throw new RuntimeException("Erro ao armazenar na Amazon S3 - Storage Exception");
		}
	}
	
	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorio(), nomeArquivo);
	}
	
	@Override
	public void remover(String nomeArquivo) {
	    try {
	        System.err.println("Excluindo do bucket S3");
			String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

	        var deleteObjectRequest = new DeleteObjectRequest(
	                storageProperties.getS3().getBucket(), caminhoArquivo);
	        amazonS3.deleteObject(deleteObjectRequest);
			
	        System.err.println("Excluido com Sucesso do bucket S3");
	    } catch (Exception e) {
			System.out.println("ERRO < -------- > "+e.getLocalizedMessage());
	    	throw new RuntimeException("Erro ao remover na Amazon S3 - Storage Exception");
	    }
	}

	
}
