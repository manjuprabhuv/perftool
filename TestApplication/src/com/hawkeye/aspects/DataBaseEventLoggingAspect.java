package com.hawkeye.aspects;

import java.lang.management.ManagementFactory;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.hawkeye.agent.aggregator.StatisticsAggregator;
import com.hawkeye.agent.aggregator.StatisticsAggregatorAndForwarder;
import com.hawkeye.beans.JSONAspectRequest;
import com.hawkeye.beans.SQLStatsBean;
import com.hawkeye.db.DerbyDbConnection;
import com.hawkeye.db.DerbyDbUtils;
import com.hawkeye.mbeans.DataBaseEventsMBeans;
import com.hawkeye.servlet.SQLStatsDisplayBean;

@Aspect
public class DataBaseEventLoggingAspect {

	DataBaseEventsMBeans mbean;
	Monitor m = Monitor.getMonitor();
	StatisticsAggregator sc = m.getStatisticsCollectorOfThisThread();

	public DataBaseEventLoggingAspect() {
		DerbyDbConnection.getInstance().init();
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		try {
			ObjectName name = new ObjectName(
					"com.hawkeye.mbeans:type=DataBaseEventsMBeans");
			mbean = DataBaseEventsMBeans.getInstance();
			mbs.registerMBean(mbean, name);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Around(" call(* java.sql..*.execute*(..)) "
			+ "&& !within(com.hawkeye.*..*)")
	public Object beforeCall2(ProceedingJoinPoint jp) throws Throwable {

		String sql = "";
		Object point = null;
		// if (mbean.getDatabaseEventEnabled()) {
		if (jp.getTarget() instanceof PreparedStatement) {
			PreparedStatement ps = (PreparedStatement) jp.getTarget();
			sql = extractSQL("" + jp.getTarget());
			System.out.println("SQL STATEMENT :: " + sql);

			// }
			Date d = new Date();

			long startTime = d.getTime();
			try {
				point = jp.proceed();
				Date d2 = new Date();
				addToCollector(d2, sql, calcExecTime(startTime, d2), "N");

			} catch (Exception e) {
				System.out
						.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SQL FAILED^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				Date d2 = new Date();
				addToCollector(d2, sql, calcExecTime(startTime, d2), "Y");

				throw e;
			}

		} else {
			point = jp.proceed();
		}
		return point;

	}

	private long calcExecTime(long startTime, Date d2) {
		long endTime = 0;
		endTime = d2.getTime();
		return (endTime - startTime);
	}

	private void addToCollector(Date d2, String sql, long executionTime,
			String failureFlag) {

		SQLStatsBean bean = new SQLStatsBean();
		bean.setExecTime(executionTime);
		bean.setFailedFlag(failureFlag);
		bean.setSqlStatment(sql);
		bean.setTimestamp(new java.sql.Timestamp(d2.getTime()));
		sc.addSQLBean(bean);
		if(sc.getReq().getSqlStatsList().size()==10){
			JSONAspectRequest req = sc.getReq();
			JSONAspectRequest newReq = new JSONAspectRequest(sc.genRequestId());
			sc.setReq(newReq);
			sc.setFirstRequest(false);
			StatisticsAggregatorAndForwarder.flushStats(req);
		}

	}

	/*
	 * @Before(" call(* java.sql..*.prepareStatement(..)) " +
	 * "&& !within(com.hawkeye.aspects..*)") public void beforeCall3(JoinPoint
	 * jp) { if (mbean.getDatabaseEventEnabled()) { if (jp instanceof
	 * PreparedStatement) { PreparedStatement ps = (PreparedStatement)
	 * jp.getThis(); System.out.println("PreparedStatement>> " + ps); } } }
	 */

	private String extractSQL(String str) {
		String sql = "";
		if (str != null && str.length() > 0) {
			String[] ary = str.split(": ");
			try {
				sql = ary[1];
			} catch (Exception e) {

			}
		}
		return formatSQLString(sql);

	}

	private String formatSQLString(String sql) {

		sql = sql.replaceAll("'", "''");
		return sql;
	}

}
