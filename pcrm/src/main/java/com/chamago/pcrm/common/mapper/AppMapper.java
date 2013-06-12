package com.chamago.pcrm.common.mapper;

import java.util.List;


import com.chamago.pcrm.common.pojo.App;
import com.chamago.pcrm.common.pojo.Subscriber;



public interface AppMapper {
	
	
	List<Subscriber> findSubscriber(String nick,String appKey);
	
	boolean ifExists(String sellernick,String appkey);
	
	int insertSubscriber(Subscriber pojo);
	
	int insertApp(App pojo);
	
	List<App> findAllApp();
	
	int updateSessionKey(Subscriber pojo);
	
	int updateInitSubscriber(String appkey,String sellernick,Integer isInit);
	
	
	int updateSession(String session,String nick,String appkey);
	
//	List<InitShell> findAllInitShell();

//	List<BatchInitShellSchedule> batchFindInitShellSchedule(String appkey);
	
//	int LockInitShellSchedule(String appkey);
}