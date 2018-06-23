package com.qa.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class TestResrBasicFeatures {
	
	//@Test
	public void testStatusCode(){
		given().
			get("http://jsonplaceholder.typicode.com/posts/3").
		then().
			statusCode(200);
	}
	
	//@Test
	public void testLogging(){
		given().
			get("http://jsonplaceholder.typicode.com/posts/3").
		then().
			statusCode(200).
			log().all();
	}
	
	//@Test
	public void testEqualToFunction(){
		given().
			get("http://services.groupkt.com/state/get/IND/AP").
		then().
			body("RestResponse.result.capital", equalTo("Hyderabad Amaravati"));
	}
	
	
	
	//@Test
	public void testHasItemsFunction(){
		given().
			get("http://services.groupkt.com/country/get/all").
		then().
			body("RestResponse.result.name", hasItems("Benin","India","China"));
	}
	
	@Test
	public void testParametersAndHeaders(){
		given().
			param("key1", "value1").
			header("headA","valueA").
		when().
			get("http://services.groupkt.com/state/get/IND/AP").
			then().statusCode(200).log().all();
			
	}
	
}
