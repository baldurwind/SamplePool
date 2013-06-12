package com.acn.ezmock;


import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.easymock.EasyMock.isA;
public class MyBusinessTest {

	private MyDAO myDAO;
	
	private MyBusiness myBusinessImpl;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		myDAO=EasyMock.createMock(MyDAO.class);
		
		
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testMergeAirportByCityWithNullException() {
		myBusinessImpl=new MyBusinessImpl();
		EasyMock.expect(myDAO.findAirport(isA(String.class))).andThrow(new NullPointerException());
		EasyMock.replay(myDAO);
		myBusinessImpl.setMyDAO(myDAO);
		List list=myBusinessImpl.MergeAirportByCity();
		Assert.assertEquals(null, list);
		EasyMock.verify(myDAO);
		
		
	}

}
