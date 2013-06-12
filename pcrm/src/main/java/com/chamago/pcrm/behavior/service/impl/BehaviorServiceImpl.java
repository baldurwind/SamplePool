package com.chamago.pcrm.behavior.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.behavior.mapper.BehaviorMapper;
import com.chamago.pcrm.behavior.service.BehaviorService;

@Service
public class BehaviorServiceImpl  implements BehaviorService {
	
	@Autowired
	private BehaviorMapper behaviorMapper;
	
	
	public List<Object[]> findKeyword(String seller, String buyer){
		return behaviorMapper.findKeyword(seller, buyer);
	}

	public Integer findPageView(String seller, String buyer){
		return behaviorMapper.findPageView(seller, buyer);
	}

	public List<Object[]> findVisitingHistory(String seller, String buyer) {
		return behaviorMapper.findVisitingHistory(seller, buyer);
	}

	public List<Object[]> findBoughtItem(String seller, String buyer) {
		return  behaviorMapper.findBoughtItem(seller, buyer);
	}
	
	public List<Object[]> findItemRelation( Long numiid,String seller) {
		
		return behaviorMapper.findItemRelation( numiid,seller);
	}
	public List<Object[]> findConsultationHistory(String seller, String buyer) {
		return behaviorMapper.findConsultationHistory(seller, buyer);
	}

	public void setBehaviorMapper(BehaviorMapper behaviorMapper){
		this.behaviorMapper=behaviorMapper;
	}
	public Long findLastConsultationItem(String seller,String buyer){
		return behaviorMapper.findLastConsultationItem(seller, buyer);
	}
	
	public int insertConsultation(String seller,String buyer,String nick,Long numiid){
		Long last_numiid = behaviorMapper.findLastConsultationItem(seller, buyer);
		if (numiid!=null&&(last_numiid==null||last_numiid.longValue()!=numiid.longValue())){
			return behaviorMapper.insertConsultation(seller, buyer, nick, numiid);
		}
		return 0;
	}
}
