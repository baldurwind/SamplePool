package com.chamago.pcrm.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import sy.hbm.Syresource;

import com.chamago.pcrm.common.pojo.LkpCity;
import com.chamago.pcrm.common.pojo.LkpErrorCode;
import com.chamago.pcrm.common.pojo.LkpProvince;
import com.chamago.pcrm.common.pojo.PromotionLkp;

public interface LkpMapper {

	@Select("SELECT id,name,tb_id tbId,weibo_id weiboId FROM lkp_city")
	public List<LkpCity>  findCitys();
	
	@Select("SELECT id,name,tb_id tbId,weibo_id weiboId FROM lkp_province")
	public List<LkpProvince>  findProvinces();
	
	
	@Select("SELECT id,name FROM promotion_lkp")
	public List<PromotionLkp>  findPromotions();
	
	@Select("SELECT *  FROM lkp_error_code")
	public List<LkpErrorCode> findErrorCodes();
	
	
	@Select("SELECT id, pid,name,url,comments,onoff FROM SYRESOURCE WHERE ONOFF=1 and (id like '0-7%' or id='0')")
	public List<Syresource> findAllSyresources();
	

			
}
