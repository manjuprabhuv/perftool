package com.hawkeye.servlet;

import java.sql.Timestamp;

public class TraceStatsDisplayBean {

	private String methodName;
	private long execTime;
	private String action;
	private String inputParams;
	private String returnVals;
	
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public long getExecTime() {
		return execTime;
	}
	public void setExecTime(long execTime) {
		this.execTime = execTime;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getInputParams() {
		return inputParams;
	}
	public void setInputParams(String inputParams) {
		this.inputParams = inputParams;
	}
	public String getReturnVals() {
		return returnVals;
	}
	public void setReturnVals(String returnVals) {
		this.returnVals = returnVals;
	}
	
	

}
