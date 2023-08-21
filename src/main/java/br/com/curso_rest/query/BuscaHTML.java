package br.com.curso_rest.query;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class BuscaHTML {

	@Test
	public void deveRealizarBuscaComHTML() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/v2/users")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body("html.body.div.table.tbody.tr.size()", Matchers.is(3))
			.body("html.body.div.table.tbody.tr[1].td[2]", Matchers.is("25"))
			.appendRootPath("html.body.div.table.tbody")
			.body("tr[1].td[2]", Matchers.is("25"))
			.body("tr.find{it.toString().startsWith('2')}.td[1]", Matchers.is("Maria Joaquina"))
		;
	}
	@Test
	public void deveRealizarBuscaHTMLcomXPATH() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=clean")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
//			.appendRootPath("html.body.div.table.tbody")
//			.body("tr[1].td[2]", Matchers.is("25"))
			.body(Matchers.hasXPath("//td[text()='2']/..//td[2]", Matchers.is("Maria Joaquina")))
		;
	}
}
