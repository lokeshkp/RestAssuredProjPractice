package com.qa.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;
import java.io.InputStream;


public class TestRest_Post {
	
	//@Test
	public void testPostReq(){
		
		given().
			headers("AppKey","Key-Value").
			param("wfsfirst_name", "first").
			param("wfslast_name", "last").
			param("wfsemail", "test@test.com").
		when().
			post("http://api.fonts.com/rest/json/Accounts/").
		then().
			statusCode(401).log().all();		
	}
	
	//@Test
	public void testGetResponseAsString(){
		String responseString = get("http://services.groupkt.com/country/search?text=lands").asString();
		System.out.println("My Response:\n\n\n"+responseString);
	}
	
	
	//@Test
	public void testGetReponseAsInputStream() throws IOException{
		InputStream ip =get("http://services.groupkt.com/country/search?text=lands").asInputStream();
		System.out.println("Stream Length:" +ip.toString().length());
		ip.close();
	}
	
	
	//@Test
	public void testGetResponseAsByteArray(){
		byte[] byteArray = get("http://services.groupkt.com/country/search?text=lands").asByteArray();
		System.out.println(byteArray.length);
	}
	
	//@Test
	public void testExtractDetailsUsingPath(){
		String href= 
		when().
			get("http://jsonplaceholder.typicode.com/photos/1").
		then().
			contentType(ContentType.JSON).
			//body("albumId", equals(1)).
		extract().path("url");
		
		System.out.println(href);
		
		when().get(href).then().statusCode(200);
	}
	
	//@Test
	public void testExtractPathInOneline(){
		//type1
		String href1 = get("http://jsonplaceholder.typicode.com/photos/1").path("thumbnailUrl");
		System.out.println("Fetched URL 1:"+href1);
		when().get(href1).then().statusCode(200);
		
		//type2
		String href2 = get("http://jsonplaceholder.typicode.com/photos/1").andReturn().jsonPath().getString("thumbnailUrl");
		System.out.println(href2);
		when().get(href2).then().statusCode(200);
	}
	
	
	//@Test
	public void testExtractDetailsusingResponse(){
		Response resp =
		when().
			get("http://jsonplaceholder.typicode.com/photos/1").
		then().
		extract().
			response();
		
		System.out.println("Content Type:" + resp.contentType());
		System.out.println("Href:"+ resp.path("url"));
		System.out.println("Status Code:"+ resp.statusCode());
	}
	
	
	// Schema verification
	@Test
	public void testSchema(){
		given().
			get("http://restapi.demoqa.com/utilities/weather/city/Hyderabad").
		then().
			assertThat().body(matchesJsonSchemaInClasspath("testLokesh.json"));
	}

}
