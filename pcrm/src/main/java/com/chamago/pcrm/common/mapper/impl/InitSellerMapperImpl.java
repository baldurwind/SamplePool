package com.chamago.pcrm.common.mapper.impl;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.chamago.pcrm.common.mapper.InitSellerMapper;

public class InitSellerMapperImpl   extends SqlSessionDaoSupport implements InitSellerMapper {

	@Override
	public int initAdminAccount(String id, String seller, String account, String password) {
		Map<String,String> map=new HashMap<String,String>();
			map.put("id", id);
			map.put("seller", seller);
			map.put("account", account);
			map.put("password", password);
		return this.getSqlSession().insert("InitSellerMapper.initAdminAccount",map);
	}

	@Override
	public int initHistoryData(String seller) {
		return 0;
	}


	
}
