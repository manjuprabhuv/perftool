package com.hawkeye.beans;

import java.sql.Timestamp;

public class HawkEyeMemoryBean {
	private long max;
	private long used;
	private long comitted;
	private long init;
	private Timestamp ts;

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public long getUsed() {
		return used;
	}

	public void setUsed(long used) {
		this.used = used;
	}

	public long getComitted() {
		return comitted;
	}

	public void setComitted(long comitted) {
		this.comitted = comitted;
	}

	public long getInit() {
		return init;
	}

	public void setInit(long init) {
		this.init = init;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

}
