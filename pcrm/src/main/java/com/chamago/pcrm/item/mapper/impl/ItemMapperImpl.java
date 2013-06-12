package com.chamago.pcrm.item.mapper.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.chamago.pcrm.crm.pojo.CrmItem;
import com.chamago.pcrm.item.mapper.ItemMapper;

@SuppressWarnings("unchecked")
public class ItemMapperImpl extends SqlSessionDaoSupport implements ItemMapper {

	
	public List<Object[]> findItemBySeller(String seller){
		return getSqlSession().selectList("ItemMapper.findItemBySeller",seller);
	}
	
	
	public  List<Object[]> findSkuByNumiidList(List numiidList){
	    
		return getSqlSession().selectList("ItemMapper.findSkuByNumiidList",numiidList);
	}
	
	public Object[] findItemById(Long numiid){
		return ( Object[] )getSqlSession().selectOne("ItemMapper.findItemById",numiid);
	}
	
	public List<CrmItem> findItemsDetail(List ids){
		List list=getSqlSession().selectList("ItemMapper.findItemsDetail",ids);
		return  list;
	}
}
