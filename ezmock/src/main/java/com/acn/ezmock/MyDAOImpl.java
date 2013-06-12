package com.acn.ezmock;

import java.util.List;

public class MyDAOImpl implements MyDAO {

	public List<Airport> findAirport(String city) {
		System.out.println("MyDAOImpl start");
		if(city==null)
			throw new NullPointerException();
		
		
		System.out.println("MyDAOImpl end");
		return null;
	}

}
