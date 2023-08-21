package br.com.curso_rest.secao10_validando_esquema;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;

public class ValidandoEsquemaJSON {

	@Test
	public void deveValidarEsquemaXMLvalido() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(200)
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json") )
		;
	}

}
