package com.hawkeye.aspects;

import java.lang.management.ManagementFactory;
import java.sql.PreparedStatement;
import java.util.Date;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.hawkeye.agent.aggregator.Monitor;
import com.hawkeye.agent.aggregator.StatisticsAggregator;
import com.hawkeye.agent.aggregator.StatisticsAggregatorAndForwarder;
import com.hawkeye.beans.JSONAspectRequest;
import com.hawkeye.beans.SQLStatsBean;
import com.hawkeye.mbeans.DataBaseEventsMBeans;

@Aspect
public class DataBaseEventLoggingAspect {

	DataBaseEventsMBeans mbean;

	public DataBaseEventLoggingAspect() {

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
		if (mbean.getDatabaseEventEnabled()) {
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
		Monitor.getStatisticsCollectorOfThisThread().addSQLBean(bean);
		/*
		 * if(Monitor.getStatisticsCollectorOfThisThread().getReq().getSqlStatsList
		 * ().size()==10){ JSONAspectRequest req =
		 * Monitor.getStatisticsCollectorOfThisThread().getReq();
		 * JSONAspectRequest newReq = new
		 * JSONAspectRequest(Monitor.getStatisticsCollectorOfThisThread
		 * ().genRequestId());
		 * Monitor.getStatisticsCollectorOfThisThread().setReq(newReq);
		 * Monitor.getStatisticsCollectorOfThisThread().setFirstRequest(false);
		 * StatisticsAggregatorAndForwarder.flushStats(req); }
		 */

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
