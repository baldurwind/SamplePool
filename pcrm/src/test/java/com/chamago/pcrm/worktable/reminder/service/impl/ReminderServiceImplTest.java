package com.chamago.pcrm.worktable.reminder.service.impl;

import static org.junit.Assert.fail;

import java.util.Date;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.chamago.pcrm.worktable.reminder.mapper.ReminderMapper;
import com.chamago.pcrm.worktable.reminder.mapper.impl.ReminderMapperImpl;
import com.chamago.pcrm.worktable.reminder.pojo.Reminder;
import com.chamago.pcrm.worktable.reminder.service.ReminderService;

@ContextConfiguration(locations = {  "classpath*:ac.xml"}) 
public class ReminderServiceImplTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ReminderService reminderService;
	
	private static ReminderMapper reminderMapper;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reminderMapper = EasyMock.createMock(ReminderMapperImpl.class);
	}
	
	@Test
	public void testSaveReminder() {
		try {
			getReminderMapper().saveReminder(EasyMock.anyObject(Reminder.class));
		} catch(Exception e) {
			fail("saveReminder error " + e.getMessage());
		}
		
		EasyMock.expectLastCall(); 
		EasyMock.replay(getReminderMapper());
		
		getReminderService().setReminderMapper(getReminderMapper());
		
		Reminder reminder = new Reminder();
		reminder.setTipType(0);
		reminder.setTipTime(new Date());
		reminder.setNick("良无限_非洲菊");
		reminder.setBuyerNick("wanghe_taobao");
		reminder.setSellerNick("stevemadden旗舰店");
		reminder.setContent("客服备忘测试");
		
		try {
			getReminderService().saveReminder(reminder);
		} catch(Exception e) {
			fail("saveReminder error " + e.getMessage());
		}
		
		EasyMock.verify(getReminderMapper()); 
	}
	
	

	public ReminderService getReminderService() {
		return reminderService;
	}

	public void setReminderService(ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	public ReminderMapper getReminderMapper() {
		return reminderMapper;
	}

	public void setReminderMapper(ReminderMapper reminderMapper) {
		this.reminderMapper = reminderMapper;
	}
}
