package br.com.curso_rest.secao11_autenticacoes;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;

public class AplicacaoWeb {

	@Test
	public void deveLogarAplicacaoWeb() {
		
		String cookie =RestAssured
		.given()
			.log().all()
			.formParam("email", "saulohrodrigues@gmail.com")
			.formParam("senha", "123456")
			.contentType(ContentType.URLENC.withCharset("UTF-8"))
		
		.when()
	//		.get("http://admin:senha@restapi.wcaquino.me/basicauth")
			.post("http://seubarriga.wcaquino.me/logar")
		.then()
			.log().all()
			.statusCode(200)
			.extract().header("set-cookie");
			
		;
	
		cookie = cookie.split("=")[1].split(";")[0];
		System.out.println("Cookie 02 "+cookie.toString());
		String bodyExtraido =
		RestAssured
			.given()
				.log().all()	
				.cookie("connect.sid", cookie)
				
			.when()
		//		.get("http://admin:senha@restapi.wcaquino.me/basicauth")
				.get("http://seubarriga.wcaquino.me/contas")
			.then()
				.log().all()
				.statusCode(200)
				.body("html.body.table.tbody.tr[0].td[0]", Matchers.is("Conta para movimentacoes"))
				.extract().body().asString();
			;
			System.out.println("==================================");
		XmlPath xmlPath = new XmlPath(CompatibilityMode.HTML, bodyExtraido);
		System.out.println("XmlPath = "+ xmlPath.getString("html.body.table.tbody.tr[0].td[0]"));
	}
}
