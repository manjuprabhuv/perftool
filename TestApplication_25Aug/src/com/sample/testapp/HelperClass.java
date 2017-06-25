package com.sample.testapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelperClass {
	
	public SampleObject doCallMethod(String arg1,int args2){
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		List<String> list= new ArrayList<String>();
		
		SampleObject object = new SampleObject();
		object.setIntegerVal(225);
		object.setList(list);
		object.setMap(map);
		object.setStrvalue("StringValue");
		doCallMethod2(object);
		return object;
		
	}
	
	public String doCallMethod2(SampleObject object){
		try {
			slowMethod();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "called-1";
	}
	public void slowMethod() throws InterruptedException{
		Thread.sleep(600);
	}

}
