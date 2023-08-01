package br.com.curso_rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultRowSorter;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.StringStartsWith;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTests {

	@Test
	public void olaMundo() {
		Response request = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		System.out.println("body da api: "+request.getBody().asString());
		System.out.println("body da api: "+request.getBody().asString().equals("Ola Mundo!"));
		System.out.println("status code: "+request.statusCode());
		System.out.println("status code: "+(request.statusCode()==200));
		
		ValidatableResponse validacao = request.then();
//		validacao.statusCode(201); // gera falha
//		throw new RuntimeException();//gera erro
		
		Assert.assertTrue(request.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue((request.statusCode()==200));
		Assert.assertTrue("O status code deveria ser 200",request.statusCode()==200);
		Assert.assertEquals(200,request.statusCode());	
	}
	
	@Test
	public void outrasFormasRestAssured() {
		Response request = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");		
		ValidatableResponse validacao = request.then();
		validacao.statusCode(200); // gera falha
		RestAssured.get("http://restapi.wcaquino.me/ola").then().statusCode(201);
		RestAssured.given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(201);
	}
	
	@Test
	public void conhecendoMatchersHamcrest() {
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(128, Matchers.is(128));
		Assert.assertThat(128, Matchers.isA(Integer.class));
		Assert.assertThat(128d, Matchers.isA(Double.class));
		Assert.assertThat(128d, Matchers.greaterThan(120d));// 128 > 120
		Assert.assertThat(128d, Matchers.lessThan(130d));// 128 < 130
		
		List<Integer> lista = Arrays.asList(1,3,5,7,9);
		Assert.assertThat(lista, Matchers.hasSize(5));// compara o numero de posiçoe da lista
		Assert.assertThat(lista, Matchers.contains(1,3,5,7,9));// compara o numero de posiçoe da lista
		Assert.assertThat(lista, Matchers.containsInAnyOrder(3,5,1,9,7));// compara o numero de posiçoe da lista
		Assert.assertThat(lista, Matchers.hasItem(1));// compara o numero de posiçoe da lista
		Assert.assertThat(lista, Matchers.hasItems(1,3));// compara o numero de posiçoe da lista
		Assert.assertThat("Maria", Matchers.not("maria"));
//		Assert.assertThat("Maria", Matchers.any("joao","jose","Maria"));
		Assert.assertThat("Joaquina", Matchers.allOf(Matchers.startsWith("Joa"),
				Matchers.endsWith("ina")));		
	}
	
	@Test
	public void devoValidarBody() {
		
	}
}
