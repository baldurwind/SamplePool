package com.acn.ezmock;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.easymock.EasyMock.isA;
public class MyBusinessTest {

	private MyDAO myDAO;
	
	
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
	public void testMergeAirportByCity() {
		List<Airport> list=new ArrayList();
		list.add(new Airport("PVG"));
		list.add(new Airport("SVG"));
		list.add(new Airport("SHA"));
		
		EasyMock.expect(myDAO.findAirport(isA(String.class))).andReturn(list).anyTimes();
		EasyMock.replay(myDAO);
		
		System.out.println(myDAO.findAirport("SVG").size());
		System.out.println(myDAO.findAirport("SVG").size());
		System.out.println(myDAO.findAirport("SVG").size());
		
		assertNotNull(myDAO.findAirport("SVG"));
      //  assertEquals();    //veify return user
		EasyMock.verify(myDAO);

		
		
	}

}
