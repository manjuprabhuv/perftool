package com.hawkeye.db;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hawkeye.beans.SQLStatsBean;
import com.hawkeye.beans.TraceStatsBean;
import com.hawkeye.beans.WatchEntriesBean;
import com.hawkeye.utils.HawkEyeUtils;
import com.hawkeye.utils.StatsPersistance;

public class DerbyDbUtils {

	public static String TruncateTab(String table) {
		String status = "Fail";
		String sqlStatement = "truncate table " + table;

		Connection conn = DerbyDbConnection.getInstance().getConnection();
		try {
			PreparedStatement pstrunc = conn.prepareStatement(sqlStatement);
			pstrunc.executeUpdate();
			status = "Success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return status;
	}

	public static boolean insert(String table, Map sqlMap) {
		boolean updated = false;
		String sqlStatement = "insert into " + table + " (";
		String columnList = "";
		String valueList = "";
		Iterator it = sqlMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			String column = (String) pairs.getKey();
			columnList = columnList + column;
			if ("EXEC_TIME".equalsIgnoreCase(column)
					|| "REQUEST_ID".equalsIgnoreCase(column)
					|| "INIT_MEM".equalsIgnoreCase(column)
					|| "MAX_MEM".equalsIgnoreCase(column)
					|| "COMITTED_MEM".equalsIgnoreCase(column)
					|| "USED_MEM".equalsIgnoreCase(column)) {
				valueList = valueList + pairs.getValue();

				// if (HawkEyeUtils.isLong("" + pairs.getValue())) {

			} else {
				valueList = valueList + "'" + pairs.getValue() + "'";
			}
			// sqlStatement = sqlStatement + pairs.getKey() + "='"
			// + pairs.getValue() + "'";
			if (it.hasNext()) {
				columnList = columnList + ",";
				valueList = valueList + ",";
			}

		}
		sqlStatement = sqlStatement + columnList + " ) VALUES ( " + valueList
				+ " )";
	
		Connection conn = DerbyDbConnection.getInstance().getConnection();
		try {
			PreparedStatement psInsert = conn.prepareStatement(sqlStatement);
			psInsert.executeUpdate();
			updated = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updated;

	}

	// public boolean insertBatch()

	public static List<SQLStatsBean> getListofSqlStats() {
		List<SQLStatsBean> sqlStatslist = new ArrayList<SQLStatsBean>();
		String sqlQuery = "select * from HAWKEYE.SQL_STATS";

		Connection conn = DerbyDbConnection.getInstance().getConnection();

		Statement stmt2;
		try {
			stmt2 = conn.createStatement();
			ResultSet rs = stmt2.executeQuery(sqlQuery);

			int num = 0;
			// FAILED_FLAG VARCHAR(2) default 'N' not null,TIME_STAMP TIMESTAMP
			// not null, constraint SQL120810064036500 primary key (ID))";
			while (rs.next()) {
				String sqlStatment = rs.getString("SQL_STMT");
				long execTime = rs.getLong("EXEC_TIME");
				String failedFlag = rs.getString("FAILED_FLAG");
				Timestamp timestamp = rs.getTimestamp("TIME_STAMP");
				long requestId = rs.getLong("REQUEST_ID");
				SQLStatsBean stats = new SQLStatsBean();
				stats.setExecTime(execTime);
				stats.setFailedFlag(failedFlag);
				stats.setSqlStatment(sqlStatment);
				stats.setTimestamp(timestamp);
				stats.setRequestId(requestId);
				sqlStatslist.add(stats);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sqlStatslist;

	}

	public static List<List<TraceStatsBean>> getListofTraceStats() {
		List<List<TraceStatsBean>> traceStatslist = new ArrayList<List<TraceStatsBean>>();
		List<TraceStatsBean> traceRequestIDlist = new ArrayList<TraceStatsBean>();
		String sqlQuery = "select * from HAWKEYE.TRACE_STATS";

		Connection conn = DerbyDbConnection.getInstance().getConnection();

		Statement stmt2;
		try {
			stmt2 = conn.createStatement();
			ResultSet rs = stmt2.executeQuery(sqlQuery);
			
			
			// FAILED_FLAG VARCHAR(2) default 'N' not null,TIME_STAMP TIMESTAMP
			// not null, constraint SQL120810064036500 primary key (ID))";
			while (rs.next()) {
				long requestId = rs.getLong("REQUEST_ID");
							
				String methodName = rs.getString("METHOD_NAME");
				long execTime = rs.getLong("EXEC_TIME");
				String action = rs.getString("ACTION");
				String inputParams = rs.getString("PARAMS");
				String returnVals = rs.getString("RETURNS");

				TraceStatsBean stats = new TraceStatsBean();
				stats.setExecTime(execTime);
				stats.setMethodName(methodName);
				stats.setAction(action);
				stats.setInputParams(inputParams);
				stats.setReturnVals(returnVals);
				stats.setRequestId(requestId);
				traceRequestIDlist.add(stats);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long requestIdCurr = 0;
		int num = 0;
		List<TraceStatsBean> traceRequestIDlistTemp = new ArrayList<TraceStatsBean>();
		for (int i = 0; i < traceRequestIDlist.size(); i++) {
			if(i==0){
				requestIdCurr = traceRequestIDlist.get(i).getRequestId();
			}
			if(requestIdCurr!=traceRequestIDlist.get(i).getRequestId() || i==traceRequestIDlist.size()-1){
				requestIdCurr = traceRequestIDlist.get(i).getRequestId();
				List<TraceStatsBean> temp = new ArrayList<TraceStatsBean>();				
				if( i==traceRequestIDlist.size()-1){
					traceRequestIDlistTemp.add(traceRequestIDlist.get(i));
				}
				temp.addAll(traceRequestIDlistTemp);
				traceStatslist.add(temp);
				num++;
				if(num==5)
					break;
				
				traceRequestIDlistTemp.clear();
				traceRequestIDlistTemp.add(traceRequestIDlist.get(i));
				
			}else{
				traceRequestIDlistTemp.add(traceRequestIDlist.get(i));
			}
			
		}

		return traceStatslist;

	}
	
	
	public static List<WatchEntriesBean> WatchEntries() {
		List<WatchEntriesBean> Watchlist = new ArrayList<WatchEntriesBean>();
		String sqlQuery = "select * from HAWKEYE.WATCHES";

		Connection conn = DerbyDbConnection.getInstance().getConnection();

		Statement stmt3;
		try {
			stmt3 = conn.createStatement();
			ResultSet rs = stmt3.executeQuery(sqlQuery);

			while (rs.next()) {
				String url = rs.getString("URL");
				WatchEntriesBean entries = new WatchEntriesBean();
				entries.setURL(url);

				Watchlist.add(entries);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Watchlist;

	}
	
	@SuppressWarnings("rawtypes")
	public static void WatchCRUD(ArrayList URL) {
		//List<WatchEntriesBean> Watchlist = new ArrayList<WatchEntriesBean>();
		//String url = null;
		String sqlQuery = "truncate table HAWKEYE.WATCHES";
		int i=0;
		Connection conn = DerbyDbConnection.getInstance().getConnection();

		//Statement stmt3;
		try {
			
			PreparedStatement pstrunc = conn.prepareStatement(sqlQuery);
			pstrunc.executeUpdate();

			Iterator itr = URL.iterator();
			while(itr.hasNext())
			{
				System.out.println(URL.get(i).toString());
				StatsPersistance.addWatchList(URL.get(i).toString());
				itr.next();
				i=i+1;
			}
				

			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public static Map<String, List<long[]>> getListofMemoryStats(String type) {

		String sqlQuery = "select * from HAWKEYE.VITAL_STATS where TYPE = ? order by ID desc";

		Connection conn = DerbyDbConnection.getInstance().getConnection();

		Map<String, List<long[]>> returnMap = new HashMap<String, List<long[]>>();
		List<long[]> maxMemlist = new ArrayList<long[]>();
		List<long[]> usedMemlist = new ArrayList<long[]>();
		try {
			PreparedStatement ps = conn.prepareStatement(sqlQuery);

			ps.setString(1, type);
			ps.execute();
			ResultSet rs = ps.getResultSet();

			// FAILED_FLAG VARCHAR(2) default 'N' not null,TIME_STAMP TIMESTAMP
			// not null, constraint SQL120810064036500 primary key (ID))";

			while (rs.next()) {

				long used = rs.getLong("USED_MEM");
				long max = rs.getLong("MAX_MEM");
				Timestamp ts = rs.getTimestamp("TIME_STAMP");
				long[] longMaxarry = new long[2];
				longMaxarry[0] = ts.getTime();
				longMaxarry[1] = max / (1024 * 1024);
				maxMemlist.add(longMaxarry);
				long[] longusedarry = new long[2];
				longusedarry[0] = ts.getTime();
				longusedarry[1] = used / (1024 * 1024);
				usedMemlist.add(longusedarry);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnMap.put("max", maxMemlist);
		returnMap.put("used", usedMemlist);
		return returnMap;

	}
	/*
	 * public static void main(String[] args) { Map<String,String> sqlMap = new
	 * HashMap<String,String>(); sqlMap.put("a", "a"); sqlMap.put("b", "b");
	 * sqlMap.put("c", "c"); sqlMap.put("d", "d"); sqlMap.put("s", "s");
	 * update("asd",sqlMap); }
	 */

}
