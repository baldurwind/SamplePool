package com.chamago.pcrm.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chamago.pcrm.common.mapper.AppMapper;
import com.chamago.pcrm.common.pojo.App;
import com.chamago.pcrm.common.service.AppService;


public class AppServiceImpl implements AppService {

	public List<App> findAllApp() {
		return mapper.findAllApp();
	}
	public  Integer  insertApp(App pojo) {
		return mapper.insertApp(pojo);
	}
	@Autowired
	private AppMapper mapper;
	
	public AppMapper getMapper() {
		return mapper;
	}
	public void setMapper(AppMapper mapper) {
		this.mapper = mapper;
	}
	
	
	

}
