package br.com.curso_rest;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserJsonTest {

	@Test
	public void jsonPrimeiroNivel() {
		RestAssured.given().when().get("https://restapi.wcaquino.me/users/1").then().statusCode(200)
				.body("id", Matchers.is(1)).body("name", Matchers.containsString("Silva"))
				.body("age", Matchers.greaterThan(18));
	}

	@SuppressWarnings("removal")
	@Test
	public void jsonPrimeiroNivel_II() {
		Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/users/1");

		// path
		Assert.assertEquals(new Integer(1), response.path("id"));
		Assert.assertEquals(new Integer(1), response.path("%s", "id"));

//		jsonpath	
		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertEquals(1, jsonPath.getInt("id"));

//		from
		int id = JsonPath.from(response.asString()).getInt("id");
		Assert.assertEquals(1, id);

	}

	@Test
	public void jsonSegundoNivel() {
		RestAssured.given().when().get("https://restapi.wcaquino.me/users/2").then().statusCode(200)
				.body("id", Matchers.is(2)).body("name", Matchers.containsString("Joaquina"))
				.body("endereco.rua", Matchers.is("Rua dos bobos"));
	}

	@Test
	public void jsonLista() {
		RestAssured
			.given()
			.when()
				.get("https://restapi.wcaquino.me/users/3")
			.then().statusCode(200)
				.body("id", Matchers.is(3)).body("name", Matchers.containsString("Ana"))
				.body("filhos", Matchers.hasSize(2)).body("filhos[0].name", Matchers.is("Zezinho"))
				.body("filhos[1].name", Matchers.is("Luizinho")).body("filhos.name", Matchers.hasItem("Luizinho"))
				.body("filhos.name", Matchers.hasItems("Luizinho", "Zezinho"));
	}

	@Test
	public void erroUsuarioInesistente() {
		RestAssured
			.given()
			.when()
				.get("https://restapi.wcaquino.me/users/4")
			.then()
				.statusCode(404)
				.body("error", Matchers.is("Usuário inexistente"))
			;
	}

}
