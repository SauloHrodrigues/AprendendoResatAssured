package br.com.curso_rest.secao11_autenticacoes;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;

public class AutenticacaoBasica {

	@Test
	public void naoDeveAcessarSemSenha() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(401)
			
		;
	}

	@Test
	public void deveFazerAutenticacaoBasica() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("http://admin:senha@restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", Matchers.is("logado"))
		;
	}

	@Test
	public void deveFazerAutenticacaoBasica02() {
		RestAssured
		.given()
			.log().all()
			.auth().basic("admin", "senha")
		
		.when()
//		.get("http://admin:senha@restapi.wcaquino.me/basicauth")
		.get("http://restapi.wcaquino.me/basicauth")
		.then()
		.log().all()
		.statusCode(200)
		.body("status", Matchers.is("logado"))
		;
	}

	@Test
	public void deveFazerAutenticacaoBasicaChallenge() {
		RestAssured
		.given()
			.log().all()
			.auth().preemptive().basic("admin", "senha")
		
		.when()
	//		.get("http://admin:senha@restapi.wcaquino.me/basicauth")
			.get("http://restapi.wcaquino.me/basicauth2")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", Matchers.is("logado"))
		;
	}
}
