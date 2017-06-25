package com.hawkeye.agent.aggregator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;



public class NotificationTimer {
	Timer timer;
	static String appURL;
	static int timeIntervel = 10;
	static long checkedCount=0;

	public NotificationTimer(String appURL) {
		NotificationTimer.appURL = appURL;
		timer = new Timer();
		timer.schedule(new RemindTask(), 0, // initial delay
				timeIntervel * 1000); // subsequent rate
	}
	
	class RemindTask extends TimerTask {
	  

	    public void run() {
	    	//check connection
	    	StatisticsAggregatorAndForwarder.isServerUp= checkConnection();
	      if (StatisticsAggregatorAndForwarder.isServerUp) {
	     this.cancel();
	       
	      } else {
	      
	    	  StatisticsAggregatorAndForwarder.isServerUp = false;
	    	  NotificationTimer.timeIntervel = NotificationTimer.timeIntervel *2;
	      }
	    }
	  }
	
	public  static boolean checkConnection(){
		checkedCount++;
		URL url;
		try {
			url = new URL(NotificationTimer.appURL);
			 HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			  httpCon.setDoOutput(true);
			  httpCon.setRequestMethod("POST");
			  System.out.println(httpCon.getResponseCode());
			  System.out.println(httpCon.getResponseMessage());
				BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                        		httpCon.getInputStream()));
				
				String response;
				String message="";
			    while ((response = in.readLine()) != null) 
			    	message = message +response;
			    in.close();
			    if("HAWKEYE-OK".equalsIgnoreCase(message)){
			    	return true;
			    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		return false;
		 
	}


}
