package br.com.curso_rest;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;




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
	
	
	@SuppressWarnings("removal")
	@Test
	public void jasonPrimeiroNivel_II() {
		Response response = RestAssured.request(Method.GET,"https://restapi.wcaquino.me/users/1");
		
		// path
		Assert.assertEquals(new Integer(1), response.path("id"));
		Assert.assertEquals(new Integer(1), response.path("%s","id"));
		
//		jsonpath	
		JsonPath jsonPath= new JsonPath(response.asString());
		Assert.assertEquals(1, jsonPath.getInt("id"));
		
//		from
		int id = JsonPath.from(response.asString()).getInt("id");
		Assert.assertEquals(1, id);
		
	}
}
