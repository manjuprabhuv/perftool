package com.hawkeye.aspects;

public class Monitor {
	private ThreadLocal statisticsCollector = new ThreadLocal() {
		protected synchronized Object initialValue() {

			return new StatisticsCollector();
		}
	};

	StatisticsCollector getStatisticsCollectorOfThisThread() {
		
		return (StatisticsCollector) statisticsCollector.get();
	}
	
}
