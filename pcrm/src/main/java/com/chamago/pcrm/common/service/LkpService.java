package com.chamago.pcrm.common.service;

import java.util.Map;

import sy.hbm.Syresource;

import com.chamago.pcrm.common.pojo.App;
import com.chamago.pcrm.common.pojo.InitShell;
import com.chamago.pcrm.common.pojo.Subscriber;

public interface LkpService {

	
	public void init();
	public void initGender();
	public void initProvince();
	public void initCity();
	
	public Map<Integer, String> getGender();
	public Map<Integer, String> getProvince();
	public Map<Integer, String> getCity();
	public Map<String, String> getItemstatus();
	public Map<String, Subscriber> getSubscribers() ;
	public Map<String, App> getApps() ;
	public Map<String,String> getPromotions();
	public Map<String,String> getErrorcodes();
	public Map<Integer, InitShell> getInitshells();
	public Boolean saveOrUpdateSession(Long userid,String nick,String appkey,String session);
	public void updateInitSubscriber(String appkey,String sellernick,Integer is_init);
	public Subscriber findSubscriberByUserId(Long userid);
	public Syresource findSyresource(String fullPath);
}
