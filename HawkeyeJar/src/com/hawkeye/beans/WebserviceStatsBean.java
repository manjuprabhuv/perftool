package com.hawkeye.beans;

import java.sql.Timestamp;

public class WebserviceStatsBean {
	
	private String request;
	private String response;
	private long requestId =(long)( Math.random() * 10000000);
	private Timestamp timestamp;
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
