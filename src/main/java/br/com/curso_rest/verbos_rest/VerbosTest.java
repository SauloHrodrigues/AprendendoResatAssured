package br.com.curso_rest.verbos_rest;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.Test;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class VerbosTest {

	@Test
	public void deveSalvarUsuario() {
		given()
			.log().all()
			.contentType("application/json") //onforma o formado a ser passado
			.body("{\"name\":\"Jose\",\"age\":50}")
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", Matchers.is(Matchers.notNullValue()) )
			.body("name", Matchers.is("Jose") )
			.body("age", Matchers.is(50) )
		;
	}
	
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		given()
		.log().all()
		.contentType("application/json") //onforma o formado a ser passado
		.body("{\"age\":50}")
	.when()
		.post("http://restapi.wcaquino.me/users")
	.then()
		.log().all()
		.statusCode(400)
		.body("id", Matchers.is(Matchers.nullValue()) )
		.body("error", Matchers.is("Name é um atributo obrigatório") )

	;
	}

	@Test
	public void salvarUsuarioViaXML() {
		given()
			.log().all()
			.contentType("application/XML; charset=utf-8") //informa o formado a ser passado
//			.contentType(ContentType.XML) //informa o formado a ser passado
			.body("<user><name>José da Silva</name><age>30</age></user>")
		.when()
			.post("http://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", Matchers.is(Matchers.notNullValue()) )
			.body("user.name", Matchers.is("José da Silva") )
			.body("user.age", Matchers.is("30") )
		;
	}
//	**************** PUT
	@Test
	public void deveAlterarUsuario() {
		given()
			.log().all()
//			.config().decoderConfig(decoderConfig().defaultContentCharset("UTF-8"))
			.contentType("application/json") //onforma o formado a ser passado
			.body("{\"name\":\"Usuario Alterado\",\"age\":46}")
		.when()
//			.put("http://restapi.wcaquino.me/users/1")
			.put("http://restapi.wcaquino.me/{xpto}/{userId}","users","1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", Matchers.is(1) )
			.body("name", Matchers.is("Usuario Alterado") )
			.body("age", Matchers.is(46) )
			.body("salary", Matchers.is(1234.5678f) )
		;
	}
	
//	DELETE *************************
	
	@Test
	public void deveDeletarUsuario() {
		RestAssured
			.given()
				.log().all()
			.when()
				.delete("http://restapi.wcaquino.me/users/1")
			.then()
				.log().all()
				.statusCode(204)
		;
	}
	@Test
	public void deveDeletarUsuarioInesistente() {
		RestAssured
		.given()
			.log().all()
		.when()
			.delete("http://restapi.wcaquino.me/users/100")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", Matchers.is("Registro inexistente"))
		;
	}
	

}

