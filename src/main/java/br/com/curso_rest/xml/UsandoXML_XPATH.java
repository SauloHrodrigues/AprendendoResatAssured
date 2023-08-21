package br.com.curso_rest.xml;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;

public class UsandoXML_XPATH {
	@Test
	public void pesquisasComXML_ComXPATH() {
		RestAssured
			.given()
			.when()
				.get("http://restapi.wcaquino.me/usersXML")
			.then()
				.statusCode(200)
				.body( Matchers.hasXPath("count(/users/user)", Matchers.is("3")))
				.body( Matchers.hasXPath("/users/user[@id='1']"))
				.body( Matchers.hasXPath("//user[@id='2']"))
				.body( Matchers.hasXPath("//name[text()='Luizinho']/../../name", Matchers.is("Ana Julia")))
				.body( Matchers.hasXPath("//name[text()='Ana Julia']/following-sibling::filhos",
						Matchers.allOf(Matchers.containsString("Zezinho"),
								Matchers.containsString("Luizinho"))))
				.body(Matchers.hasXPath("//user/name", Matchers.is("João da Silva")))
				.body(Matchers.hasXPath("//user[@id='2']//name", Matchers.is("Maria Joaquina")))
				.body(Matchers.hasXPath("//user[@id='2']//name", Matchers.is("Maria Joaquina")))
				.body(Matchers.hasXPath("//user[last()]//name", Matchers.is("Ana Julia")))
				.body(Matchers.hasXPath("count(/users/user/name[contains(.,'n')])", Matchers.is("2")))
				.body(Matchers.hasXPath("//user[age < 24]/name", Matchers.is("Ana Julia")))
				.body(Matchers.hasXPath("//user[age >= 25][age<30]/name", Matchers.is("Maria Joaquina")))
				.body(Matchers.hasXPath("//user[age >= 25 and age<30]/name", Matchers.is("Maria Joaquina")))
				
			;
				
	}
}
