package com.hawkeye.agent.aggregator;

public class Monitor {
	private ThreadLocal statisticsCollector = new ThreadLocal() {
		protected synchronized Object initialValue() {

			return new StatisticsAggregatorAndForwarder();
		}
	};

	StatisticsAggregatorAndForwarder getStatisticsCollectorOfThisThread() {
		
		return (StatisticsAggregatorAndForwarder) statisticsCollector.get();
	}
	
}
