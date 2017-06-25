package com.hawkeye.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.hawkeye.beans.SQLStatsBean;
import com.hawkeye.beans.WatchEntriesBean;
import com.hawkeye.db.DerbyDbUtils;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;

public class WatchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7719703019831126021L;

	public void display(HttpServletRequest rq, HttpServletResponse rs)
			throws ServletException, IOException {
	
		List<WatchEntriesBean> list = DerbyDbUtils.WatchEntries();
		
		rq.setAttribute("WatchList", list);
        RequestDispatcher rd = rq.getRequestDispatcher("/MainWatch.jsp");
        rd.forward(rq, rs);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void crud(HttpServletRequest rq, HttpServletResponse rs)
			throws ServletException, IOException {
		ArrayList urls = new ArrayList();
		int i=1;
		
		System.out.println(rq.getContentLength());
		//for(int i=1; i<=rq.getContentLength();i++)
		while(rq.getParameter("url"+i) != null)
		{
			//			
			System.out.println("url"+i);
			urls.add(rq.getParameter("url"+i));
			System.out.println(rq.getParameter("url"+i));
			i = i+1;
		}
		//urls.add(rq.getParameter("url1"));
		//System.out.println(urls.get(0).toString());
		DerbyDbUtils.WatchCRUD(urls);
		ServletContext sc = getServletContext();  
		RequestDispatcher rd = sc.getRequestDispatcher("/MainWatch.jsp");
		rd.forward(rq, rs);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//if (request.getParameter("ch").equalsIgnoreCase("display"))
			display(request,response);

		}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			crud(request,response);

		}
}