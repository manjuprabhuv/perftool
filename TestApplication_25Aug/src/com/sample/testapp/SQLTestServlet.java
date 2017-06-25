package com.sample.testapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hawkeye.factory.DBConnDAO;

public class SQLTestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String status = "";

	public String GetStatus(String ch) throws SQLException {
		status = ch;
		DBConnDAO DBconn = new DBConnDAO();
		Connection conn = null;
		try {
			conn = DBconn.getConnection();
			conn.setAutoCommit(true);
			String statement ="";
			int id = (int)Math.random() *100;
			if ("insert".equalsIgnoreCase(ch)) {
				statement = "insert into product values (?,?,?)";
				PreparedStatement ps = conn.prepareStatement(statement);
				ps.setInt(1, 4);
				ps.setString(2, "sampleProd");
				ps.setString(3, "sample Product");
				ps.execute();

			} else if ("update".equalsIgnoreCase(ch)) {
				
				statement = "update product set product_id=?,product=? where product_id=?";
				PreparedStatement ps = conn.prepareStatement(statement);
				ps.setInt(1, 4);
				ps.setString(2, "sampleProdU");				
				ps.setInt(3, 4);
				ps.execute();

			} else if ("delete".equalsIgnoreCase(ch)) {
				
				statement = "delete from product where product_id=?";
				PreparedStatement ps = conn.prepareStatement(statement);
				ps.setInt(1, 4);
				ps.execute();
			} else if ("select".equalsIgnoreCase(ch)) {
				statement = "SELECT * FROM product";
				PreparedStatement ps = conn.prepareStatement(statement);
				ps.execute();

			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			GetStatus(request.getParameter("choice"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write(status);

	}

}
