package com.chamago.pcrm.item.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.item.mapper.ItemMapper;
import com.chamago.pcrm.item.pojo.ItemSearch;
import com.chamago.pcrm.item.pojo.NewSpeciesComparator;
import com.chamago.pcrm.item.pojo.NewSpeciesComparator2;
import com.chamago.pcrm.item.pojo.PriceComparator;
import com.chamago.pcrm.item.pojo.PriceComparator2;
import com.chamago.pcrm.item.service.ItemLuceneService;
import com.chamago.pcrm.item.service.ItemService;

/**
 * 商品搜索业务
 * @author James.wang
 */
@Service
public class ItemServiceImpl    implements ItemService {
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemLuceneService itemLuceneService;
	
	public Object[] findItemById(Long numiid) {
		return itemMapper.findItemById(numiid);
	}

	/**
	 * 商品信息搜索
	 * @param title       搜索关键字
	 * @param type        搜索类型
	 * @param sellerNick  店铺名称
	 * @return            商品信息列表
	 */
	public List<ItemSearch> search(String title, String type, String sellerNick, String p) throws Exception {
		List<ItemSearch> itemSearchs = new ArrayList<ItemSearch>();
		try {
			//List<Document> items = getItemLuceneService().searchItem(sellerNick, new Term(C.LUCENE_FIELD_ITEM_TITLE,title));
			//对keywords进行分词后搜索
			List<Document> items = getItemLuceneService().searchItemByKeywordsAndSellerNick(sellerNick, title);
			for(int i = 0; i < items.size(); i++) {
				if(!items.get(i).get(C.LUCENE_FIELD_ITEM_TITLE).toLowerCase().contains(title.toLowerCase()))
					continue;
				ItemSearch itemSearch = new ItemSearch();
				itemSearch.setNumid(items.get(i).get(C.LUCENE_FIELD_ITEM_NUMIID));
				itemSearch.setTitle(items.get(i).get(C.LUCENE_FIELD_ITEM_TITLE));
				itemSearch.setPrice(items.get(i).get(C.LUCENE_FIELD_ITEM_PRICE));
				itemSearch.setNewSpecies(items.get(i).get(C.LUCENE_FIELD_ITEM_CREATED));
				itemSearch.setNum(items.get(i).get(C.LUCENE_FIELD_ITEM_NUM));
				itemSearch.setPicUrl(items.get(i).get(C.LUCENE_FIELD_ITEM_PICURL));
				itemSearchs.add(itemSearch);
			}
			if("0".equals(p)) {
				Collections.sort(itemSearchs, new PriceComparator());
			} else if("1".equals(p)) {
				Collections.sort(itemSearchs, new PriceComparator2());
			} else if ("4".equals(p)) {
				Collections.sort(itemSearchs, new NewSpeciesComparator());
			} else if ("5".equals(p)) {
				Collections.sort(itemSearchs, new NewSpeciesComparator2());
			}
		} catch(Exception e) {
			throw e;
		}
		return itemSearchs;
	}

	public ItemLuceneService getItemLuceneService() {
		return itemLuceneService;
	}

	public void setItemLuceneService(ItemLuceneService itemLuceneService) {
		this.itemLuceneService = itemLuceneService;
	}
}
