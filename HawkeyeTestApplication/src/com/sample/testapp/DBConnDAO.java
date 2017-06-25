package com.sample.testapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;






public class DBConnDAO {
	
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 */
	
	
	public Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			     
		  Connection conn = null;
		  String url = "jdbc:mysql://localhost:3306/";
		  String dbName = "printoms";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName ="root"; 
		  String password = "password";

			  Class.forName(driver).newInstance();
			  conn = DriverManager.getConnection(url+dbName,userName,password);
				
		  
		  return conn;
	}
	
	
}
