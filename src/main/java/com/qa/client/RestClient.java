package com.qa.client;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {
	
	// GET Method
	
	public void get(String url) throws ClientProtocolException, IOException, JSONException{
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		
		// Status Code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code-->"+statusCode);
		
		// JSON String
		String responseString = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
		JSONObject jsonObj = new JSONObject(responseString);  					
		System.out.println("Response JSON-->"+jsonObj);
		
		// ALL Headers
		Header[] headerArray = httpResponse.getAllHeaders();		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header:headerArray){
			allHeaders.put(header.getName(), header.getName());
		}
		
		System.out.println("All Headers-->"+allHeaders);
		
	}
	
	
	
	
	
	

}
