package com.chamago.pcrm.common.mapper.impl;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.chamago.pcrm.common.mapper.TotalPageViewMapper;
import com.chamago.pcrm.common.pojo.TotalPageView;
import com.chamago.pcrm.common.utils.MemCached;


public class TotalPageViewMapperImpl extends SqlSessionDaoSupport implements TotalPageViewMapper{
	
	@MemCached
	public TotalPageView findByUser(String sellernick,String buyernick){
		Map<String,String> map=new HashMap<String,String>();
		map.put("sellernick", sellernick);
		map.put("buyernick", buyernick);
		return (TotalPageView)getSqlSession().selectOne("TotalPageViewMapper.findByUser",map);
	}
	
}