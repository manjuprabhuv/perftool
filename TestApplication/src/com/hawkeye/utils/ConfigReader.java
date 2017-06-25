package com.hawkeye.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigReader {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private ConfigReader conf = new ConfigReader();;
	

	private ConfigReader()  {
		props = new Properties();
		
		ResourceBundle rb = ResourceBundle.getBundle("/hawkeye");
		Enumeration<String> e =  rb.getKeys();
		while(e.hasMoreElements()){
			String key = e.nextElement();
			String value = rb.getString(key);
			props.put(key, value);
		}
	
	}
	
	private static Properties props;


	public static String getProps(String key) {
		return props.getProperty(key);
	}

	public void setProps(String key,String value) {
		props.put(key, value);
	}

}
