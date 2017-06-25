package com.hawkeye.statsThread;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.hawkeye.agent.aggregator.StatisticsAggregatorAndForwarder;
import com.hawkeye.beans.HawkEyeMemoryBean;
import com.hawkeye.beans.JSONVitalStatsRequest;
import com.hawkeye.utils.ConfigReader;

public class VitalStatsThread extends Thread {

	public void run() {
		MemoryMXBean mx = ManagementFactory.getMemoryMXBean();
		List<HawkEyeMemoryBean> muHeapList = new ArrayList<HawkEyeMemoryBean>();
		List<HawkEyeMemoryBean> muNonHeapList = new ArrayList<HawkEyeMemoryBean>();
		List<Timestamp> timeStampList = new ArrayList<Timestamp>();
		JSONVitalStatsRequest stats = new JSONVitalStatsRequest();
		int count = 0;
		try {

			
			do {
				Timestamp ts = new Timestamp(new Date().getTime());
				MemoryUsage muHeap = mx.getHeapMemoryUsage();				
				HawkEyeMemoryBean muhHeap = new HawkEyeMemoryBean();
				muhHeap.setComitted(muHeap.getCommitted());
				muhHeap.setInit(muHeap.getInit());
				muhHeap.setMax(muHeap.getMax());
				muhHeap.setTs(ts);
				muhHeap.setUsed(muHeap.getUsed());
				muHeapList.add(muhHeap);
				
				MemoryUsage muNonHeap = mx.getNonHeapMemoryUsage();
				HawkEyeMemoryBean muhNonHeap = new HawkEyeMemoryBean();
				muhNonHeap.setComitted(muNonHeap.getCommitted());
				muhNonHeap.setInit(muNonHeap.getInit());
				muhNonHeap.setMax(muNonHeap.getMax());
				muhNonHeap.setTs(ts);
				muhNonHeap.setUsed(muNonHeap.getUsed());				
				muNonHeapList.add(muhNonHeap);

				count++;

				if (count == 6) {
					stats.addToMap("HeapMemory", muHeapList);
					stats.addToMap("NonHeapMemory", muNonHeapList);						
					StatisticsAggregatorAndForwarder.flushStats(stats);
					muHeapList.clear();
					muNonHeapList.clear();
					count=0;
				} else {
					Thread.sleep(10000);
				}
			} while (true);

		} catch (InterruptedException e) {
			System.out.println("Stats Thread interrupted.");
		}

	}

}
