package com.hawkeye.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private ConfigReader conf = new ConfigReader();;
	

	private ConfigReader()  {
		props = new Properties();
		try {
			props.load(new FileInputStream("E:\\Perftool\\TestApplication\\src\\hawkeye.properties"));			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Properties props;


	public static Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

}
