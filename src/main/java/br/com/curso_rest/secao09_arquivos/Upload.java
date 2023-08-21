package br.com.curso_rest.secao09_arquivos;

import java.io.File;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;

public class Upload {

	@Test
	public void deveObrigarEnviarArquivo() {
		RestAssured
		.given()
			.log().all()
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404)
			.body("error", Matchers.is("Arquivo não enviado"))
		;	
	}
	
	@Test
	public void deveEnviarArquivo() {
		RestAssured
		.given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/users.pdf"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", Matchers.is("users.pdf"))
		;		
	}

	@Test
	public void naoDeveEnviarArquivoGrande() {
		RestAssured
		.given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/TutorialSelenium.docx"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.time(Matchers.lessThan(1000L))
			.statusCode(413)
		
		;		
	}
}