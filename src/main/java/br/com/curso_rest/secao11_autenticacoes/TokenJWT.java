package br.com.curso_rest.secao11_autenticacoes;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TokenJWT {

//	jwt.io
	@Test
	public void deveFazerAutenticacaoTokenJWC() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "saulohrodrigues@gmail.com");
		login.put("senha", "123456");
		String token = RestAssured
		.given()
			.log().all()
			.body(login)
			.contentType(ContentType.JSON)
		.when()
			.post("http://barrigarest.wcaquino.me/signin")
		.then()
			.log().all()
			.statusCode(200)
			.extract().path("token");
			
		;
		System.out.println("************ obter contas ************");
		//obter contas
		RestAssured
		.given()
			.log().all()
			.header("Authorization","JWT "+token)
		.when()
			.get("http://barrigarest.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)	
			.body("nome", Matchers.hasItem("Conta Teste"))
		;
		
	}
}
