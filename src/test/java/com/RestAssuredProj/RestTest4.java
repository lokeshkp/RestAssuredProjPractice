package com.RestAssuredProj;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.path.json.JsonPath.*;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RestTest4 {
	
	//@Test
	public void testGroovyWithRest1(){
		when().
			get("http://services.groupkt.com/country/search?text=lands").
		then().
			body("RestResponse.result.alpha3_code*.length().sum()", greaterThan(45));		
	}
	
	//@Test
	public void testGetResponseAsList(){
		String response=get("http://services.groupkt.com/state/get/IND/all").asString();
		List<String> ls= from(response).getList("RestResponse.result.name");
		
		System.out.println("Total States "+ls.size());
		for(String state:ls){
			if(state.equals("Andhra Pradesh")){
				System.out.println(state);
			}
		}		
	}
	
	//@Test
	public void testConditionOnList(){
		String response = get("http://services.groupkt.com/state/get/IND/all").asString();
		List<String> ls=  from(response).getList("RestResponse.result.findAll { it.name.length() > 20}.");
		System.out.println(ls);
		
	}
	
	
	@Test
	public void testJsonPath1(){
		
		Response Json=
				get("https://dev.virtualearth.net/REST/V1/Imagery/Metadata/Road?output=json&key=Aky6WBikbCgZ-gtvZZ6M8mfPytpDNGkppjCnLfGsrRemofWdXIXmstRq26LjNIe0").
			then().
			extract().response();
		System.err.println(Json.body());
		//JsonPath jsonPath = new JsonPath(Json).setRoot("RestResponse.result");
		
	}

}
