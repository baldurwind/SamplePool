package com.chamago.pcrm.behavior.web;

import static org.junit.Assert.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.api.domain.Item;
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})  
public class BehaviorControllerTest {

	@Autowired
	private  BehaviorController behaviorController;
	
	private   MockHttpServletRequest request=new MockHttpServletRequest();
	
	 
	
	@Test
	public void testFindPageView() {
	}

	@Test
	public void testFindKeyword() {
	}

	@Test
	public void testFindVisitingHistory() {
		String seller="stevemadden旗舰店";
		String buyer="cc小嘎";
		behaviorController.findVisitingHistory(request, seller, buyer);
		PagedListHolder<Object[]>  resultList=(PagedListHolder<Object[]> )request.getSession().getAttribute("visitinghistory");
		Assert.assertTrue(7==resultList.getPageList().size());
	}

	@Test
	public void testFindBoughtItem() {
		String seller="stevemadden旗舰店";
		String buyer="英伦玫瑰01";
		behaviorController.findBoughtItem(request, seller, buyer);
		PagedListHolder<Object[]>  resultList=(PagedListHolder<Object[]> )request.getSession().getAttribute("boughtitem");
		Assert.assertTrue(3==resultList.getPageList().size());
	}

	@Test
	public void testFindConsultationHistory() {
		String seller="stevemadden旗舰店";
		String buyer="wccctest01";
	//	behaviorController.findConsultationHistory(request,seller,buyer);
		
		List<Object[]> mixEntryList=(List<Object[]>)request.getAttribute("mixEntryList") ;
		Assert.assertTrue(15333008871L==Long.valueOf((String)mixEntryList.get(0)[0]));
		Assert.assertTrue(3==mixEntryList.size());
	}

	@Test
	public void testFindItemRelation() {
	}

}
