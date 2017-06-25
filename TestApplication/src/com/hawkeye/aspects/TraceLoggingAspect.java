package com.hawkeye.aspects;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.hawkeye.agent.aggregator.StatisticsAggregator;
import com.hawkeye.agent.aggregator.StatisticsAggregatorAndForwarder;
import com.hawkeye.beans.JSONAspectRequest;
import com.hawkeye.beans.TraceStatsBean;
import com.hawkeye.db.DerbyDbConnection;
import com.hawkeye.db.DerbyDbUtils;
import com.hawkeye.mbeans.DataBaseEventsMBeans;
import com.hawkeye.mbeans.TraceEventsMBeans;
import com.hawkeye.statsThread.VitalStatsThread;
import com.hawkeye.utils.ConfigReader;

@Aspect
public class TraceLoggingAspect {
	TraceEventsMBeans mbean;

	Monitor m = Monitor.getMonitor();
	

	public TraceLoggingAspect() {
		
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		VitalStatsThread t = new VitalStatsThread();
		t.start();
		try {
			ObjectName name = new ObjectName(
					"com.hawkeye.mbeans:type=TraceEventsMBeans");
			mbean = TraceEventsMBeans.getInstance();
			if (ConfigReader.getProps("app.package") != null
					&& ConfigReader.getProps("app.package").length() > 0) {
				mbean.setPackageName(ConfigReader.getProps("app.package"));
				mbean.setTraceEnabled(true);
			}
			mbs.registerMBean(mbean, name);
			// mbs.invoke(name, "traceAllEnabled", null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	boolean isTraceAllEnabled = false;
	// boolean isTraceEnabledfrommbean = false;
	String packagename = "";

	@Around("execution(* *(..))"
			+ "&& !within(com.hawkeye.*..*) && !within(org.apache.derby.*..*)")
	public Object traceAll(ProceedingJoinPoint call) throws Throwable {

		if (mbean != null) {
			isTraceAllEnabled = (Boolean) mbean.getAttribute("traceEnabled");
			if (isTraceAllEnabled) {
				packagename = (String) mbean.getAttribute("packageName");
				if ((packagename != null) && (packagename.contains("*")))
					packagename = packagename.substring(0,
							packagename.indexOf("*") - 1);// can be upgraded to
															// list of packages!
			}
		}

		if (packagename != null && packagename != "") {

			if (call.getSignature().getDeclaringTypeName()
					.contains(packagename)) {

				return trace(call, isTraceAllEnabled);
			} else
				return call.proceed();
		}
		return call.proceed();
		/*
		 * else if (isTraceAllEnabled) return trace(call, isTraceAllEnabled);
		 * 
		 * else return call.proceed();
		 */

	}

	public Object trace(ProceedingJoinPoint call, boolean flag)
			throws Throwable {

		if (flag) {
			StatisticsAggregator sc = m.getStatisticsCollectorOfThisThread();
			Logger logger = (call.getTarget() == null) ? Logger
					.getLogger(TraceLoggingAspect.class.getName()) : Logger
					.getLogger(call.getTarget().getClass().getName());
			boolean firstRequest = false;
			if (!sc.isFirstRequest()) {
				sc.setFirstRequest(true);
				firstRequest = true;
			}
			trace(logger, true, call, null, 0, false,sc);
			Date d = new Date();
			long startTime = d.getTime();
			Object point = call.proceed();
			Date d2 = new Date();
			long endTime = d2.getTime();
			long executionTime = (endTime - startTime);

			if (executionTime > 500) {
				logger.warning("SLOW EXECUTION>>>>More than " + 500 + " ms [ "
						+ call.toLongString() + " executionTime : "
						+ executionTime + " mSecs]<<<");
			}

			trace(logger, false, call, point, executionTime, firstRequest,sc);

			return point;
		} else {
			return call.proceed();
		}
	}

	public void trace(Logger logger, boolean entry, ProceedingJoinPoint call,
			Object retVal, long time, boolean firstRequest,StatisticsAggregator sc) {
		try {
			String action = "";
			String paramList = "";
			if (entry) {
				paramList = "";
				for (int i = 0; i < call.getArgs().length; i++) {
					paramList = paramList + call.getArgs()[i];
					if (!(i == call.getArgs().length - 1)) {
						paramList = paramList + " , ";
					}
				}
				logger.info("TRACE :: ENTERING >> " + call.toLongString()
						+ " with param : [" + paramList + "]");
				action = "ENTERING";
			} else {
				logger.info("TRACE :: EXITING << [" + call.toLongString()
						+ "] with return as: [" + String.valueOf(retVal)
						+ "] [executionTime : " + time + " mSecs]");
				action = "EXITING";
			}
			addToAggregator(call.toLongString(), paramList, action,
					String.valueOf(retVal), time, firstRequest,sc);
			//addToDB(call.toLongString(), paramList, action,
				//	String.valueOf(retVal), time);

		} catch (Exception ignore) {
		}
	}

	
	private void addToAggregator(String methodname, String paramList,
			String action, String returnval, long executionTime,
			boolean firstRequest,StatisticsAggregator sc) {
		
		TraceStatsBean bean = new TraceStatsBean();
		bean.setAction(action);
		bean.setExecTime(executionTime);
		bean.setInputParams(paramList);
		bean.setMethodName(methodname);
		bean.setReturnVals(returnval);
		sc.addTraceBean(bean);
		if (firstRequest) {// add other methods
			JSONAspectRequest req = sc.getReq();
			JSONAspectRequest newReq = new JSONAspectRequest(sc.genRequestId());
			sc.setReq(newReq);
			sc.setFirstRequest(false);
			StatisticsAggregatorAndForwarder.flushStats(req);
		}

	}
}
