package com.sample.testapp;



import javax.jws.WebService;
 
@WebService(endpointInterface = "com.sample.testapp.IWebservice")
 
public class WebServiceTest implements IWebservice{

		@Override
		public String getWS(String name) {
			
					System.out.println("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump  >>"+com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump);
					System.out.println("com.sun.xml.ws.transport.http.HttpAdapter.dump  >>"+com.sun.xml.ws.transport.http.HttpAdapter.dump);
					
			return "TEST WebService " + name;
		}
	 


}
