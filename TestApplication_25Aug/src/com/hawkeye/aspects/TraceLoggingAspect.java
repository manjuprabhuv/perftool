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

import com.hawkeye.db.DerbyDbConnection;
import com.hawkeye.db.DerbyDbUtils;
import com.hawkeye.mbeans.DataBaseEventsMBeans;
import com.hawkeye.mbeans.TraceEventsMBeans;
import com.hawkeye.utils.ConfigReader;

@Aspect
public class TraceLoggingAspect {
	TraceEventsMBeans mbean;

	// Monitor m = new Monitor();
	public TraceLoggingAspect() {
		DerbyDbConnection.getInstance().init();
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		try {
			ObjectName name = new ObjectName(
					"com.hawkeye.mbeans:type=TraceEventsMBeans");
			mbean = TraceEventsMBeans.getInstance();
			if (ConfigReader.getProps() != null
					&& ConfigReader.getProps().getProperty("app.package") != null
					&& ConfigReader.getProps().getProperty("app.package")
							.length() > 0) {
				mbean.setPackageName(ConfigReader.getProps().getProperty(
						"app.package"));
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
		/*
		 * StatisticsCollector sc = m.getStatisticsCollectorOfThisThread();
		 * sc.map.put(c, c); c++;
		 */
		if (mbean != null) {
			isTraceAllEnabled = (Boolean) mbean.getAttribute("traceEnabled");
			if (isTraceAllEnabled) {
				packagename = (String) mbean.getAttribute("packageName");
				if ((packagename != null) && (packagename.contains("*")))
					packagename = packagename.substring(0,
							packagename.indexOf("*") - 1);//can be upgraded to list of packages!
			}
		}

		if (packagename != null && packagename != "") {

			if (call.getSignature().getDeclaringTypeName()
					.contains(packagename) )
				return trace(call, isTraceAllEnabled);
			else
				return call.proceed();
		}
		return call.proceed();
		/*else if (isTraceAllEnabled)
			return trace(call, isTraceAllEnabled);

		else
			return call.proceed();*/

	}

	public Object trace(ProceedingJoinPoint call, boolean flag)
			throws Throwable {

		if (flag) {

			Logger logger = (call.getTarget() == null) ? Logger
					.getLogger(TraceLoggingAspect.class.getName()) : Logger
					.getLogger(call.getTarget().getClass().getName());

			trace(logger, true, call, null, 0);
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

			trace(logger, false, call, point, executionTime);

			return point;
		} else {
			return call.proceed();
		}
	}

	public void trace(Logger logger, boolean entry, ProceedingJoinPoint call,
			Object retVal, long time) {
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
			addToDB(call.toLongString(), paramList, action,
					String.valueOf(retVal), time);

		} catch (Exception ignore) {
		}
	}

	@SuppressWarnings("unchecked")
	private void addToDB(String methodname, String paramList, String action,
			String returnval, long executionTime) {

		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		// java.sql.Timestamp timest = new java.sql.Timestamp(d2.getTime());
		map.put("METHOD_NAME", methodname);
		map.put("ACTION", action);
		map.put("PARAMS", "" + paramList);
		map.put("RETURNS", returnval);
		map.put("EXEC_TIME", executionTime);
		DerbyDbUtils.insert("HAWKEYE.TRACE_STATS", map);

	}
}
