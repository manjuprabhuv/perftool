package com.hawkeye.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChkStatusServlet extends HttpServlet{
	
	String status="";
	/**
	 * 
	 */
	private static final long serialVersionUID = -4800287698390011916L;
	/**
	 * @param args
	 */
	
	public String GetStatus(String url)
	{
		
		URL u;
		try {
			u = new URL (url);
		HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
		huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD"); 
		huc.connect () ; 
		int code = huc.getResponseCode() ;

		if (code == 200)
			status = "Running";
		else
			status = "Stopped";
		} catch (MalformedURLException e) {
			status = "Stopped";
		} catch (IOException e) {
			status = "Stopped";
		}
		
		return status;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		GetStatus(request.getParameter("url"));
		response.getWriter().write(status);

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		

	}

}
