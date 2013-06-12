package com.chamago.pcrm.item.service;

import java.util.List;

import com.chamago.pcrm.item.pojo.ItemSearch;

/**
 * 商品搜索业务
 * @author James.wang
 */
public interface ItemService {

	
	public Object[] findItemById(Long numiid);
	
	/**
	 * 商品信息搜索
	 * @param title       搜索关键字
	 * @param type        搜索类型
	 * @param sellerNick  店铺名称
	 * @return            商品信息列表
	 */
	public List<ItemSearch> search(String title, String type, String sellerNick, String p) throws Exception;
}
