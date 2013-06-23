package com.simple.service;

import org.osoa.sca.annotations.Destroy;
import org.osoa.sca.annotations.Init;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;


@Service(TBService.class)
public class TBServiceImpl implements TBService{

	
	private String zipCode;
	/* @Constructor
		public TBServiceImpl(@Reference(name = "zipCode", required = true) String zipCode) {
		        this.zipCode = zipCode;
		}*/
	
	
	@Init
	public void init(){
		System.out.println("init..");
	}
	
	@Override
	public String display(String name) {
		System.out.println("ZipCode:"+zipCode+",Service:"+name);
		return name;
	}
	
	@Destroy
	public void destory(){
		System.out.println("destory..");
	}

}
