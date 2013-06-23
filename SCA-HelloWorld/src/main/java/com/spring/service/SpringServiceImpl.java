package com.spring.service;

import org.osoa.sca.annotations.Service;


@Service(SpringServiceImpl.class)
public class SpringServiceImpl implements SpringService {

	@Override
	public void serviceDisplay(String username,String password) {
		System.out.println("Spring Service Display");
	}

}
