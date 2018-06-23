package com.qa.restapi;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {	
	
	
	public void get(String url) throws ClientProtocolException, IOException, JSONException{
		
		CloseableHttpClient httpClient = HttpClients.createDefault();	
		 
		HttpGet httpget = new HttpGet(url);
		
		CloseableHttpResponse httpResponse = httpClient.execute(httpget);		
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code.." + statusCode);
		
		String responseString = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
		JSONObject resJson = new JSONObject(responseString);
		System.out.println("JSON Response :"+resJson);
		
		Header[] headersArray = httpResponse.getAllHeaders();
		
		HashMap<String, String> hmap = new HashMap<String, String>();
		
		for(Header header:headersArray){
			hmap.put(header.getName(), header.getValue());
		}
		
		System.out.println("Headers Array :"+hmap);
	}

}
