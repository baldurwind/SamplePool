package com.chamago.pcrm.item.web;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.behavior.web.BehaviorController;
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})  
public class ItemControllerTest {

	
	@Autowired
	private  SkuController itemController;
	private   MockHttpServletRequest request=new MockHttpServletRequest();
	@Test
	public void testFindSkuNum() {
		//request.setParameter("props", "[\"尺码_41\",\" 颜色分类_黑色\"]");
		Long numiid=15187972183L;
		String seller="stevemadden旗舰店";
	}
	
	public void testValidateProps(){
		
	}
	
	
	

}
