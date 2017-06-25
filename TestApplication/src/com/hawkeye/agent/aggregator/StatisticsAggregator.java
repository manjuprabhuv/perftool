package com.hawkeye.agent.aggregator;

import com.hawkeye.beans.JSONAspectRequest;
import com.hawkeye.beans.SQLStatsBean;
import com.hawkeye.beans.TraceStatsBean;

public class StatisticsAggregator {
	public long requestId ;
	
	public long genRequestId(){
		long requestId =(long)( Math.random() * 10000000);;		
		return requestId;
	}
	private boolean firstRequest = false;	

	public boolean isFirstRequest() {
		return firstRequest;
	}

	public void setFirstRequest(boolean firstRequest) {
		this.firstRequest = firstRequest;
	}

	JSONAspectRequest req = new JSONAspectRequest(genRequestId());	
	
	public JSONAspectRequest getReq() {
		return req;
	}

	public void setReq(JSONAspectRequest req) {
		this.req = req;
	}

	public void addTraceBean(TraceStatsBean tracebean){
		req.getTraceStatsList().add(tracebean);
	}
	public void addSQLBean(SQLStatsBean sqlbean){
		req.getSqlStatsList().add(sqlbean);
	}

	
}
