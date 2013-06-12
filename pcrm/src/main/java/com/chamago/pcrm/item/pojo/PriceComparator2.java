package com.chamago.pcrm.item.pojo;

import java.util.Comparator;

public class PriceComparator2 implements Comparator {

	public int compare(Object o1, Object o2) {
		double price1 = Double.parseDouble(((ItemSearch)o1).getPrice());
		double price2 = Double.parseDouble(((ItemSearch)o2).getPrice());
		return price2 > price1 ? 1 : (price1 == price2 ? 0 : -1) ;
	}

}
