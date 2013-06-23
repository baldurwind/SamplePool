package com.spring.service;

import org.osoa.sca.annotations.Remotable;


@Remotable
public interface SpringService {
	
	public void serviceDisplay(String username,String password);
}
