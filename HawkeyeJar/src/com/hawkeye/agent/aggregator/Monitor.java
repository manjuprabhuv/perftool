package com.hawkeye.agent.aggregator;

import com.hawkeye.beans.WebserviceStatsBean;

public class Monitor {
	static volatile Monitor monitor = null;
	private static ThreadLocal statisticsCollector = new ThreadLocal() {
		protected synchronized Object initialValue() {

			return new StatisticsAggregator();
		}
	};
	private static ThreadLocal wsStatsCollector = new ThreadLocal() {
		protected synchronized Object initialValue() {

			return new WebserviceStatsBean();
		}
	};

	public static StatisticsAggregator getStatisticsCollectorOfThisThread() {

		return (StatisticsAggregator) statisticsCollector.get();
	}
	public static WebserviceStatsBean getWebserviceStatsThread() {

		return (WebserviceStatsBean) wsStatsCollector.get();
	}

	private Monitor() {

	}

	public static Monitor getMonitor() {
		if (monitor == null) {
			synchronized (Monitor.class) {
				if (monitor == null) {
					monitor = new Monitor();
				}
			}
		}

		return monitor;
	}
}
