package com.chamago.pcrm.common.service;

import java.util.List;

import com.chamago.pcrm.common.mapper.AppMapper;
import com.chamago.pcrm.common.pojo.App;

public interface  AppService {

	
	
	
	public Integer insertApp(App pojo);
	
	public List<App>  findAllApp();
	
	public AppMapper getMapper();
}
