package com.chamago.pcrm.behavior.service;

import java.util.List;


import com.chamago.pcrm.behavior.mapper.BehaviorMapper;

public interface BehaviorService {

	
	public List<Object[]> findKeyword(String seller,String buyer);
	
	public Integer findPageView(String seller,String buyer);
	
	public List<Object[]> findVisitingHistory(String seller,String buyer);
	
	public List<Object[]> findBoughtItem(String seller,String buyer);
	
	public List<Object[]> findConsultationHistory(String seller,String buyer);
	
	public List<Object[]> findItemRelation( Long num_iid,String seller);
	
	public void setBehaviorMapper(BehaviorMapper behaviorMapper);
	
	public Long findLastConsultationItem(String seller,String buyer);
	
	public int insertConsultation(String seller,String buyer,String nick,Long numiid);
}
