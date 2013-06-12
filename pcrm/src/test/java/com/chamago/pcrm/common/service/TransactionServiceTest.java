package com.chamago.pcrm.common.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chamago.pcrm.leads.memo.mapper.LeadsMemoMapper;
import com.chamago.pcrm.leads.memo.pojo.LeadsMemo;

//@TransactionConfiguration(defaultRollback = true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})
public class TransactionServiceTest {

	@Autowired
	private LeadsMemoMapper leadsMemoMapper;
	
	
	@Test
	public void test(){
		LeadsMemo leadsMemo=new LeadsMemo();
		leadsMemo.setAddReminder(true);
		leadsMemo.setBuyerNick("test");
		leadsMemo.setCreateDate(new Date());
		leadsMemo.setId("iamtestid");
		leadsMemo.setNumId(44374387438743L);
		
		leadsMemo.setSellerNick("iamsellernick");
		leadsMemo.setWangwangNick("iamwangwang");
		leadsMemo.setSkuId(3434434344343L);
		leadsMemo.setPrice(new BigDecimal(32));
		leadsMemo.setMobile("23232323");
		leadsMemo.setEmail("ssdssd");
		leadsMemo.setType(0);
		leadsMemo.setExpiredDate(new Date());
		leadsMemo.setStatus(1);
		
		try {
			leadsMemoMapper.saveLeadsMemo(leadsMemo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
