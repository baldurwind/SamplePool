package com.chamago.pcrm.common.mapper;

import com.chamago.pcrm.common.pojo.TotalPageView;


public interface TotalPageViewMapper {
	
	public TotalPageView findByUser(String sellernick,String buyernick);
}
