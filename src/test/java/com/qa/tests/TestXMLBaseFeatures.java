package com.qa.tests;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class TestXMLBaseFeatures {
	
	@Test
	public void testSingleContent(){
		given().
			get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10").
		then().
			body("CUSTOMER.ID", equalTo("10")).
			log().all();
	}

}
