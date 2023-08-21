package br.com.curso_rest.secao09_arquivos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;

public class DownLoad {

	@Test
	public void deveBaixarrArquivo() throws IOException {
		byte[] image = RestAssured
		.given()
			.log().all()
			
		.when()
			.get("http://restapi.wcaquino.me/download")
		.then()
//			.log().all()
//			.time(Matchers.lessThan(1000L)) //tempo limite de espera
			.statusCode(200)
			.extract().asByteArray();
		;	
		
		File imagem = new File("src/main/resources/arquivo.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		System.out.println("O tamanho da omagem é : "+imagem.length());
		Assert.assertThat(imagem.length(), Matchers.lessThan(100000L));
	}
}
