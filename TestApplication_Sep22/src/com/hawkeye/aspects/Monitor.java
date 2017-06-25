package com.hawkeye.aspects;

import com.hawkeye.agent.aggregator.StatisticsAggregator;

public class Monitor {
	static volatile Monitor monitor = null;
	private ThreadLocal statisticsCollector = new ThreadLocal() {
		protected synchronized Object initialValue() {

			return new StatisticsAggregator();
		}
	};

	StatisticsAggregator getStatisticsCollectorOfThisThread() {

		return (StatisticsAggregator) statisticsCollector.get();
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
