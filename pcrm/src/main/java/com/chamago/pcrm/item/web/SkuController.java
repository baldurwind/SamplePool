package com.chamago.pcrm.item.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.MemCached;
import com.chamago.pcrm.item.service.ItemLuceneService;
import com.chamago.pcrm.taobao.top.service.TopService;
import com.taobao.api.ApiException;
import com.taobao.api.domain.PromotionInItem;
import com.taobao.api.domain.PromotionInShop;
import com.taobao.api.domain.Sku;
import com.taobao.api.response.UmpPromotionGetResponse;

@Controller
public class SkuController {

	@Autowired
	TopService topService;
	
	@Autowired 
	ItemLuceneService itemLuceneService;
	
	@RequestMapping("/item/sku/findSku")
	@MemCached
	@AopLogModule(name=C.LOG_MODULE_ITEM,layer=C.LOG_LAYER_CONTROLLER)
	public  void findSku(HttpServletRequest request,HttpServletResponse response,@RequestParam String seller,@RequestParam Long numiid){
		try {
			JSONArray array=	JSONArray.fromObject(request.getParameter("props"));
			
			List<Document> docs=itemLuceneService.searchSku(seller, numiid);
			List<Sku> skus=new ArrayList<Sku>();
			boolean hasFind=false;
			for(Document doc:docs){
				String props=doc.get(C.LUCENE_FIELD_SKU_PROPS);
				boolean result=this.validateProps(array, props);
				
				String promotion_price="0";
				if(result){
					Long skuid=Long.valueOf(doc.get(C.LUCENE_FIELD_SKU_ID));
					Sku sku= topService.getSku(seller,numiid, skuid);
					
					UmpPromotionGetResponse res=topService.getUmpPromotionGet(seller, numiid);
					
					if(res.isSuccess()){
						if(res.getPromotions().getPromotionInItem()!=null){
							List<PromotionInItem> list=res.getPromotions().getPromotionInItem();
							List<String>  items=new ArrayList<String>();
							for(int i=0;i<list.size();i++){
								 List<String> str_sku=list.get(i).getSkuIdList();
								 boolean flag=false;
								 for(int j=0;j<str_sku.size();j++){
									 if(str_sku.equals(sku.getSkuId())){
										 promotion_price=list.get(i).getSkuPriceList().get(j);
										 flag=true;
										 break;
									 }
								 }
								 if(flag)
									 break;
							}
						}
					}
					String str="{\"result\":\"true\",\"promotion_price\":\""+promotion_price+"\",\"price\":\""+sku.getPrice()+"\",\"quantity\":\""+sku.getQuantity()+"\",\"skuid\":\""+skuid+"\"}";
					response.getWriter().write(str);
					hasFind=true;
					break;
				}
			}
			if(!hasFind)
				response.getWriter().write("{\"result\":\"nothing\"}");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}catch (ApiException e) {
			e.printStackTrace();
		}
	}
	private boolean validateProps(JSONArray array,String props){
		int size=array.size();
		Iterator it=array.iterator();
		while(it.hasNext()){
			String s=(String)it.next();
			String prop=s.replaceAll("_",":").trim();
			if(props.contains(prop))
				size--;
		}
		if(size==0)
			return true;
		else
			return false ;
	}
}
