package com.sample.package2;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sample.testapp.DBConnDAO;

public class HelperClass2 {

	public String anotherMethod(String inputparam){
		callDBMethod();
		return "input parama is " +inputparam;
	}
	public void callDBMethod(){
		DBConnDAO DBconn = new DBConnDAO();
		Connection conn = null;
		try{
			conn = DBconn.getConnection();
			conn.setAutoCommit(true);
			String statement ="";
			statement = "select user.first_name from printoms.user user where user.cust_id=1";
			PreparedStatement ps = conn.prepareStatement(statement);
			ps.execute();
		}catch(Exception e){
			
		}
	}
}
