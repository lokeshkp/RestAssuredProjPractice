package com.RestAssuredProj;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class RestTest2 {

	//@Test
	public void testWithOutRoot(){
		given().
			get("http://services.groupkt.com/state/get/IND/AP").
		then().
			body("RestResponse.result.name", is("Andhra Pradesh")).
			body("RestResponse.result.largest_city",is("Hyderabad Amaravati")).log().all();
			
	}
	
	//@Test
	public void testWithRoot(){
		given().
			get("http://services.groupkt.com/state/get/IND/KA").
		then().
			root("RestResponse.result").
			body("name", is("Karnataka")).
			body("largest_city", is ("Bangalore"));
	}
	
	//@Test
	public void testDetachRoot(){
		given().
			get("http://services.groupkt.com/state/get/IND/all").
		then().
			root("RestResponse.result").
			body("name", hasItems("Karnataka")).
			body("largest_city", hasItems ("Bangalore")).
			detachRoot("result").
			body("result.id", hasItem(58)).log().all();
	}
	
	@Test
	public void testPostRequest(){
		given().
			headers("Appkey","Hey-Value").
			param("firstName", "Lokesh").
			param("lastName", "Kondepudi").
			param("email", "kplokesh@gmail.com").
		when().
			post("http://api.fonts.com/rest/json/Accounts/").
		then().
			statusCode(401).log().all();
	}
}
