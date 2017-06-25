package com.hawkeye.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hawkeye.db.DerbyDbUtils;

public class StatsPersistance {

	@SuppressWarnings("unchecked")
	public static void addTraceStatsToDB(String methodname, String paramList,
			String action, String returnval, long executionTime,long requestId) {

		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		// java.sql.Timestamp timest = new java.sql.Timestamp(d2.getTime());
		map.put("METHOD_NAME", methodname);
		map.put("ACTION", action);
		map.put("PARAMS", "" + paramList);
		map.put("RETURNS", returnval);
		map.put("EXEC_TIME", executionTime);
		map.put("REQUEST_ID", requestId);
		DerbyDbUtils.insert("HAWKEYE.TRACE_STATS", map);

	}

	@SuppressWarnings("unchecked")
	public static void addSQLStatsToDB(Timestamp ts, String sql, long executionTime,
			String failureFlag,long requestId) {

		@SuppressWarnings("rawtypes")
		Map map = new HashMap();		
		map.put("SQL_STMT", sql);
		map.put("EXEC_TIME", executionTime);
		map.put("FAILED_FLAG", failureFlag);
		map.put("TIME_STAMP", ts);
		map.put("REQUEST_ID", requestId);
		DerbyDbUtils.insert("HAWKEYE.SQL_STATS", map);

	}
	//create table HAWKEYE.VITAL_STATS(ID BIGINT not null generated always as identity,TYPE VARCHAR(50) not null,INIT BIGINT not null,USED BIGINT not null,MAX BIGINT not null,COMITTED BIGINT not null,TIME_STAMP TIMESTAMP not null, constraint SQL120810064036502 primary key (ID))";
	@SuppressWarnings("unchecked")
	public static void addVitalStatsToDB(String type, Timestamp ts, long init,
			long used,long comitted,long max) {

		@SuppressWarnings("rawtypes")
		Map map = new HashMap();		
		map.put("TYPE", type);
		map.put("INIT_MEM", init);
		map.put("USED_MEM", used);
		map.put("COMITTED_MEM", comitted);
		map.put("MAX_MEM", max);
		map.put("TIME_STAMP", ts);
		DerbyDbUtils.insert("HAWKEYE.VITAL_STATS", map);

	}

}
