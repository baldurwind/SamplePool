package com.simple.client;

import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;

import com.simple.service.TBService;


public class ClientLauncher {

		 public static void main(String[] args) throws Exception {

			   long timeout = -1L;
		        if (args.length > 0) {
		            timeout = Long.parseLong(args[0]);
		        }

		        SCANodeFactory factory = SCANodeFactory.newInstance();
		        SCANode node = factory.createSCANodeFromClassLoader("simple_client.composite", TBClient.class.getClassLoader());
		        node.start();
		        
		        // Method 1: To access the Spring Application Context instance
		        
		        if (timeout < 0) {
		            System.out.println("Press Enter to Exit...");
		            System.in.read();
		        } else {
		            Thread.sleep(timeout);
		        }

		        node.stop();
		        System.out.println("Bye");
		    }
}
