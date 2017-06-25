package com.hawkeye.aspects;

import java.util.Date;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class JspEventsAspects {
	
	
	
	
	@Around("execution(public void javax.servlet.jsp.HttpJspPage+._jspService(javax.servlet.http.HttpServletRequest," +
			"	javax.servlet.http.HttpServletResponse)) && !within(com.hawkeye.*..*) && !within(com.hawkeye.db..*) && !within(com.hawkeye.db..*)")
	public Object trace(ProceedingJoinPoint call)
			throws Throwable {
		
		
	//	System.out.println("***********************mbean.getTraceEventEnabled()***********************"+mbean.getTraceEventEnabled());

		Logger logger = (call.getTarget() == null) ? Logger
				.getLogger(TraceLoggingAspect.class.getName()) : Logger
				.getLogger(call.getTarget().getClass().getName());

		print(logger, true, call, null, 0);
		Date d = new Date();
		long startTime = d.getTime();
		Object point = call.proceed();
		Date d2 = new Date();
		long endTime = d2.getTime();
		long executionTime = (endTime - startTime);

	

		print(logger, false, call, point, executionTime);

		return point;

	}

	public void print(Logger logger, boolean entry, ProceedingJoinPoint call,
			Object retVal, long time) {
		try {
			if (entry) {
				String paramList = "";
				for (int i = 0; i < call.getArgs().length; i++) {
					paramList = paramList + call.getArgs()[i] ;
					if(!(i==call.getArgs().length-1)){
						paramList = paramList +" , ";
					}
				}
				logger.info("TRACE jsp:: ENTERING >> " + call.toLongString()
						+ " with param : [" + paramList + "]");
			} else {
				logger.info("TRACE jsp :: EXITING << [" + call.toLongString()
						+ "] with return as: [" + String.valueOf(retVal)
						+ "] [executionTime : " + time + " mSecs]");
			}

		} catch (Exception ignore) {
		}
	}

	

}
