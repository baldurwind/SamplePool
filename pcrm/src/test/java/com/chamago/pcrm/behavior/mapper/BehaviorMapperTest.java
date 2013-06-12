package com.chamago.pcrm.behavior.mapper;

import static org.junit.Assert.*;




import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.Utils;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})  
public class BehaviorMapperTest extends AbstractJUnit4SpringContextTests {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass");
	}
	
	public static void main(String ag[]){
		System.out.println(1);
	}
	@Autowired
	private BehaviorMapper behaviorMapper;
	

	@Test
	public void testFindKeyword() {
		List<Object[]>	 result=behaviorMapper.findKeyword("stevemadden旗舰店", "cole_888");
		Assert.assertEquals("Test:","SWBUCKKIE",result.get(0)[0]);
		Assert.assertEquals("Test:","1",result.get(0)[1]);
		Assert.assertEquals("Test:","SWTROOPA-S",result.get(1)[0]);
		Assert.assertEquals("Test:","1",result.get(1)[1]);
	}

	@Test
	public void testFindPageView() {
		 Integer 	 result=behaviorMapper.findPageView("stevemadden旗舰店", "英伦玫瑰01");
		 Assert.assertTrue("Test:",161==result);
	}

	@Test
	public void testFindVisitingHistory() {
		List<Object[]> result=behaviorMapper.findVisitingHistory("stevemadden旗舰店", "angela_shao");
		Assert.assertEquals("Test:","12440965824",result.get(0)[0]);
		Assert.assertEquals("Test:","3",result.get(0)[1]);
		Assert.assertEquals("Test:","STEVE MADDEN旗舰店麂皮粗跟女鞋SWSARRINA新品 原价1290元",result.get(0)[2]);
		Assert.assertEquals("Test:","http://item.taobao.com/item.htm?id=12440965824",result.get(0)[3]);
		Assert.assertEquals("Test:","http://img02.taobaocdn.com/bao/uploaded/i2/T1LOmzXlhtXXar1t.._111728.jpg",result.get(0)[4]);
		Assert.assertEquals("Test:","645.00",result.get(0)[5]);
		Assert.assertEquals("Test:","12819333185",result.get(1)[0]);
		Assert.assertEquals("Test:","5",result.get(1)[1]);
		Assert.assertEquals("Test:","STEVE MADDEN旗舰店水钻蝴蝶结麂皮鞋 SWKARISMA 原价1090元",result.get(1)[2]);
		Assert.assertEquals("Test:","http://item.taobao.com/item.htm?id=12819333185",result.get(1)[3]);
		Assert.assertEquals("Test:","http://img03.taobaocdn.com/bao/uploaded/i3/T10LeqXmNOXXc38xw._112449.jpg",result.get(1)[4]);
		Assert.assertEquals("Test:","545.00",result.get(1)[5]);
	}

	@Test
	public void testFindBoughtItem() {
		List<Object[]> result=behaviorMapper.findBoughtItem("芳蕾玫瑰粉粉旗舰店", "我是女生13984");
		Assert.assertTrue("Test:",7==result.size());
	}

	@Test
	public void testFindConsultationHistory() {
		List<Object[]> results=behaviorMapper.findConsultationHistory("wanghe_taobao", "草本");
		
		Assert.assertTrue("Test:",15333008871L==(Long)results.get(0)[0]);
		Assert.assertTrue("Test:",15187972183L==(Long)results.get(1)[0]);
		Assert.assertTrue("Test:",14083968885L==(Long)results.get(2)[0]);
		
	}
	@Test
	public void testFindItemRelation(){
		//List<Object[]> results=	behaviorMapper.findItemRelation(3994226377L);
	//	Assert.assertTrue("Test:",10==results.size());
	}
}
