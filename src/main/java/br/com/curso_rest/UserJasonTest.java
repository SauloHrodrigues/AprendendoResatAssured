package br.com.curso_rest;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;

public class UserJasonTest {

	@Test
	public void jasonPrimeiroNivel() {
		RestAssured
		.given()
		.when()
			.get("https://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", Matchers.is(1))
			.body("name", Matchers.containsString("Silva"))
			.body("age", Matchers.greaterThan(18))
		;
	}
}
