package com.qa.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestUtil {
	
	public JSONObject resJson;
	
	public static String getValueByJPath(JSONObject resJson, String jpath) throws JSONException{
		Object obj = resJson;
		for(String s:jpath.split("/")){
			if(!s.isEmpty())
				if(!(s.contains("[") || s.contains("]"))){
					obj = ((JSONObject) obj).get(s);
				}else if(s.contains("[") || s.contains("]")){
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
				}
		}
		
		return obj.toString();
		
	}

}
