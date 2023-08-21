package br.com.curso_rest.secao07_serializacao;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class Serializacao01 {

	@Test
	public void deveSalvarUsuarioMAP() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("name", "Usuario via map");
		param.put("age", 50);
		RestAssured
			.given()
				.log().all()
				.contentType("application/json")
				.body(param)
			.when()
				.post("http://restapi.wcaquino.me/users")
			.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.is(Matchers.notNullValue()))
				.body("name", Matchers.is("Usuario via map"))
				.body("age", Matchers.is(50))
			;
	}
	
	@Test
	public void deveSalvarUsuarioObjeto() {
		Users param = new Users("Usuario via Objeto",50);
		RestAssured
		.given()
		.log().all()
		.contentType("application/json")
		.body(param)
		.when()
		.post("http://restapi.wcaquino.me/users")
		.then()
		.log().all()
		.statusCode(201)
		.body("id", Matchers.is(Matchers.notNullValue()))
		.body("name", Matchers.is("Usuario via Objeto"))
		.body("age", Matchers.is(50))
		;
	}
	@Test
	public void deveSalvarUsuarioObjetoXML() {
		Users param = new Users("Usuario via Objeto XML",40);
		RestAssured
		.given()
			.log().all()
			.contentType(ContentType.XML)
			.body(param)
		.when()
			.post("http://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", Matchers.is(Matchers.notNullValue()))
			.body("user.name", Matchers.is("Usuario via Objeto XML"))
			.body("user.age", Matchers.is("40"))
		;
	}

	@Test
	public void deveDescerializarUsuarioObjeto() {
		Users param = new Users("Usuario via Descentralizado",25);
		Users usuarioRetornado = RestAssured
		.given()
			.log().all()
			.contentType("application/json")
			.body(param)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(Users.class)
		;
		
		Assert.assertEquals("Usuario via Descentralizado", usuarioRetornado.getName());
		Assert.assertTrue(25 == usuarioRetornado.getAge());	
	}
	@Test
	public void descerialisarUsuarioObjetoXML() {
		Users param = new Users("Usuario via Objeto XML",45);
		Users usuarioNovo = RestAssured
		.given()
			.log().all()
			.contentType(ContentType.XML)
			.body(param)
		.when()
			.post("http://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(Users.class)
		;
		System.out.println(usuarioNovo);
		Assert.assertThat(usuarioNovo.getId(),Matchers.notNullValue());
		Assert.assertThat(usuarioNovo.getName(),Matchers.is("Usuario via Objeto XML"));
		Assert.assertThat(usuarioNovo.getAge(),Matchers.is(45));
		Assert.assertThat(usuarioNovo.getSalary(),Matchers.nullValue());
		
	}
}
