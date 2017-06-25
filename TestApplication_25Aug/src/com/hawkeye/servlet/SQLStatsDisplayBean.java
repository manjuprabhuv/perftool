package com.hawkeye.servlet;

import java.sql.Timestamp;

public class SQLStatsDisplayBean {

	private String sqlString;
	private long avergeExecTime;
	private long count;
	private long failedCount;
	private long worstExecTime;
	private Timestamp timestamp;
	private Timestamp worstExecTimeStamp;

	public long getWorstExecTime() {
		return worstExecTime;
	}

	public void setWorstExecTime(long worstExecTime) {
		this.worstExecTime = worstExecTime;
	}

	public Timestamp getWorstExecTimeStamp() {
		return worstExecTimeStamp;
	}

	public void setWorstExecTimeStamp(Timestamp worstExecTimeStamp) {
		this.worstExecTimeStamp = worstExecTimeStamp;
	}

	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

	public long getAvergeExecTime() {
		return avergeExecTime;
	}

	public void setAvergeExecTime(long avergeExecTime) {
		this.avergeExecTime = avergeExecTime;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(long failedCount) {
		this.failedCount = failedCount;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
