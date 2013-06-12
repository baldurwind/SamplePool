/**
 * 
 */
package com.chamago.pcrm.worktable.advice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author gavin.peng
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})  
public class AdviceServiceImplTest {

	@Autowired
	private AdviceService adviceService;
	
	
	
	@Test
	public void testBatchUpdateAdviceByIds(){
		try{
			String type = "14";
		String ids = "4f58694ff47d41d379864e55,4f587d36f47d5b1b53999fcf,"+null;
		int rs = adviceService.batchUpdateAdviceByIds(ids, type);
		System.out.println("rs:"+rs);
		//Assert.assertTrue(rs == 1);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
