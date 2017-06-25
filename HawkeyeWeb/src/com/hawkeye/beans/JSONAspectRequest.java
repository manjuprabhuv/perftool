package com.hawkeye.beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JSONAspectRequest {
	
	public JSONAspectRequest (long requestId) {
		this.requestId = requestId;
	}

	private long requestId;
	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	private List<SQLStatsBean> sqlStatsList = new ArrayList<SQLStatsBean>();
	private List<TraceStatsBean> traceStatsList = new ArrayList<TraceStatsBean>();

	
	public List<SQLStatsBean> getSqlStatsList() {
		return sqlStatsList;
	}

	public void setSqlStatsList(List<SQLStatsBean> sqlStatsList) {
		this.sqlStatsList = sqlStatsList;
	}

	public List<TraceStatsBean> getTraceStatsList() {
		return traceStatsList;
	}

	public void setTraceStatsList(List<TraceStatsBean> traceStatsList) {
		this.traceStatsList = traceStatsList;
	}

}
