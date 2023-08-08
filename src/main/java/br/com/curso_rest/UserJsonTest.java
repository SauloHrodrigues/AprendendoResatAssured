package br.com.curso_rest;

import java.util.List;

import org.hamcrest.Matchers;
import org.hamcrest.number.IsCloseTo;
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
	public void jsonListaNaRaiz() {
		RestAssured
			.given()
			.when()
				.get("https://restapi.wcaquino.me/users")
			.then()
				.statusCode(200)
				.body("$",Matchers.hasSize(3))
		;
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

	@Test
	public void verificacoesAvancadas() {
		RestAssured
		.given()
		.when()
		.get("https://restapi.wcaquino.me/users")
		.then()
		.statusCode(200)
		.body("$", Matchers.hasSize(3))
		.body("name", Matchers.hasItem("João da Silva"))
		.body("name", Matchers.hasItems("João da Silva","Maria Joaquina","Ana Júlia"))
		.body("salary", Matchers.contains(1234.5678f,2500,null))
		.body("age.findAll{it <= 25}.size()", Matchers.is(2))
		.body("age.findAll{it <= 25 && it > 20}.size()", Matchers.is(1))
		.body("findAll{it.age <= 30}[0].name", Matchers.is("João da Silva"))
		.body("findAll{it.age <= 30}[-1].name", Matchers.is("Ana Júlia"))//ultimo registro
		.body("find{it.age <= 30}.name", Matchers.is("João da Silva"))
		.body("findAll{it.name.contains('n')}.name", Matchers.hasItems("Ana Júlia", "Maria Joaquina"))//ultimo registro
		.body("findAll{it.name.length()<10}.name", Matchers.hasItem("Ana Júlia"))//ultimo registro
		
		;
	}
	@Test
	public void verificacoesAvancadasII() {
		RestAssured
		.given()
		.when()
		.get("https://restapi.wcaquino.me/users")
		.then()
		.statusCode(200)
		.body("findAll{it.name.length()<10}.name", Matchers.hasItem("Ana Júlia"))//ultimo registro
		.body("name.collect{it.toUpperCase()}", Matchers.hasItem("JOÃO DA SILVA"))
		.body("name.findAll{it.startsWith('Maria')}"
				+ ".collect{it.toUpperCase()}", Matchers.hasItem("MARIA JOAQUINA"))
		.body("name.findAll{it.startsWith('Maria')}"
				+ ".collect{it.toUpperCase()}.toArray()", Matchers.allOf(
						Matchers.arrayContaining("MARIA JOAQUINA"), 
						Matchers.arrayWithSize(1)))
		.body("age.collect{it *2}", Matchers.hasItems(60,50,40))
		.body("id.max()", Matchers.is(3))
		.body("salary.min()", Matchers.is(1234.5677f))
		.body("salary.max()", Matchers.is(2500))
		.body("salary.findAll{it != null}.sum()", Matchers.is(
				Matchers.closeTo(3734.5677f, 0.001)))
		.body("salary.findAll{it != null}.sum()", Matchers.allOf(
				Matchers.greaterThan(3000d), Matchers.lessThan(4000d)))
		
		;
	}
	
	@Test
	public void unindoJsonPathJava() {
		List<String> nomes =
			RestAssured
			.given()
			.when()
			.get("https://restapi.wcaquino.me/users")
			.then()
			.statusCode(200)
			.extract().path("name.findAll{it.startsWith('Maria')}")
			;
		for(String nome : nomes) {
			System.out.println("nome: "+ nome.intern());
		}
	}
}