package com.spring.client;

import org.osoa.sca.annotations.Remotable;


@Remotable
public interface SpringClient {
	
	public void clientDisplay(String username,String password);
}
