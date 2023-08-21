package br.com.curso_rest.xml;


import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;


public class UsandoXML {

	@Test
	public void utilizandoXML() {
		RestAssured.given()
		.when()
			.get("http://restapi.wcaquino.me/usersXML/3")
		.then()
			.statusCode(200)
			.body("user.name", Matchers.is("Ana Julia"))
			.body("user.@id", Matchers.is("3"))
			.body("user.filhos.name.size()", Matchers.is(2))
			.body("user.filhos.name[0]", Matchers.is("Zezinho"))
			.body("user.filhos.name", Matchers.hasItems("Zezinho", "Luizinho"))
			;
	}

	@Test
	public void utilizandoXML_II() {
		RestAssured.given()
		.when()
			.get("http://restapi.wcaquino.me/usersXML/3")
		.then()
			.statusCode(200)
			
			.rootPath("user") //define a raiz. Comparar ao enterior. supressão do user
			.body("name", Matchers.is("Ana Julia"))
			.body("@id", Matchers.is("3"))
	
			.rootPath("user.filhos")
//			.body("filhos.name.size()", Matchers.is(2))
			.body("name.size()", Matchers.is(2))
	
			.detachRootPath("filhos") // retira o "nó" colocado no rootPath
			.body("filhos.name[0]", Matchers.is("Zezinho"))
//			.body("name[0]", Matchers.is("Zezinho"))
	
			.appendRootPath("filhos") // retorna o "nó" retirado no detachRootPath
//			.body("filhos.name", Matchers.hasItems("Zezinho", "Luizinho"))
			.body("name", Matchers.hasItems("Zezinho", "Luizinho"))
			;
	}
}
