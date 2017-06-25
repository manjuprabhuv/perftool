package com.sample.testapp;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
public class WebserviceClient {
	public static void main(String[] args) throws Exception {
		 
		URL url = new URL("http://localhost:8080/TestApplication/testWS?wsdl");
	 
	        //1st argument service URI, refer to wsdl document above
		//2nd argument is service name, refer to wsdl document above
	        QName qname = new QName("http://testapp.sample.com/", "WebServiceTestService");
	 
	        Service service = Service.create(url, qname);
	 
	        IWebservice hello = service.getPort(IWebservice.class);
	 
	        System.out.println(hello.getWS("test"));
	 
	    }
}
