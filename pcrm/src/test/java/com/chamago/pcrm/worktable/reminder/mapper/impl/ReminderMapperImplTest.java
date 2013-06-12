package com.chamago.pcrm.worktable.reminder.mapper.impl;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.worktable.reminder.mapper.ReminderMapper;
import com.chamago.pcrm.worktable.reminder.pojo.Reminder;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})
public class ReminderMapperImplTest {

	@Autowired
	private ReminderMapper reminderMapper;
	
	@Test
	public void testSaveReminder() {
		boolean flag = true;
		Reminder reminder = new Reminder();
		reminder.setTipType(0);
		reminder.setTipTime(new Date());
		reminder.setNick("良无限_非洲菊");
		reminder.setBuyerNick("wanghe_taobao");
		reminder.setSellerNick("stevemadden旗舰店");
		reminder.setContent("客服备忘测试");
		try {
			getReminderMapper().saveReminder(reminder);
		} catch(Exception e) {
			flag = false;
			fail("saveReminder error " + e.getMessage());
		}
		
		if(flag) {
			Reminder reminder2 = getReminderMapper().getReminderById(reminder.getId());
			if(reminder2 == null) {
				fail("getReminderById is null ");
			} else {
				Assert.assertTrue("Test:", reminder2.getNick().equals(reminder.getNick()));
				Assert.assertTrue("Test:", reminder2.getContent().equals(reminder.getContent()));
			}
		}
	}
	
	@Test
	public void testGetReminderSuccessCountByNick() {
		Integer count = getReminderMapper().getReminderSuccessCountByNick("wanghe_taobao");
		Assert.assertTrue("Test:", 2 == count.intValue());
	}

	@Test
	public void testGetReminderOverdueCountByNick() {
		Integer count = getReminderMapper().getReminderOverdueCountByNick("wanghe_taobao");
		Assert.assertTrue("Test:", 3 == count.intValue());
	}

	@Test
	public void testGetReminderNoBeginCountByNick() {
		Integer count = getReminderMapper().getReminderNoBeginCountByNick("wanghe_taobao");
		Assert.assertTrue("Test:", 7 == count.intValue());
	}

//	@Test
//	public void testQueryReminderList() {
//		List<Reminder> reminders = getReminderMapper().queryReminderList("wanghe_taobao");
//		Assert.assertTrue("Test:", 16 == reminders.size());
//		Assert.assertTrue("Test:", "继续提醒xxx".equals(reminders.get(0).getContent()));
//	}

	@Test
	public void testUpdateReminderStatus() {
		boolean flag = true;
		try {
			getReminderMapper().updateReminderStatus("4f420ca6681341c326fccb7c", 2);
		} catch(Exception e) {
			flag = false;
			fail("updateReminderStatus error " + e.getMessage());
		}
		
		if(flag) {
			Reminder reminder = getReminderMapper().getReminderById("4f420ca6681341c326fccb7c");
			if(reminder != null) {
				Assert.assertTrue("Test:", "4f420ca6681341c326fccb7c".equals(reminder.getId()));
				Assert.assertTrue("Test:", 2 == reminder.getStatus().intValue());
			} else {
				fail("getReminderById is null ");
			}
		}
	}

	@Test
	public void testUpdateReminder() {
		Reminder reminder = new Reminder();
		reminder.setId("4f420ca6681341c326fccb7c");
		reminder.setTipType(0);
		reminder.setTipTime(new Date());
		reminder.setBuyerNick("wanghe_taobao_t");
		reminder.setContent("content");
		boolean flag = true;
		try {
			getReminderMapper().updateReminder(reminder);
		} catch(Exception e) {
			flag = false;
			fail("updateReminder error " + e.getMessage());
		}
		
		if(flag) {
			Reminder reminder2 = getReminderMapper().getReminderById(reminder.getId());
			if(reminder2 == null) {
				fail("getReminderById is null ");
			} else {
				Assert.assertTrue("Test:", "wanghe_taobao_t".equals(reminder2.getBuyerNick()));
				Assert.assertTrue("Test:", "content".equals(reminder2.getContent()));
			}
		}
	}

	@Test
	public void testUpdateReminderByLeadsMemo() {
		Reminder reminder = new Reminder();
		reminder.setId("4f420ca6681341c326fccb7c");
		reminder.setTipTime(new Date());
		reminder.setContent("content2");
		reminder.setStatus(0);
		
		boolean flag = true;
		try {
			getReminderMapper().updateReminderByLeadsMemo(reminder);
		} catch(Exception e) {
			flag = false;
			fail("updateReminderByLeadsMemo error " + e.getMessage());
		}
		
		if(flag) {
			Reminder reminder2 = getReminderMapper().getReminderById("4f420ca6681341c326fccb7c");
			if(reminder2 == null) {
				fail("getReminderById is null ");
			} else {
				Assert.assertTrue("Test:", "content2".equals(reminder2.getContent()));
			}
		}
	}

	public ReminderMapper getReminderMapper() {
		return reminderMapper;
	}

	public void setReminderMapper(ReminderMapper reminderMapper) {
		this.reminderMapper = reminderMapper;
	}
}