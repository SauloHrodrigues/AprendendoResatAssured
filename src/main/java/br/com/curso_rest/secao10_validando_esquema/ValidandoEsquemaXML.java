package br.com.curso_rest.secao10_validando_esquema;

import org.junit.Test;
import org.xml.sax.SAXParseException;

import io.restassured.RestAssured;
import io.restassured.matcher.RestAssuredMatchers;


public class ValidandoEsquemaXML {

	@Test
	public void deveValidarEsquemaXMLvalido() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(200)
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd") )
		;
	}

	@Test(expected = SAXParseException.class) // aguardando a exceção
	public void naoDeveValidarEsquemaXMLvalido() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/invalidUsersXML")
		.then()
			.log().all()
			.statusCode(200)
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd") )
		;
	}
}
