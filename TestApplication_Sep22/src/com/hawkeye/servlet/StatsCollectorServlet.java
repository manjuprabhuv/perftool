package com.hawkeye.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.lang.management.MemoryUsage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hawkeye.beans.HawkEyeMemoryBean;
import com.hawkeye.beans.JSONAspectRequest;
import com.hawkeye.beans.JSONVitalStatsRequest;
import com.hawkeye.beans.SQLStatsBean;
import com.hawkeye.beans.TraceStatsBean;
import com.hawkeye.utils.StatsPersistance;

/**
 * Servlet implementation class StatsCollectorServlet
 */
public class StatsCollectorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StatsCollectorServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			// resultStream = new ObjectInputStream(request.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream()));
			String requeststr = "";
			String line;
			while ((line = br.readLine()) != null) {
				requeststr = requeststr + line;
			}
			if (requeststr.contains("requestId")) {
				handleStats(requeststr);
			} else if (requeststr.contains("HeapMemory")) {
					handleVitalStats(requeststr);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.write("HAWKEYE-200");
	}

	private void handleVitalStats(String requeststr) {
		Gson gson = new Gson();
		JSONVitalStatsRequest jsonreq = gson.fromJson(requeststr,
				JSONVitalStatsRequest.class);
		Map<String, List<HawkEyeMemoryBean>> memoryMap = jsonreq.getMemoryMap();

		List<HawkEyeMemoryBean> heapMemory = memoryMap.get("HeapMemory");
		for (HawkEyeMemoryBean memoryUsage : heapMemory) {
			// memoryUsage.ge
			StatsPersistance.addVitalStatsToDB("HeapMemory", memoryUsage.getTs(),
					memoryUsage.getInit(), memoryUsage.getUsed(),
					memoryUsage.getComitted(), memoryUsage.getMax());
		}
		List<HawkEyeMemoryBean> nonHeapMemory = memoryMap.get("NonHeapMemory");
		for (HawkEyeMemoryBean memoryUsage2 : nonHeapMemory) {
			// memoryUsage.ge
			StatsPersistance.addVitalStatsToDB("NonHeapMemory", memoryUsage2.getTs(),
					memoryUsage2.getInit(), memoryUsage2.getUsed(),
					memoryUsage2.getComitted(), memoryUsage2.getMax());
		}

	}

	private void handleStats(String requeststr) {
		Gson gson = new Gson();
		JSONAspectRequest jsonreq = gson.fromJson(requeststr,
				JSONAspectRequest.class);
		List<SQLStatsBean> sqlstatsList = jsonreq.getSqlStatsList();
		long requestId = jsonreq.getRequestId();
		for (SQLStatsBean sqlStatsBean : sqlstatsList) {
			// TODO :Make it batch.
			StatsPersistance.addSQLStatsToDB(sqlStatsBean.getTimestamp(),
					sqlStatsBean.getSqlStatment(), sqlStatsBean.getExecTime(),
					sqlStatsBean.getFailedFlag(), requestId);
		}
		List<TraceStatsBean> tracestatsList = jsonreq.getTraceStatsList();
		for (TraceStatsBean traceStatsBean : tracestatsList) {
			// TODO :Make it batch.
			StatsPersistance.addTraceStatsToDB(traceStatsBean.getMethodName(),
					traceStatsBean.getInputParams(),
					traceStatsBean.getAction(), traceStatsBean.getReturnVals(),
					traceStatsBean.getExecTime(), requestId);
		}

	}

}
