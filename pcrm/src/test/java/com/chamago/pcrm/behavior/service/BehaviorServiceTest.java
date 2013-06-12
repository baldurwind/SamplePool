package com.chamago.pcrm.behavior.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.chamago.pcrm.behavior.mapper.BehaviorMapper;
import com.chamago.pcrm.behavior.mapper.impl.BehaviorMapperImpl;


 @ContextConfiguration(locations = {  "classpath*:ac.xml"}) 
public class BehaviorServiceTest extends AbstractJUnit4SpringContextTests  {

	 @Autowired
	 private BehaviorService behaviorService ;
	
	 
	 private static BehaviorMapper behaviorMapper;
	 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		behaviorMapper=EasyMock.createMock(BehaviorMapperImpl.class);
	}


	@Test
	public void testFindKeyword() {
		List<Object[]> result=new  ArrayList<Object[]>();
		result.add(new Object[]{1,1939});
		
		EasyMock.expect(behaviorMapper.findKeyword("富安娜官方旗舰店", "湖边小鹿88")).andReturn(result);
		EasyMock.replay(behaviorMapper);
		behaviorService.setBehaviorMapper(behaviorMapper);
	
		List<Object[]>	 list=behaviorService.findKeyword("富安娜官方旗舰店", "湖边小鹿88");
		Assert.assertEquals("Test:",1939,list.get(0)[1]);
		EasyMock.verify(behaviorMapper); 
	}

	@Test
	public void testFindPageView() {
	}

	@Test
	public void testFindVisitingHistory() {
	}

	@Test
	public void testFindBoughtItem() {
	}

	@Test
	public void testFindConsultationHistory() {
	}

}
