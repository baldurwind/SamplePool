package com.chamago.pcrm.behavior.mapper.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.context.ApplicationContext;

import com.chamago.pcrm.behavior.mapper.BehaviorMapper;
import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.MemCached;
import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.Utils;
public class BehaviorMapperImpl  extends SqlSessionDaoSupport implements BehaviorMapper {

	@AopLogModule(name="item",layer="dao")
	public void test(String seller){
		System.out.println(1);
	}
	
	@MemCached
	@SuppressWarnings("unchecked")
	public List<Object[]> findKeyword(String seller, String buyer) {
		Map<String,String>  map=new HashMap<String,String> ();
			map.put("seller", seller);
			map.put("buyer", buyer);
		return  getSqlSession().selectList("BehaviorMapper.findKeyword",map);
	}
	@MemCached
	public  Integer  findPageView(String seller, String buyer) {
		Map<String,String>  map=new HashMap<String,String> ();
			map.put("seller", seller);
			map.put("buyer", buyer);
		return (Integer)getSqlSession().selectOne("BehaviorMapper.findPageView",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findVisitingHistory(String seller, String buyer) {
		Map<String,String>  map=new HashMap<String,String> ();
			map.put("seller", seller);
			map.put("buyer", buyer);
		return getSqlSession().selectList("BehaviorMapper.findVisitingHistory",map);
	}
	@MemCached
	@SuppressWarnings("unchecked")
	public List<Object[]> findBoughtItem(String seller, String buyer    ) {
		Map<String,String>  map=new HashMap<String,String> ();
			map.put("seller", seller);
			map.put("buyer", buyer);
		return getSqlSession().selectList("BehaviorMapper.findBoughtItem",map);
	}
	//@MemCached
	@SuppressWarnings("unchecked")
	public List<Object[]> findItemRelation( Long numiid ,String seller) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("numiid", numiid);
		map.put("seller",seller);
		return getSqlSession().selectList("BehaviorMapper.findItemRelation",map);
	}
	
	public static void main(String ags[]){
		ApplicationContext ac=Utils.getClassPathXMlApplication();
		BehaviorMapper be=ac.getBean(BehaviorMapper.class);
	 	System.out.println("sdsdsdsdsd"+be.findItemRelation(null, "良无限home"));
	}
	//@MemCached
	@SuppressWarnings("unchecked")
	public List<Object[]> findConsultationHistory(String seller, String buyer) {
		Map<String,String>  map=new HashMap<String,String> ();
			map.put("seller", seller);
			map.put("buyer", buyer);
		return getSqlSession().selectList("BehaviorMapper.findConsultationHistory",map);
	}
	
	public Long  findLastConsultationItem(String seller,String buyer){
		Map<String,String>  map=new HashMap<String,String> ();
			map.put("seller", seller);
			map.put("buyer", buyer);
		return (Long)getSqlSession().selectOne("BehaviorMapper.findLastConsultationItem",map);
	}
	public int insertConsultation(String seller,String buyer,String nick,Long numiid){
		Map<String,String>  map=new HashMap<String,String> ();
		map.put("id", ObjectId.get().toString());
		map.put("seller", seller);
		map.put("buyer", buyer);
		map.put("wangwang", nick);
		map.put("numiid", ""+numiid);
	return  getSqlSession().insert("BehaviorMapper.insertConsultation",map);
	}
}
