package com.chamago.pcrm.item.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;

import com.chamago.lucene.api.LuceneUtils;



public interface ItemLuceneService {

	public   void initItem(String seller) throws IOException;

	public   List<Document> searchItem(String seller, Term term) throws IOException, ParseException;

	public   List<Document> searchItem(String seller, List<Object[]> numiidList) throws IOException, ParseException;

	public   Document searchItem(String seller, Long numiid)throws IOException, ParseException;

	public   void initSku(String seller) throws IOException;

	public   List<Document> searchSku(String seller,List<Object[]> skuidList) throws IOException, ParseException;

	public   List<Document> searchSku(String seller, Long numiid)throws IOException, ParseException;

	public   void setLuceneUtils(LuceneUtils luceneUtils);
	
	public Document searchSkuById(String seller,Long skuid)throws IOException, ParseException;
	
	public   Document searchItemByNumiid(Long numiid)throws IOException, ParseException;

	public void  init();
	
	/**
	 * 根据关键字搜索卖家seller的商品在title字段上搜索
	 * @param seller
	 * @param keywords
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public   List<Document> searchItemByKeywordsAndSellerNick(String seller, String keywords) throws IOException, ParseException;
}
