package com.chamago.pcrm.behavior.mapper;

import java.util.List;

public interface BehaviorMapper {
	
	public List<Object[]> findKeyword(String seller, String buyer);
	
	public Integer findPageView(String seller, String buyer);
	
	public List<Object[]> findVisitingHistory(String seller,String buyer);
	
	public List<Object[]> findBoughtItem(String seller,String buyer );
	
	public List<Object[]> findItemRelation( Long numiid ,String seller );
	
	public List<Object[]> findConsultationHistory(String seller,String buyer);
	
	public Long findLastConsultationItem(String seller,String buyer);
	
	public int insertConsultation(String seller,String buyer,String nick,Long numiid);
	
	public void test(String seller);
}
