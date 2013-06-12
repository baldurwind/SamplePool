package com.chamago.pcrm.item.service.impl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKQueryParser;

import com.chamago.lucene.api.LuceneUtils;
import com.chamago.pcrm.common.service.LkpService;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.MemoryDisplay;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.item.mapper.ItemMapper;
import com.chamago.pcrm.item.service.ItemLuceneService;
import com.chamago.pcrm.marketing.mapper.MarketingMapper;
import com.taobao.api.internal.util.StringUtils;

@Service
public class ItemLuceneServiceImpl implements ItemLuceneService {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private LuceneUtils luceneUtils;
	
	@Autowired
	private LkpService lkpService;
	
	
	@Autowired
	private MarketingMapper marketingMapper;
	// @PostConstruct
     @Scheduled(fixedDelay = 60*15*1000)   //init every 60 mins
	public void  init(){
		 System.err.println("item init");
	 	Iterator<String> it=lkpService.getSubscribers().keySet().iterator();
		String root=luceneUtils.ROOT_PATH;
		File f=new File(root);
			if(f.exists())
				  Utils.delDirectory(f);
			f.mkdirs();
		while(it.hasNext()){
			String tmp=it.next();
			String seller=tmp.replace(C.PCRM_APPKEY+"_", "");
			try {
				initItem(seller);
				initSku(seller);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("init item lucene compeleted");
	}
	
	
	public void initItem(String seller)throws IOException {
		List<Object[]> itemList=itemMapper.findItemBySeller(seller);
		List<Document> docList=new ArrayList<Document>();
		//Object[]= 0:num_iid, 1:cid, 2:title,3:props_name, 4:num,5:picurl,6price,7created
		List<Long> list=marketingMapper.findLimitDiscountItemIdForLucene();
		String discounted="";
		for(Object[] item:itemList){
			if(list.contains(Long.valueOf((String)item[0])))
				discounted="Y";
			else
				discounted="N";
			
			Document doc=generateItemDocument(seller,item,discounted);
		
			if(doc!=null)
				docList.add(doc);
			
			
		}
		luceneUtils.createBatch(C.LUCENE_DIRECTORY_ITEM, docList);
		System.out.println("init item completed:"+seller+": docList:"+docList.size());
	
	}
	
	public Document generateItemDocument(String seller,Object[] item,String discounted){
		
		String prop_name=this.parseSkuProps((String)item[3]);
		
		if(prop_name==null)
			return null;
		
		Document doc=new Document();
		doc.add(new Field(C.LUCENE_FIELD_ITEM_SELLER,seller,Store.NO,Index.NOT_ANALYZED));
		doc.add(new Field(C.LUCENE_FIELD_ITEM_NUMIID,(String)item[0],Store.YES,Index.NOT_ANALYZED));
		doc.add(new Field(C.LUCENE_FIELD_ITEM_TITLE,(String)item[2],Store.YES,Index.ANALYZED));
		doc.add(new Field(C.LUCENE_FIELD_ITEM_PROPS,prop_name,Store.YES,Index.NO));
		doc.add(new Field(C.LUCENE_FIELD_ITEM_PICURL,(String)item[5],Store.YES,Index.NO));
		doc.add(new Field(C.LUCENE_FIELD_ITEM_PRICE,(String)item[6],Store.YES,Index.NO));
		doc.add(new Field(C.LUCENE_FIELD_ITEM_CREATED,(String)item[7],Store.YES,Index.NO));
		doc.add(new Field(C.LUCENE_FIELD_ITEM_NUM,(String)item[4],Store.YES,Index.NO));
		doc.add(new Field(C.LUCENE_FIELD_ITEM_HAS_DISCOUNT,discounted,Store.YES,Index.NO));
		
		return doc;
	}
	
	public List<Document> searchItem(String seller,Term term)  throws IOException,ParseException{
		TermQuery tq1=new TermQuery(new Term(C.LUCENE_FIELD_ITEM_SELLER,seller));
		TermQuery tq2=new TermQuery(term);
	
		BooleanQuery query=new BooleanQuery();
			query.add(tq1, Occur.MUST);
			query.add(tq2, Occur.MUST);
			System.out.println("lucene:"+luceneUtils);
	return luceneUtils.search(C.LUCENE_DIRECTORY_ITEM,query);
	}
	
	public List<Document> searchItem(String seller,List<Object[]> numiidList) throws IOException, ParseException{
		BooleanQuery query=new BooleanQuery();

		BooleanQuery itemIdListQuery=new BooleanQuery();
		for(Object[] obj:numiidList){
			itemIdListQuery.add(new TermQuery(new Term(C.LUCENE_FIELD_ITEM_NUMIID,String.valueOf(obj[0]))),Occur.SHOULD);
		}
		
		TermQuery  sellerQuery =new TermQuery(new Term(C.LUCENE_FIELD_ITEM_SELLER,seller));
			query.add(itemIdListQuery, Occur.MUST);
			query.add(sellerQuery,Occur.MUST);
		return luceneUtils.search(C.LUCENE_DIRECTORY_ITEM, query);
	}
	public  Document  searchItem(String seller,Long numiid)throws IOException, ParseException{
		BooleanQuery query=new BooleanQuery();
		TermQuery  q1 =new TermQuery(new Term(C.LUCENE_FIELD_ITEM_SELLER,seller));
		TermQuery  q2 =new TermQuery(new Term(C.LUCENE_FIELD_ITEM_NUMIID,String.valueOf(numiid)));
			query.add(q1, Occur.MUST);
			query.add(q2,Occur.MUST);
			List<Document> result=luceneUtils.search(C.LUCENE_DIRECTORY_ITEM, query);
			if(null==result||result.size()<1)
				return null;
			else
				return result.get(0);
	}
	
	public Document searchItemByNumiid(Long numiid) throws IOException,
		ParseException {
		Term numidTerm = new Term(C.LUCENE_FIELD_ITEM_NUMIID,String.valueOf(numiid));
		Query query = new TermQuery(numidTerm);
		List<Document> result=luceneUtils.search(C.LUCENE_DIRECTORY_ITEM, query);
		if(null==result||result.size()<1)
			return null;
		else
			return result.get(0);
	}
	
	public void initSku(String seller)throws IOException{
		List<Object[]> itemList=itemMapper.findItemBySeller(seller);
		List<Object> numiidList=new ArrayList<Object>();
		List<Document> docList=new ArrayList<Document>();
		for(Object[] item:itemList){
			numiidList.add(item[0]);
		}
		if(numiidList.size()<1)
			return;
		
		List<Object[]> skuList=itemMapper.findSkuByNumiidList(numiidList);
		// 0sku_id,1num_iid, 2quantity,3price, 4properties_name 
		for(Object[] sku:skuList){
			if(null==sku[4])
				continue;
			Document doc=generateSkuDocument(seller,sku);
			docList.add(doc);
		}
		luceneUtils.createBatch(C.LUCENE_DIRECTORY_SKU, docList);
	}
	private Document generateSkuDocument(String seller,Object[] sku){
		Document doc=new Document();
		doc.add(new Field(C.LUCENE_FIELD_SKU_SELLER,seller,Store.NO,Index.NOT_ANALYZED));
		doc.add(new Field(C.LUCENE_FIELD_SKU_NUMIID,(String)sku[1],Store.NO,Index.NOT_ANALYZED));
		doc.add(new Field(C.LUCENE_FIELD_SKU_ID,(String)sku[0],Store.YES,Index.NOT_ANALYZED));
		doc.add(new Field(C.LUCENE_FIELD_SKU_PROPS,(String)sku[4],Store.YES,Index.NO));
		doc.add(new Field(C.LUCENE_FIELD_SKU_PRICE,(String)sku[3],Store.YES,Index.NO));
		doc.add(new Field(C.LUCENE_FIELD_SKU_NUM,(String)sku[2],Store.YES,Index.NO));
		return doc;
	}
	
	public List<Document> searchSku(String seller,List<Object[]> skuidList) throws IOException, ParseException{
		BooleanQuery query=new BooleanQuery();
		BooleanQuery itemIdListQuery=new BooleanQuery();
		for(Object[] obj:skuidList){
			itemIdListQuery.add(new TermQuery(new Term(C.LUCENE_FIELD_SKU_ID,String.valueOf(obj[0]))),Occur.SHOULD);
		}
		TermQuery  sellerQuery =new TermQuery(new Term(C.LUCENE_FIELD_SKU_SELLER,seller));
			query.add(itemIdListQuery, Occur.MUST);
			query.add(sellerQuery,Occur.MUST);
		return luceneUtils.search(C.LUCENE_DIRECTORY_SKU, query);
	}
	
	
	public List<Document> searchSku(String seller,Long numiid)throws IOException, ParseException{
		BooleanQuery query=new BooleanQuery();
		TermQuery  q1 =new TermQuery(new Term(C.LUCENE_FIELD_SKU_SELLER,seller));
		TermQuery  q2 =new TermQuery(new Term(C.LUCENE_FIELD_SKU_NUMIID,String.valueOf(numiid)));
			query.add(q1, Occur.MUST);
			query.add(q2,Occur.MUST);
		return luceneUtils.search(C.LUCENE_DIRECTORY_SKU, query);
	}
	public Document searchSkuById(String seller,Long skuid)throws IOException, ParseException{
		BooleanQuery query=new BooleanQuery();
		TermQuery  q1 =new TermQuery(new Term(C.LUCENE_FIELD_SKU_SELLER,seller));
		TermQuery  q2 =new TermQuery(new Term(C.LUCENE_FIELD_SKU_ID,String.valueOf(skuid)));
			query.add(q1, Occur.MUST);
			query.add(q2,Occur.MUST);
		List<Document> list=luceneUtils.search(C.LUCENE_DIRECTORY_SKU, query);
		if(null==list||list.size()<1)
			return null;
		else
		return list.get(0);
	}
	
	
	public static void main(String ags[]){
	  try {
		  String str="裤长:短裤（穿起至膝盖以上）;货号:YEXPN246;裤型:直筒裤（膝盖围=裤口围）;颜色:军绿色;尺码:28（2.16尺）;尺码:29（2.23尺）;尺码:30（2.31尺）;尺码:31（2.39尺）;尺码:32（2.46尺）;尺码:33（2.54尺）;尺码:34（2.62尺）;尺码:35（2.69尺）;尺码:36（2.77尺）;尺码:37（2.85尺）;尺码:38（2.92尺）;尺码:39（3尺）;尺码:40（3.08尺）;尺码:41（3.16尺）;尺码:42(3.23尺);牛仔面料:薄牛仔布;工艺处";
			ItemLuceneServiceImpl a=new ItemLuceneServiceImpl();
			a.parseSkuProps(str);
		/*	ApplicationContext ac=Utils.getClassPathXMlApplication();
			ItemLuceneService  service=ac.getBean(ItemLuceneService.class);
             service.init(); */
			/*// service.initSku("stevemadden旗舰店");
			//List  <Document> docList=service.searchItem("stevemadden旗舰店", new Term(C.LUCENE_FIELD_ITEM_NUMIID,"13670634157"));
			for(Document doc:docList){
				System.out.println(doc.get("title"));	
			}
			
			//List  <Document> docList=service.search("stevemadden旗舰店", new Term(C.LUCENE_KEY_NUMIID,"13136016716"),C.LUCENE_KEY_TYPE_SKU); 
     		//List  <Document> docList=service.search("stevemadden旗舰店", new Term(C.LUCENE_KEY_TITLE,"饰短"),C.LUCENE_KEY_TYPE_ITEM);
			List l=new ArrayList();
			l.add(13671309351L);
			l.add(15238680802L);
			l.add(15238792343L);
			l.add(15238872096L);
			List  <Document> docList=service.search("stevemadden旗舰店", l,C.LUCENE_KEY_TYPE_ITEM);*/
			 
			
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}  
	} 
	
	private String parseSkuProps(String str){
		
	String[] props=null;
	
	try {
		props = str.split(";");
	} catch (NullPointerException e) {
		 return null;
	}
		Map<String,String> map=new HashMap<String,String>();
		for(String prop:props){
			 String[] term=prop.split(":");
			 if(term.length!=2||StringUtils.isEmpty(term[0])||StringUtils.isEmpty(term[1])||"undefined".equals(term[0])||"undefined".equals(term[1]))
				 continue;
			 if(null==map.get(term[0]))
				 map.put(term[0], term[1]);
			 else{
				String tmp= map.get(term[0])+"[-]"+term[1];
				 map.put(term[0], tmp);
			 }
		}
		for(String prop:props){
			 String[] term=prop.split(":");
			 if(term.length!=2||StringUtils.isEmpty(term[0])||StringUtils.isEmpty(term[1])||"undefined".equals(term[0])||"undefined".equals(term[1]))
				 continue;
			 String name=term[0];
			 
			  if(null!=map.get(name)&&!map.get(name).contains("[-]")){
				  map.remove(name);
			  }
		}
		String result=map.toString();
		map.clear();
		result= result.substring(1,result.length()-1);
	
		 return result;
	}
	
	public LuceneUtils getLuceneUtils() {
		return luceneUtils;
	}
	public void setLuceneUtils(LuceneUtils luceneUtils) {
		this.luceneUtils = luceneUtils;
	}

	@Override
	public List<Document> searchItemByKeywordsAndSellerNick(String seller,
			String keywords) throws IOException, ParseException {
		TermQuery tq1=new TermQuery(new Term(C.LUCENE_FIELD_ITEM_SELLER,seller));
		Query ikquery = IKQueryParser.parse(C.LUCENE_FIELD_ITEM_TITLE, keywords);
		BooleanQuery query=new BooleanQuery();
		query.add(tq1, Occur.MUST);
		query.add(ikquery, Occur.MUST);
		return luceneUtils.search(C.LUCENE_DIRECTORY_ITEM,query);
	}
	
	
}
