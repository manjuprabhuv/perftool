package com.hawkeye.beans;

import java.util.ArrayList;
import java.util.List;

public class TraceStatsBean {

	private String methodName;
	private long execTime;
	private String action;
	private String inputParams;
	private String returnVals;
	private long requestId;
	private List<TraceStatsBean> children = new ArrayList<TraceStatsBean>();

	public void addToChildren(TraceStatsBean bean) {
		children.add(bean);
	}

	public List<TraceStatsBean> getChildren() {
		return children;
	}

	public void setChildren(List<TraceStatsBean> children) {
		this.children = children;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

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
