package com.chamago.pcrm.crm.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.taobao.top.service.TopService;
import com.chamago.pcrm.trade.service.TradeService;
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {  "classpath*:ac.xml"}) 
public class CrmControllerTest {
   
	@Autowired
	private CrmController crmController;
	
	private static MockHttpServletRequest  request;
	
	private static TradeService tradeService;
	
	private static TopService topService;
	
	 
	public static void main(String ags[]){
		MockHttpServletRequest request = new MockHttpServletRequest();
	}
	@BeforeClass
	public  static  void setUpBeforeClass() throws Exception {
		request = new MockHttpServletRequest();
		topService=EasyMock.createMock(TopService.class);
		tradeService=EasyMock.createMock(TradeService.class);
	}
	
	@Test
	public void testFindCrmTradeSummary() {
		String seller="stevemadden旗舰店";
		String buyer="wccctest01";
		
		
		
		List<Long> waitingSendGoodsTradeList =new  ArrayList<Long>();
			waitingSendGoodsTradeList.add(99999999999999L);
		EasyMock.expect(tradeService.findWaitingSendGoodsTradeId(seller,buyer)).andReturn(waitingSendGoodsTradeList) ;
		
		List<Long> refundOidList=new ArrayList<Long>();
			refundOidList.add(99999999999999L);
		EasyMock.expect(tradeService.findRefundNum(seller,buyer)).andReturn(refundOidList);
		
		Assert.assertTrue(1==(Integer)request.getAttribute("waitingSendGoodsTradeList"));
		Assert.assertTrue(1==(Integer)request.getAttribute("refundOidList"));
	//	String page=crmController.findCrmTradeSummary(request, seller,buyer);
		
	//	Assert.assertTrue("/crm/index".equals(page));
	}

}
