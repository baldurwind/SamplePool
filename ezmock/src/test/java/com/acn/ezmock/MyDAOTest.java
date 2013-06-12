package com.acn.ezmock;

import static org.easymock.EasyMock.isA;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyDAOTest {

	private MyDAO myDAO;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMergeAirportByCity() {
		myDAO =EasyMock.createMock(MyDAO.class);
		
		List<Airport> list=new ArrayList();
		list.add(new Airport("PVG"));
		list.add(new Airport("SVG"));
		list.add(new Airport("SHA"));
		EasyMock.expect(myDAO.findAirport(isA(String.class))).andReturn(list).times(1);
		EasyMock.replay(myDAO);
		
		Assert.assertEquals(3, myDAO.findAirport("SVG").size());
		EasyMock.verify(myDAO);
		
	}
}
