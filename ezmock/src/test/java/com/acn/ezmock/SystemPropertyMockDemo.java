package com.acn.ezmock;



import java.io.IOException;     

public class SystemPropertyMockDemo {     
   
	public String getSystemProperty() throws IOException {   
		System.out.println("getProperty");
        return System.getProperty("property");     
    }     

} 

