package com.sample.testapp;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
public class WebserviceClient {
	public static void TestWebserive() throws Exception {
		try{
			com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump = true;
			com.sun.xml.ws.transport.http.HttpAdapter.dump=true;
		}catch(Exception e){
			
		}
		 
		URL url = new URL("http://localhost:8080/HawkeyeTestApplication/testWS?wsdl");
	 
	        //1st argument service URI, refer to wsdl document above
		//2nd argument is service name, refer to wsdl document above
	        QName qname = new QName("http://testapp.sample.com/", "WebServiceTestService");
	 
	        Service service = Service.create(url, qname);
	 
	        IWebservice hello = service.getPort(IWebservice.class);
	 
	        System.out.println(hello.getWS("test"));
	 
	    }
}
