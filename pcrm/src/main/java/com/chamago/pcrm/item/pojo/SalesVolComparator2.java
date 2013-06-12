package com.chamago.pcrm.item.pojo;

import java.util.Comparator;

public class SalesVolComparator2 implements Comparator {

	public int compare(Object o1, Object o2) {
		int salesVol1 = Integer.parseInt(((ItemSearch)o1).getSalesVol());
		int salesVol2 = Integer.parseInt(((ItemSearch)o2).getSalesVol());
		return salesVol2 > salesVol1 ? 1 : (salesVol1 == salesVol2 ? 0 : -1) ;
	}
}