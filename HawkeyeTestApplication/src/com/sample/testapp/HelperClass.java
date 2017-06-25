package com.sample.testapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sample.package2.HelperClass2;

public class HelperClass {
	
	public SampleObject doCallMethod(String arg1,int args2){
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("key", 100);
		List<String> list= new ArrayList<String>();
		list.add("str1");
		SampleObject object = new SampleObject();
		object.setIntegerVal(225);
		object.setList(list);
		object.setMap(map);
		object.setStrvalue("StringValue");
		doCallMethod2(object);
		HelperClass2 helper = new HelperClass2();
		 helper.anotherMethod("demo");
		
		
		 
		 
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
		Thread.sleep(3000);
	}
	
	

}
