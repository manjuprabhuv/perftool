package com.hawkeye.beans;

import java.sql.Timestamp;

public class SQLStatsBean{

	private String sqlStatment;
	private long execTime;
	private String failedFlag;
	private Timestamp timestamp;
	private long requestId;
	
	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public String getSqlStatment() {
		return sqlStatment;
	}

	public void setSqlStatment(String sqlStatment) {
		this.sqlStatment = sqlStatment;
	}

	public long getExecTime() {
		return execTime;
	}

	public void setExecTime(long execTime) {
		this.execTime = execTime;
	}

	public String getFailedFlag() {
		return failedFlag;
	}

	public void setFailedFlag(String failedFlag) {
		this.failedFlag = failedFlag;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
