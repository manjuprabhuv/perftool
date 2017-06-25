package com.hawkeye.beans;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JSONVitalStatsRequest {
	

	private Map<String, List<HawkEyeMemoryBean>> memoryMap = new HashMap<String, List<HawkEyeMemoryBean>>();

	public Map<String, List<HawkEyeMemoryBean>> getMemoryMap() {
		return memoryMap;
	}

	public void setMemoryMap(Map<String, List<HawkEyeMemoryBean>> memoryMap) {
		this.memoryMap = memoryMap;
	}

	public void addToMap(String str, List<HawkEyeMemoryBean> list) {
		memoryMap.put(str, list);
	}

}
