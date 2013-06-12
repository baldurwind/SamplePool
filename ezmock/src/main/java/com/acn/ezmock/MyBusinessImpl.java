package com.acn.ezmock;

import java.util.List;

public class MyBusinessImpl implements MyBusiness{

	
	private MyDAO myDAO;
	
	public MyDAO getMyDAO() {
		return myDAO;
	}

	public void setMyDAO(MyDAO myDAO) {
		this.myDAO = myDAO;
	}

	public List<Airport> MergeAirportByCity() {
		try{
			return myDAO.findAirport("SVG");
		}
		catch(Exception e){
			return null;
		}
		
	}

}
