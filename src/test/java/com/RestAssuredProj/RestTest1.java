package com.RestAssuredProj;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class RestTest1 {
	
	@Test
	public void testStatusCode(){
		given().get("http://jsonplaceholder.typicode.com/posts/3").then().statusCode(200);			
	}
	
	//@Test
	public void testLogging(){
		given().get("http://services.groupkt.com/country/get/iso2coe/in").then().statusCode(200).log().all();
	}

	//@Test
	public void testHasItemFunction(){
		given().get("http://services.groupkt.com/country/get/all").then().body("RestResponse.result.name", hasItems("Austria1", "India", "Poland"));
	}
	
	//@Test
	public void testEqualToFunction(){
		given().get("http://services.groupkt.com/country/get/iso2code/IN").then().body("RestResponse.result.name", equalTo("India"));
	}
	
	//@Test
	public void testParameterAndHeaer(){
		System.err.println(this.toString());
		given().
			param("key1", "value1").
			header("headA","valueA").
		when().
			get("http://services.groupkt.com/country/get/iso2code/gb").
		then().
			statusCode(200).
			log().all();
	}
}
