package br.com.curso_rest.organizacao_codigo;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class _23AtributosEstatico {

	public static RequestSpecification requereSpec;
	public static ResponseSpecification resonseSpec;
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://restapi.wcaquino.me/";
//		RestAssured.port=80; //se fosse localhost
//		RestAssured.port=443; //se fosse httpS
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.log(LogDetail.ALL);
		requereSpec = reqBuilder.build();
		
		ResponseSpecBuilder respSpecBuilder = new ResponseSpecBuilder();
		respSpecBuilder.expectStatusCode(200);
		resonseSpec = respSpecBuilder.build();
		
	}
	
	@Test
	public void testandoBasePath() {
		RestAssured.basePath = "/v2";
		RestAssured
			.given() //requeste
//				.log().all() //logar a requesição - exibeno console
				.spec(requereSpec)
			.when() //requeste
				.get("users")
			.then() //respose
//				.statusCode(200)
				.spec(resonseSpec)
			;				
	}
	
	@Test
	public void testandoBaseURL() {	
		RestAssured
		.given()
			.spec(requereSpec)
		.when()
			.get("usersXML")
		.then()
//			.statusCode(200)
			.spec(resonseSpec)
			.body( Matchers.hasXPath("count(/users/user)", Matchers.is("3")))
			.body( Matchers.hasXPath("/users/user[@id='1']"))
			.body( Matchers.hasXPath("//user[@id='2']"))	
		;	
	}
}