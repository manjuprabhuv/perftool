package com.hawkeye.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hawkeye.beans.TraceStatsBean;
import com.hawkeye.db.DerbyDbUtils;

/**
 * Servlet implementation class MemoryUIServlet
 */
public class MemoryUIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemoryUIServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, List<long[]>> map = DerbyDbUtils.getListofMemoryStats(
				"HeapMemory");
		Gson gson = new Gson();

		String maxGsonString = gson.toJson(map.get("max"));
		System.out.println(maxGsonString);
		String usedGsonString = gson.toJson(map.get("used"));
		System.out.println(usedGsonString);
		request.setAttribute("used", usedGsonString);
		request.setAttribute("max", maxGsonString);
		RequestDispatcher rd = request.getRequestDispatcher("/MemoryGraph.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
