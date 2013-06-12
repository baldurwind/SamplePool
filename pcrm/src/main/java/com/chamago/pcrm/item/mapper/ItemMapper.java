package com.chamago.pcrm.item.mapper;

import java.util.List;

import com.chamago.pcrm.crm.pojo.CrmItem;

public interface ItemMapper {
	
	 
	 
	 
	 List<Object[]> findItemBySeller(String seller);
	 
	 List<Object[]> findSkuByNumiidList(List numiidList);
	 
	 Object[] findItemById(Long numiid);
	 
	 List<CrmItem> findItemsDetail(List ids);
}
