package com.simple.service;

import org.osoa.sca.annotations.Remotable;

@Remotable
public interface TBService {

	public String display(String name);
	
}
