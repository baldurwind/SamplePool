package com.chamago.pcrm.item.pojo;

import java.util.Comparator;

public class CollectComparator2 implements Comparator{
	
	public int compare(Object o1, Object o2) {
		int collect1 = Integer.parseInt(((ItemSearch)o1).getCollect());
		int collect2 = Integer.parseInt(((ItemSearch)o2).getCollect());
		return collect2 > collect1 ? 1 : (collect1 == collect2 ? 0 : -1) ;
	}
}
