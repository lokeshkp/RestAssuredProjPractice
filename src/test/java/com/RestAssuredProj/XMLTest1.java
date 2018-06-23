package com.RestAssuredProj;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class XMLTest1 {

	@Test
	public void testSingleContent(){
		given().
			get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10").
		then().
			body("CUSTOMER.ID", equalTo("10")).
			log().all();
	}		
}
