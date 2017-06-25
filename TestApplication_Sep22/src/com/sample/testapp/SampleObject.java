package com.sample.testapp;

import java.util.List;
import java.util.Map;

public class SampleObject {

	String strvalue;
	int integerVal;
	Map<String, Integer> map;
	List<String> list;

	public String getStrvalue() {
		return strvalue;
	}

	public void setStrvalue(String strvalue) {
		this.strvalue = strvalue;
	}

	public int getIntegerVal() {
		return integerVal;
	}

	public void setIntegerVal(int integerVal) {
		this.integerVal = integerVal;
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	
	@Override
	public String toString(){
		String returnString="";
		returnString = "strvalue :"+strvalue+"\n" +"integerVal :"+integerVal+"\n" +"map :"+map+"\n" +"list :"+list+"\n" ;
		return returnString;
	}
}
