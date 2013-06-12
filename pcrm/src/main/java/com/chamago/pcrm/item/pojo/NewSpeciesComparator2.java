package com.chamago.pcrm.item.pojo;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class NewSpeciesComparator2 implements Comparator {
	
	public int compare(Object o1, Object o2) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GregorianCalendar cal = new GregorianCalendar();
		GregorianCalendar cal2 = new GregorianCalendar();
		try {
			cal.setTime(format.parse(((ItemSearch)o1).getNewSpecies()));
			cal2.setTime(format.parse(((ItemSearch)o2).getNewSpecies()));
		} catch(Exception e) {}
		
		return cal.after(cal2) ? 1 : (cal.equals(cal2) ? 0 : -1) ;
	}
}
