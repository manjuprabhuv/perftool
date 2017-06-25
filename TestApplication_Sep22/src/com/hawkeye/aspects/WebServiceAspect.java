package com.hawkeye.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class WebServiceAspect {
	@Before(" call(private * com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump(..)) "
			+ "&& !within(com.test.aspects..*) && !within(com.hawkeye.db..*)")
	public void beforeCall2(JoinPoint jp) {
		System.out.println("About to make call to print Hello World2");
		System.out.println("beforeCall2:: " + jp);
		
	}

	@Before(" call(private * com.sun.xml.ws.transport.http.HttpAdapter.dump(..)) "
			+ "&& !within(com.test.aspects..*) && !within(com.hawkeye.db..*)")
	public void beforeCall3(JoinPoint jp) {
		System.out.println("About to make call to print Hello World2");
		System.out.println("beforeCall3:: " + jp);
	

	}
}
