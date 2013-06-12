package com.chamago.pcrm.common.mapper.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.chamago.pcrm.common.mapper.AppMapper;
import com.chamago.pcrm.common.pojo.App;
import com.chamago.pcrm.common.pojo.Subscriber;
import com.chamago.pcrm.common.utils.MemCached;


@SuppressWarnings("unchecked")
public class AppMapperImpl extends SqlSessionDaoSupport  implements AppMapper {
	
	@MemCached
	public List<App> findAllApp() {
		return getSqlSession().selectList("AppMapper.findAllApp");
	}
	
	public List<Subscriber> findSubscriber(String nick,String appKey) {
		Map<String,String>  map=new HashMap<String,String> ();
		map.put("nick", nick);
		map.put("appKey", appKey);
		return getSqlSession().selectList("AppMapper.findSubscriber",map);
	}
	public boolean ifExists(String sellernick,String appkey) {
		Map<String,String>   map=new HashMap<String,String> ();
		map.put("sellernick", sellernick);
		map.put("appkey", appkey);
		return  (Boolean)getSqlSession().selectOne("AppMapper.ifExists",map);
	}
	public int replaceSubscriber(Subscriber pojo) {
		return  getSqlSession().insert("AppMapper.replaceTopSession",pojo);
	}
	public int insertApp(App pojo) {
		return  getSqlSession().insert("AppMapper.insertApp",pojo);
	}
	public int updateSessionKey(Subscriber pojo) {
		return  getSqlSession().update("AppMapper.updateSessionKey",pojo);
	}
	public int insertSubscriber(Subscriber pojo){
		return  getSqlSession().insert("AppMapper.insertSubscriber",pojo);
	}
	public int updateInitSubscriber(String appkey,String sellernick,Integer isInit){
		Map<String,Object>  map=new HashMap<String,Object>();
		map.put("appkey", appkey);
		map.put("sellernick", sellernick);
		map.put("isInit", isInit);
		return getSqlSession().update("AppMapper.updateInitSubscriber",map);
	}
	
	public int updateSession(String session,String nick,String appkey){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("appkey", appkey);
		map.put("nick", nick);
		map.put("session", session);
		return getSqlSession().update("AppMapper.updateSession",map);
	}
}
