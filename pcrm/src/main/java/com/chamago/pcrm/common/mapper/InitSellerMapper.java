package com.chamago.pcrm.common.mapper;


public interface InitSellerMapper {

	public int initAdminAccount(String id,String seller,String account,String password) ;

	public int initHistoryData(String seller) ;
}
