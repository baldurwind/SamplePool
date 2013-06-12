package com.chamago.pcrm.item.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.item.pojo.ItemSearch;
import com.chamago.pcrm.item.service.ItemService;

/**
 * 商品Controller
 * @author James.wang
 */
@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 跳转店铺商品信息查询页
	 */
	@RequestMapping("/toSearch")
	@AopLogModule(name=C.LOG_MODULE_ITEM,layer=C.LOG_LAYER_CONTROLLER)
	public String toSearch(ModelMap map, HttpServletRequest request) {
		return "/item/item_search";
	}
	
	/**
	 * 店铺商品信息查询
	 */
	@RequestMapping("/search")
	@AopLogModule(name=C.LOG_MODULE_ITEM,layer=C.LOG_LAYER_CONTROLLER)
	public String search(ModelMap map, HttpServletRequest request) {
		String title = request.getParameter("title");
		String type = request.getParameter("type");
		String p = request.getParameter("p");
		String sellerNick = request.getParameter("sellerNick");
		PagedListHolder<ItemSearch> list = null;
		int pageNo = 0;
		if(request.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(request.getParameter("pageNo").toString());
			if(pageNo < 0) {
				pageNo = 0;
			}
		}
		try {
			List<ItemSearch> itemSearchs = getItemService().search(title.toLowerCase(), type, sellerNick, p);
			list = new PagedListHolder<ItemSearch>(itemSearchs);
			list.setPageSize(4);
			list.setPage(pageNo);
			if(list.getPageList() != null && list.getPageList().size() > 0) {
				map.put("itemSearch", list);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "/item/search_detail";
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
}