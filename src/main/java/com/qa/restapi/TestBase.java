package com.qa.restapi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

	public Properties pro;
	
	public TestBase(){
		try{
			pro = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/qa/config/config.properties");
			pro.load(fis);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException i){
			i.printStackTrace();
		}
	}
	
}
