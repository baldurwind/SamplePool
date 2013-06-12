package com.chamago.pcrm.item.mapper;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})  
public class ItemMapperImplTest {

	@Autowired
	private ItemMapper itemMapper;

	@Test
	public void testFindItemBySeller() {
		int count = 0;
		List<Object[]> results=itemMapper.findItemBySeller("芳蕾玫瑰粉粉旗舰店");
		Assert.assertTrue("test",96==results.size());
	}

	@Test
	public void testFindSkuByNumiidList() {
		List<Long> numiidList=new ArrayList<Long>();
			numiidList.add(12783245451L);
			numiidList.add(12699970741L);
			
	List<Object[]> results=	itemMapper.findSkuByNumiidList(numiidList);
	Assert.assertTrue("test",2==results.size());
	}

	@Test
	public void testFindItemById() {
		Object[] result=itemMapper.findItemById(3994226377L);
		Assert.assertTrue("test","芳蕾玫瑰粉粉旗舰店".equals(result[2]));
	}
}
