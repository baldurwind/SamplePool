package com.chamago.pcrm.trade.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.common.utils.Utils;
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})  
public class TradeMapperTest {

	@Autowired
	private TradeMapper tradeMapper;
	
	@Test
	public void testFindOrderByTidList() {
		List<Long> tidList=new ArrayList<Long>();
		tidList.add(104688434015646L);
		tidList.add(107016987973572L);
		tradeMapper.findOrderByTidList(tidList);
	}

//	@Test
//	public void testFindTradeBySellerBuyer() {
//		tradeMapper.findTradeBySellerBuyer("芳蕾玫瑰粉粉旗舰店", "我是女生13984");
//	}

	@Test
	public void testFindTradeByTid() {
		 Object[]  result= tradeMapper.findTradeByTid(105382055815646L);
		 Assert.assertEquals("test", (Object)229.89==result[3]);
	}
	@Test
	public void testFindRefundNum(){
		List<Long> results=tradeMapper.findRefundNum("顾家工艺官方旗舰店","y_q100");
		Assert.assertTrue(results.get(0)==100477279779093L);
		Assert.assertTrue(results.get(1)==100300176189093L);
	}
	@Test
	public void testFindWaitingSendGoodsTradeId(){
		List<Long> results=tradeMapper.findWaitingSendGoodsTradeId("顾家工艺官方旗舰店","dingpangzi_2008");
		Assert.assertTrue(results.get(0)==92173008569089L);
		
	}

	
	@Test
	public void testFindPerformanceParamsBySeller() throws ParseException{
		Map<String,Object> params = new HashMap<String,Object>();
		Date today = Utils.StringToDate("2012-02-23", "yyyy-MM-dd");
		String startTime = Utils.DateToString(today, "yyyy-MM-dd")+" 00:00:00";
		String endTime = Utils.DateToString(today, "yyyy-MM-dd")+" 23:59:59";
		params.put("seller", "良无限home");
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("buyers", "cntaobaobaronsnow");
		List<Object[]> results=tradeMapper.findPerformanceParamsBySeller(params);
		Assert.assertTrue(results.size() == 1);
		
	}
}
