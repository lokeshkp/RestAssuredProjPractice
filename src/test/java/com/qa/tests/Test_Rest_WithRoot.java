package com.qa.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static io.restassured.path.json.JsonPath.*;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.meta.When;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class Test_Rest_WithRoot {
	
	//@Test
	public void testWithoutRoot(){
		
		given().
			get("http://services.groupkt.com/state/get/IND/AP").
		then().			
			body("RestResponse.result.name", is("Andhra Pradesh")).
			body("RestResponse.result.area", is("49506799SKM")).
			body("RestResponse.result.largest_city", is("Hyderabad Amaravati"));			
	}
	
	//@Test
	public void testRestAPI(){
		given().
			get("http://services.groupkt.com/state/get/IND/AP").
		then().
			statusCode(200).
			log().
			all();
	}
	
	//@Test
	public void testWithRoot(){
		given().
			get("http://services.groupkt.com/state/get/IND/AP").
		then().
			root("RestResponse.result").
			body("name", is("Andhra Pradesh")).
			body("area", is("49506799SKM")).
			body("largest_city", is("Hyderabad Amaravati"));
	}
	
	//@Test
	public void testDetachRoot(){
		
		given().
			get("http://services.groupkt.com/state/get/IND/KA").
		then().
			root("RestResponse.result").
			body("name", is ("Karnataka")).
			body("area", is ("61095297SKM")).			
			detachRoot("result").
			body("messages", hasItem("State found matching code [KA]."));
			//body("result.largest_city", is("Bangalore"));
	}
	
	//@Test
	public void testXMLAPI(){
		given().
			get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10").
		then().
			statusCode(200).
			log().
			all();
	}
	
	//@Test
	public void testRestAPIHasItesm(){
		given().
			get("http://services.groupkt.com/state/get/IND/all").
		then().
			body("RestResponse.result.name", hasItems("Kerala","Karnataka","Andhra Pradesh"));
	}
	
	//@Test
	public void testRestAPIPost(){
		given().
			headers("AppKey","Key-value").
			param("wfsfirst_name", "first").
			param("wfslast_name", "last").
			param("wfsemail", "email").
		when().
			post("http://api.fonts.com/rest/json/Accounts/").
		then().
			statusCode(400).log().all();
	}
	
	//@Test
	public void testExtractURLandHit(){
		String res =given().
			get("http://jsonplaceholder.typicode.com/photos/1").
		then().extract().path("url");
		System.err.println(res);
		
		given().get(res).then().statusCode(200).log().all();
	}
	

	//@Test
	public void testRestAPIRespose(){
		Response res = given().
			get("http://jsonplaceholder.typicode.com/photos/1").
		then().
			extract().response();
		System.err.println("Content Type-->"+res.contentType());
		System.err.println("Href-->" + res.path("url"));
		System.err.println("Status Code-->"+ res.statusCode());
	}
	
	//@Test
	public void testRestAPISchema(){
		//given().get("http://services.groupkt.com/state/get/IND/all").then().assertThat().body(matchesJsonSchemaInClasspath("test"));
	}
	
	//@Test
	public void testGroovyFeature(){
		given().
			get("http://services.groupkt.com/country/search?text=islands").
		then().
			body("RestResponse.result.name",hasItems("Cook Islands","Solomon Islands")).log().all();
		
	}
	
	//@Test
	public void testGroovyfeatureRestAPI(){
		given().
			get("http://services.groupkt.com/country/search?text=islands").
		then().
			body("RestResponse.result.alpha3_code*.length().sum()", greaterThan(42));
	}
	
	//@Test
	public void testGroovyFeatureRestAPI1(){
		String response = given().get("http://services.groupkt.com/country/search?text=islands").asString();
		List<String> ls = from(response).getList("RestResponse.result.name");
		System.out.println(ls.size());
		for(String name:ls){
			System.err.println(name);
		}
	}
	
	//@Test
	public void testGrovvyFeature2(){
		String response = given().get("http://services.groupkt.com/country/search?text=lands").asString();		
		List<String> ls = from(response).getList("RestResponse.result.findAll{it.name.length()>30}.name");
		System.out.println(ls);
		
	}
	
	//@Test
	public void testCONNECTRestAPI(){
		when().request("CONNECT", "https://api.fonts.com/rest/json/Accounts").then().statusCode(400);
		
	}
	
	//@Test
	public void testQueryParam(){
		given().
			queryParam("A", "A Value").
			queryParam("B", "B Value").
		when().
			get("https://api.fonts.com/rest/json/Accounts").
		then().statusCode(500);
	}
	
	//@Test
	public void testFormParam(){
		given().formParam("A", "A Value").formParam("B", "B Value").when().get("https://api.fonts.com/rest/json/Accounts").then().statusCode(500);
	}
	
	//@Test
	public void testParam(){
		given().param("A", "A value").param("B", "B Value").when().post("https://api.fonts.com/rest/json/Accounts").then().statusCode(400);
	}
	
	@Test
	public void testSetMultivalueParam(){
		List<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		
		given().
			param("A", "val1","val2","val3").
			param("B", list).
			param("C").when().get("https://api.fonts.com/rest/json/Accounts").then().statusCode(400);
	}
}
