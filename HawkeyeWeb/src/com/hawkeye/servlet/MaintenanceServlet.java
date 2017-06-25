package com.hawkeye.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hawkeye.db.DerbyDbUtils;

/**
 * Servlet implementation class MaintenanceServlet
 */
public class MaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	String status="";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Table(request.getParameter("table"));
		response.getWriter().write(status);
	}


	private void Table(String tab) {
		// TODO Auto-generated method stub
		String tabname="";
		if (tab.equalsIgnoreCase("SQL"))
			tabname = "HAWKEYE.SQL_STATS";
		else if(tab.equalsIgnoreCase("Trace"))
			tabname = "HAWKEYE.TRACE_STATS";
		else if(tab.equalsIgnoreCase("WS"))
			tabname = "HAWKEYE.WS_STATS";
		else if(tab.equalsIgnoreCase("Vital"))
			tabname = "HAWKEYE.VITAL_STATS";
		status = DerbyDbUtils.TruncateTab(tabname); 

	}



}
