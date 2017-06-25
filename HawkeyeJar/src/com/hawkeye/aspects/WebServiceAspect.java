package com.hawkeye.aspects;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.hawkeye.agent.aggregator.Monitor;
import com.hawkeye.agent.aggregator.StatisticsAggregatorAndForwarder;
import com.hawkeye.beans.WebserviceStatsBean;
import com.sun.xml.ws.util.ByteArrayBuffer;


@Aspect
public class WebServiceAspect {
	
	
	@Before(" call(private * com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump(..)) "
			+ "&& !within(com.test.aspects..*) && !within(com.hawkeye.db..*)")
	public void beforeCall2(JoinPoint jp) {
		
		String type = (String) jp.getArgs()[1];
		if(type.contains("request")){
			ByteArrayBuffer buff = (ByteArrayBuffer) jp.getArgs()[0];
			String requestString = new String(buff.toByteArray());
			Monitor.getWebserviceStatsThread().setRequest(requestString);
			
		}else{
			ByteArrayBuffer buff = (ByteArrayBuffer) jp.getArgs()[0];
			String requestString = new String(buff.toByteArray());
			Monitor.getWebserviceStatsThread().setResponse(requestString);
			Monitor.getWebserviceStatsThread().setTimestamp(new java.sql.Timestamp(new Date().getTime()));
			StatisticsAggregatorAndForwarder.flushWSStats(Monitor.getWebserviceStatsThread());
		}
		
		
	}

	@Before(" call(private * com.sun.xml.ws.transport.http.HttpAdapter.dump(..)) "
			+ "&& !within(com.test.aspects..*) && !within(com.hawkeye.db..*)")
	public void beforeCall3(JoinPoint jp) {
		System.out.println("About to make call to print Hello World2");
		System.out.println("beforeCall3:: " + jp);
	

	}
}
