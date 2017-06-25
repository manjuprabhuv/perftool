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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hawkeye.beans.SQLStatsBean;
import com.hawkeye.beans.TraceStatsBean;
import com.hawkeye.utils.HawkEyeUtils;

public class DerbyDbUtils {

	
	public static String TruncateTab(String table) {
		String status = "Fail";
		String sqlStatement = "truncate table " + table ;
		
		Connection conn = DerbyDbConnection.getInstance().getConnection();
		try {
			PreparedStatement psInsert = conn.prepareStatement(sqlStatement);
			psInsert.executeUpdate();
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
			columnList = columnList + pairs.getKey();
			if (HawkEyeUtils.isLong("" + pairs.getValue())) {
				valueList = valueList + pairs.getValue();
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
		System.out.println(sqlStatement);

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
				SQLStatsBean stats = new SQLStatsBean();
				stats.setExecTime(execTime);
				stats.setFailedFlag(failedFlag);
				stats.setSqlStatment(sqlStatment);
				stats.setTimestamp(timestamp);
				sqlStatslist.add(stats);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sqlStatslist;

	}

	public static List<TraceStatsBean> getListofTraceStats() {
		List<TraceStatsBean> traceStatslist = new ArrayList<TraceStatsBean>();
		String sqlQuery = "select * from HAWKEYE.TRACE_STATS";

		Connection conn = DerbyDbConnection.getInstance().getConnection();

		Statement stmt2;
		try {
			stmt2 = conn.createStatement();
			ResultSet rs = stmt2.executeQuery(sqlQuery);
			int num = 0;
			// FAILED_FLAG VARCHAR(2) default 'N' not null,TIME_STAMP TIMESTAMP
			// not null, constraint SQL120810064036500 primary key (ID))";
			while (rs.next()) {
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
				traceStatslist.add(stats);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return traceStatslist;

	}
	/*
	 * public static void main(String[] args) { Map<String,String> sqlMap = new
	 * HashMap<String,String>(); sqlMap.put("a", "a"); sqlMap.put("b", "b");
	 * sqlMap.put("c", "c"); sqlMap.put("d", "d"); sqlMap.put("s", "s");
	 * update("asd",sqlMap); }
	 */

}
