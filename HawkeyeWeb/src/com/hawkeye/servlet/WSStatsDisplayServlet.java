package com.hawkeye.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hawkeye.beans.SQLStatsBean;
import com.hawkeye.beans.WebserviceStatsBean;
import com.hawkeye.db.DerbyDbUtils;

/**
 * Servlet implementation class WSStatsDisplayServlet
 */
public class WSStatsDisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WSStatsDisplayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<WebserviceStatsBean> list = DerbyDbUtils.getListOfWSStats();
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("/WebserviceDisplay.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
