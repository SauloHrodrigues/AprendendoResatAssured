package br.com.curso_rest.inicial_basico;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundo {
	public static void main(String[] args) {
		Response request = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		System.out.println("body da api: "+request.getBody().asString());
		System.out.println("body da api: "+request.getBody().asString().equals("Ola Mundo!"));
		System.out.println("status code: "+request.statusCode());
		System.out.println("status code: "+(request.statusCode()==200));
		
		ValidatableResponse validacao = request.then();
		validacao.statusCode(200);
	}
}
