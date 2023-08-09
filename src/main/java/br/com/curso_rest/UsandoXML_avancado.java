package br.com.curso_rest;

import java.util.ArrayList;
import java.util.Iterator;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.internal.path.xml.NodeImpl;


public class UsandoXML_avancado {


	@Test
	public void pesquisasComXML() {
		RestAssured.given()
		.when()
			.get("http://restapi.wcaquino.me/usersXML")
		.then()
			.statusCode(200)
			.body("users.user.size()", Matchers.is(3))
			.body("users.user.findAll{it.age.toInteger() <= 25}.size()", Matchers.is(2))
			.body("users.user.@id", Matchers.hasItems("1","2","3")) // Em XML os valores são Strings
			.body("users.user.find{it.age == 25}.name", Matchers.is("Maria Joaquina")) // Em XML os valores são Strings
			.body("users.user.findAll{it.name.toString().contains('n')}.name", Matchers.hasItems("Maria Joaquina","Ana Julia")) // Em XML os valores são Strings
			.body("users.user.salary.find{it != null}", Matchers.is("1234.5678"))
			.body("users.user.salary.find{it != null}.toDouble()", Matchers.is(1234.5678))
			.body("users.user.age.collect{it.toInteger() *2}", Matchers.hasItems(60,50,40))
			.body("users.user.name.findAll{it.toString().startsWith('Maria')}.collect{"
					+ "it.toString().toUpperCase()}", Matchers.is("MARIA JOAQUINA"))
			;
	}
	
	@Test
	public void pesquisasComXML_ComJava() {
		Object path = RestAssured
				.given()
				.when()
					.get("http://restapi.wcaquino.me/usersXML")
				.then()
					.statusCode(200)
					.extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}.collect{"
							+ "it.toString().toUpperCase()}")
				;
		System.out.println(path.toString());
		Assert.assertEquals("MARIA JOAQUINA", path.toString());
	} 
	
	@Test
	public void pesquisasComXML_ComJavaII() {
//		Object nomes = RestAssured
		
/*		ArrayList<String> nomes = RestAssured
 * 
 * Quando é feita uma pesquisa do RestAssured o retorno não é uma String,
 * mas um objeto NodeImpl. 
 */
		ArrayList<NodeImpl> nomes = RestAssured
	
			.given()
			.when()
				.get("http://restapi.wcaquino.me/usersXML")
			.then()
				.statusCode(200)
				.extract().path("users.user.name.findAll{it.toString().contains('n')}") // Em XML os valores são Strings
		;
		
		System.out.println(nomes.get(0));
		System.out.println(nomes.get(1));
		Assert.assertEquals(2, nomes.size());
		Assert.assertEquals("Maria Joaquina".toUpperCase(), nomes.get(0).toString().toUpperCase());
		Assert.assertTrue("Ana Julia".equalsIgnoreCase(nomes.get(1).toString()));
	}
}
