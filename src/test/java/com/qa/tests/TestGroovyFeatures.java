package com.qa.tests;

import org.apache.http.Header;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Headers;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.response.Response.*;

import static org.hamcrest.Matchers.*;
import static io.restassured.path.json.JsonPath.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class TestGroovyFeatures {

	//@Test
	public void testPresenseOfElements(){
		given().
			get("http://services.groupkt.com/country/search?text=lands").
		then().
			body("RestResponse.result.name", hasItems("Faroe Islands","Solomon Islands"));
	}
	
	//@Test
	public void testLengthOfResponse(){
		given().
			get("http://services.groupkt.com/country/search?text=lands").
		then().
			body("RestResponse.result.alpha2_code*.length().sum()",greaterThan(30));
	}
	
	//@Test
	public void testGetReposeAsList(){
		String res = get("http://services.groupkt.com/country/search?text=lands").asString();
		List<String> ls = from(res).getList("RestResponse.result.name");
		System.out.println("Total Countries List "+ls.size());
		
		for(String country: ls){
			if(country.equals("Solomon Islands")){
				System.out.println("Found given place...");
			}
		}		
	}
	
	//@Test
	public void testConditionsOnList(){
		String res = get("http://services.groupkt.com/country/search?text=lands").asString();
		List<String> ls = from(res).getList("RestResponse.result.findAll { it.name.length() > 20 }.name");
		System.out.println(ls);
	}
	
	//@Test
	public void testJsonPath1(){
		String responseAsString=
				when().
					get("http://jsonplaceholder.typicode.com/photos").
				then().
				extract().asString();
		
		List<Integer> albIds = from(responseAsString).get("id");
		System.out.println(albIds.size());
	}
	
	// Extract details as String and fetching further details using JSONPath
	
	//@Test
	public void testJsonPath2(){
		String json=
				when().
					get("http://services.groupkt.com/country/get/all").
				then().
				extract().asString();
		
		JsonPath jsPath = new JsonPath(json).setRoot("RestResponse.result");
		
		List<String> list = jsPath.get("name");
		System.out.println(list.size());
	}
	
	// To Get response headers
	//@Test
	public void testResponseHeaders(){
		Response resp = get("http://jsonplaceholder.typicode.com/photos");		
		String header = resp.getHeader("CF-RAY");
		System.out.println(">>>>> Header :"+header);
		System.out.println("");
		
		Headers heads = resp.getHeaders();
		for(io.restassured.http.Header head:heads){
			System.out.println(head.getName()+":"+head.getValue());
		}		
	}
	
	// To Get response cookies
	//@Test
	public void testCookies(){
		Response resp = get("http://jsonplaceholder.typicode.com/photos");
		Map<String, String> cookies = resp.getCookies();
		
		for(Map.Entry<String, String> entry : cookies.entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue());
		}		
	}
	
	// To Get response cookies
	//@Test
	public void testDetaildCookies(){
		Response resp = get("http://jsonplaceholder.typicode.com/photos");
		Cookie a = resp.getDetailedCookie("__cfduid");
		System.out.println("Detailed:"+a.hasExpiryDate());			
		System.out.println("Detailed:"+a.getExpiryDate());
		System.out.println("Detailed:"+a.hasValue());
	}
	
	// CONNECT with HTTPS
	
	//@Test
	public void testConnectRequest(){
		when().
			request("CONNECT","https://api.fonts.com/rest.json/Accounts").
		then().
			statusCode(400);
	}
	
	// Setting query parameter
	
	//@Test
	public void testQueryParameters(){
		given().
			queryParam("key1", "value1").
			queryParam("key2", "value2").
		when().
			get("https://api.fonts.com/rest/json/Accounts").
		then().
			statusCode(400);
	}
	
	// Posting form parameter
	
	//@Test
	public void testFormParameters(){
		given().
			formParam("key1", "value1").
			formParam("key2", "value2").
		when().
			post("https://api.fonts.com/rest/json/Accounts").
		then().
			statusCode(400);
	}
	
	// Posting parameter
	
	//@Test
	public void testParameters(){
		given().
			param("key1", "value1").
			param("key2", "value2").
		when().
			get("https://api.fonts.com/rest/json/Accounts").
		then().
			statusCode(400);
	}
	
	// To Set multiple value parameters
	// we can pass list, multiple values or no values in param
	
	//@Test
	public void testSetMultiValueParameters(){
		List<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		
		given().
			param("A","val1","val2").
			param("B").
			param("C",list).
		when().
			get("https://api.fonts.com/rest/json/Accounts").
		then().statusCode(400);
	}
	
	// Setting Path parameters
	//@Test
	public void testSetPathParameters2(){
		given().
			pathParam("type", "json").
			pathParam("Section", "Domians").
		when().
			post("https://api.fonts.com/rest/{type}/{Section}").
		then().
			statusCode(400);
	}
	
	// Cookies can be set as request param
	//@Test
	public void testSetCookieInRequest(){
		given().
			cookie("__utmt","1").
		when().
			get("http://www.webservicex.com/globalweather.asmx?op=GetCitiesByCountry").
		then().
			statusCode(200);
	}
	
	
	// Passing single header, header with multiple values and multiple headers
	//@Test
	public void testSetHeaders(){
		given().
			header("key","val").
			header("key1","va1","va2").
			headers("key2","val1","key3","val3").
		when().
			get("https://api.fonts.com/rest/json/Accounts").
		then().
			statusCode(400);
	}
	
	// Setting the content type
	//@Test
	public void testSetContentType(){
		given().
			contentType(ContentType.JSON).
			contentType("application/json; charset=utf-8").
		when().
			get("https://api.fonts.com/rest/json/Accounts").
		then().
			statusCode(400);
	}
	
	
	// Status code verification
	//@Test
	public void testResponseStatus(){
		given().get("http://jsonplaceholder.typicode.com/photos").then().assertThat().statusCode(200).log().all();
		given().get("http://jsonplaceholder.typicode.com/photos").then().assertThat().statusLine("HTTP/1.1 200 OK");
		given().get("http://jsonplaceholder.typicode.com/photos").then().assertThat().statusLine(containsString("OK"));
	}
	
	// Headers verofication
	//@Test
	public void testResponseHeader(){
		given().get("http://jsonplaceholder.typicode.com/photos").then().assertThat().header("X-Powered-By","Express");
		given().get("http://jsonplaceholder.typicode.com/photos").then().assertThat().headers("Vary", "Accept-Encoding","Content-Type",containsString("json"));
	}
	
	
	// Content type verification
	//@Test
	public void testContentTypeInReponse(){
		given().get("http://jsonplaceholder.typicode.com/photos").then().assertThat().contentType(ContentType.JSON);		
	}
	
	
	// Body text verification
	//@Test
	public void testBodyInResponse(){
		String resp = get("http://jsonplaceholder.typicode.com/photos").asString();
		given().get("http://jsonplaceholder.typicode.com/photos").then().assertThat().body(equalTo(resp));
	}
	
	
	
	// body attribute verification using java 8 Lambda expression
	//@Test
	public void testBodyParametersInResponse(){
		given().get("http://jsonplaceholder.typicode.com/photos").then().body("thumbnailUrl", new ResponseAwareMatcher<Response>() {
			
			public Matcher<?> matcher(Response resp) throws Exception {
				// TODO Auto-generated method stub
				return equalTo("http://placehold.it/150/92c952");
			}
		});
		
		
		// above or below
		
		
		// With java 8 lambda expression
		given().get("http://jsonplaceholder.typicode.com/photos/1").then().body("thumbnailUrl", resp-> equalTo("http://placehold.it/150/92c952"));
		given().get("http://jsonplaceholder.typicode.com/photos/1").then().body("thumbnailUrl", endsWith("92c952"));
	}
	
	
	// cookie value changing one every hit
	@Test
	public void testCookiesInResponse(){
		given().get("http://jsonplaceholder.typicode.com/photos/1").then().log().all().assertThat().cookie("__fdf","sfdfsfsdfs");
	}
	
}
