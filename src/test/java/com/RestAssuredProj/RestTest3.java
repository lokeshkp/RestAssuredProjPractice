package com.RestAssuredProj;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.io.InputStream;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestTest3 {

	//@Test
	public void testGetResponseAsString(){
		String response=get("http://services.groupkt.com/country/search?text=lands").asString();
		System.out.println(response);
	}
	
	//@Test
	public void testGetResponseAsInputStream() throws IOException{
		InputStream iStream=get("http://services.groupkt.com/country/search?text=lands").asInputStream();
		System.out.println(iStream.toString().charAt(20));
		iStream.close();
	}
	
	//@Test
	public void testGetResponseAsByteArray() throws IOException{
		byte[] byteArray=get("http://services.groupkt.com/country/search?text=lands").asByteArray();
		System.out.println(byteArray.toString().length());		
	}
	
	//@Test
	public void testExtractDetailsUsingPath(){
		
		String href=
		when().
			get("http://jsonplaceholder.typicode.com/photos/1").
		then().
			contentType(ContentType.JSON).
			body("albumId", equalTo(1)).
		extract().
			path("thumbnailUrl");
		
		System.out.println(href);
		
		when().
			get(href).
		then().
			statusCode(200);
	}
	
	//@Test
	public void testSingleLineExtractDetails(){
		//type1
		String href1=get("http://jsonplaceholder.typicode.com/photos/1").path("url");
		System.out.println(href1);		
		get(href1).then().statusCode(200);
		
		//type2
		String href2=get("http://jsonplaceholder.typicode.com/photos/2").andReturn().jsonPath().getString("thumbnailUrl");
		System.err.println(href2);
		when().get(href2).then().statusCode(200);
	}
	
	@Test
	public void testExtractDetailsUsingResponse(){
		Response resp= when().get("http://services.groupkt.com/country/search?text=lands").then().extract().response();
		//System.err.println(resp.contentType());
		System.err.println(resp.getStatusLine());
	}
}
