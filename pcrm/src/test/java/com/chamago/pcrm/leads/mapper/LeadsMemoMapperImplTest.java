package com.chamago.pcrm.leads.mapper;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.leads.memo.mapper.LeadsMemoMapper;
import com.chamago.pcrm.leads.memo.pojo.LeadsMemo;
import com.chamago.pcrm.leads.memo.pojo.LeadsMemoReminderLKP;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})
@Transactional
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback = true)
public class LeadsMemoMapperImplTest {

	@Autowired
	private LeadsMemoMapper leadsMemoMapper;
	
	@Test
	@Rollback(true)
	public void testSaveLeadsMemo() {
		boolean falg = true;
		LeadsMemo leadsMemo = new LeadsMemo();
		leadsMemo.setBuyerNick("1s");
		leadsMemo.setSellerNick("芳蕾玫瑰粉粉旗舰店");
		leadsMemo.setWangwangNick("良无限_非洲菊");
		leadsMemo.setNumId(12447733485l);
		leadsMemo.setSkuId(14798489864l);
		leadsMemo.setPrice(null);
		leadsMemo.setMobile("15821074704");
		leadsMemo.setEmail("wanghe_email@163.com");
		leadsMemo.setType(0);
		leadsMemo.setExpiredDate(new Date());
		leadsMemo.setStatus(0);
		try {
			getLeadsMemoMapper().saveLeadsMemo(leadsMemo);
		} catch(Exception e) {
			falg = false;
			fail("saveLeadsMemo error" + e.getMessage());
		}
		
		if(falg) {
			LeadsMemo leadsMemo2 = getLeadsMemoMapper().getLeadsMemoById(leadsMemo.getId());
			if(leadsMemo2 == null) {
				fail("getLeadsMemoById is null" );
			} else {
				Assert.assertTrue("Test:", "wanghe_taobao".equals(leadsMemo2.getBuyerNick()));
			}
		}
	}

	@Test
	public void testSaveLeadsMemoReminderLKP() {
		LeadsMemoReminderLKP leadsMemoReminderLKP = new LeadsMemoReminderLKP();
		leadsMemoReminderLKP.setLid(ObjectId.get().toString());
		leadsMemoReminderLKP.setRid(ObjectId.get().toString());
		try {
			leadsMemoMapper.saveLeadsMemoReminderLKP(leadsMemoReminderLKP);
		} catch(Exception e) {
			fail("saveLeadsMemoReminderLKP error" + e.getMessage());
		}
	}

	@Test
	public void testUpdateLeadsMemo() {
		LeadsMemo leadsMemo = new LeadsMemo();
		leadsMemo.setId("4f43413ae29337a47ce85e16");
		leadsMemo.setNumId(12447733485l);
		leadsMemo.setSkuId(14798489864l);
		leadsMemo.setMobile("15821074744");
		leadsMemo.setEmail("wanghe_email@163.com");
		leadsMemo.setType(0);
		leadsMemo.setExpiredDate(new Date());
		boolean flag = true;
		try {
			getLeadsMemoMapper().updateLeadsMemo(leadsMemo);
		} catch(Exception e) {
			flag = false;
			fail("updateLeadsMemo error" + e.getMessage());
		}
		
		if(flag) {
			LeadsMemo leadsMemo2 = getLeadsMemoMapper().getLeadsMemoById(leadsMemo.getId());
			if(leadsMemo2 == null) {
				fail("getLeadsMemoById is null" );
			} else {
				Assert.assertTrue("Test:", "15821074744".equals(leadsMemo2.getMobile()));
			}
		}
	}

	@Test
	public void testGetLeadsMemoByParam() {
		//List<LeadsMemo> leadsMemos = getLeadsMemoMapper().getLeadsMemoByParam("wanghe_taobao", "0", "0");
		/*Assert.assertTrue("Test:", 20 == leadsMemos.size());
		Assert.assertTrue("Test:", "wanghe_taobao".equals(leadsMemos.get(0).getBuyerNick()));
		Assert.assertTrue("Test:", "12447733485".equals(leadsMemos.get(0).getNumId().toString()));
		Assert.assertTrue("Test:", "14798489864".equals(leadsMemos.get(0).getSkuId().toString()));*/
	}

	@Test
	public void testUpdateStatus() {
		boolean flag = true;
		try {
			getLeadsMemoMapper().updateStatus("4f43413ae29337a47ce85e16", 1);
		} catch(Exception e) {
			flag = false;
			fail("updateStatus error " + e.getMessage());
		}
		
		if(flag) {
			LeadsMemo leadsMemo = getLeadsMemoMapper().getLeadsMemoById("4f43413ae29337a47ce85e16");
			Assert.assertTrue("Test:", 1 == leadsMemo.getStatus().intValue());
		}
	}

	public LeadsMemoMapper getLeadsMemoMapper() {
		return leadsMemoMapper;
	}

	public void setLeadsMemoMapper(LeadsMemoMapper leadsMemoMapper) {
		this.leadsMemoMapper = leadsMemoMapper;
	}
}