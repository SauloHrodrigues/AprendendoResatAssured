package br.com.curso_rest.secao11_autenticacoes;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;

public class AcessandoApiPublica {

	@Test
	public void deveAcessarSWAPI() {
		RestAssured
		.given()
			.log().all()
		.when()
			.get("https://swapi.dev/api/people/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", Matchers.is("Luke Skywalker"))
		;
	}
	
	// key = 042159083c4afceb81c01bf5cfc21c57
	//https://api.openweathermap.org/data/2.5/weather?q=Campinas,BR&appid=042159083c4afceb81c01bf5cfc21c57&units=metric
	
	@Test
	public void deveObterClima() {
		RestAssured
		.given()
			.log().all()
			.queryParam("q", "Campinas,BR")
			.queryParam("appid", "042159083c4afceb81c01bf5cfc21c57")
			.queryParam("units", "metric")
		.when()
			.get("https://api.openweathermap.org/data/2.5/weather")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", Matchers.is("Campinas"))
			.body("coord.lon", Matchers.is(-47.0608f))
			.body("coord.lat", Matchers.is(-22.9056f))
			
		;
	}
}
