package com.spring.client;

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import com.spring.service.SpringService;


@Service(SpringClientImpl.class)
public class SpringClientImpl implements SpringClient {

	
	private SpringService springService;
	
	@Reference
	public void setSpringService(SpringService springService) {
		this.springService = springService;
	}


	@Override
	public void clientDisplay(String username,String password) {
		System.out.println("Spring Client Display");
		springService.serviceDisplay(username, password);
	}

}
