package com.chamago.pcrm.worktable.knowledgebase.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKQueryParser;

import com.chamago.lucene.api.LuceneUtils;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.item.service.ItemLuceneService;
import com.chamago.pcrm.worktable.knowledgebase.mapper.KnowledgeMapper;
import com.chamago.pcrm.worktable.knowledgebase.pojo.Knowledge;
import com.chamago.pcrm.worktable.knowledgebase.service.KnowledgeLuceneService;

import com.mysql.jdbc.StringUtils;

@Service
public class KnowledgeLuceneServiceImpl  implements KnowledgeLuceneService{
	@Autowired
	private LuceneUtils luceneUtils;
	
	@Autowired
	private KnowledgeMapper knowledgeMapper;
	
	@PostConstruct
	@Scheduled(fixedDelay = 30*10*1000)  
	public void init()throws IOException {
		 List<Knowledge>  list=knowledgeMapper.findAllKnowledge();
		if(list!=null&&list.size()>0){
			List<Document> docs=new ArrayList<Document>();
			for(Knowledge knowledge:list){
				Document doc=new Document();
				doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_ID,knowledge.getId(),Store.YES,Index.NOT_ANALYZED));
				doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_ITEMID,String.valueOf(knowledge.getItemId()),Store.YES,Index.NOT_ANALYZED));
				if(!StringUtils.isNullOrEmpty(knowledge.getTitle())){
					doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_TITLE,knowledge.getTitle().toLowerCase(),Store.YES,Index.ANALYZED));
				}
				if(!StringUtils.isNullOrEmpty(knowledge.getContent())){
					doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_CONTENT,knowledge.getContent().toLowerCase(),Store.YES,Index.ANALYZED));
				}
				if(!StringUtils.isNullOrEmpty(knowledge.getCreator())){
					doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_CREATOR,knowledge.getCreator().toLowerCase(),Store.YES,Index.NOT_ANALYZED));
				}
				if(!StringUtils.isNullOrEmpty(knowledge.getSubjectId())){
					doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_SUBJECT_ID,knowledge.getSubjectId().toLowerCase(),Store.YES,Index.NOT_ANALYZED));
				}
				if(!StringUtils.isNullOrEmpty(knowledge.getSubjectName())){
					doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_SUBJECT_NAME,knowledge.getSubjectName().toLowerCase(),Store.YES,Index.ANALYZED));
				}
				if(!StringUtils.isNullOrEmpty(knowledge.getSellerNick())){
					doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_SELLER,knowledge.getSellerNick().toLowerCase(),Store.YES,Index.NOT_ANALYZED));
				}
				doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_SEND_NUMS,String.valueOf(knowledge.getSendNums()),Store.YES,Index.NOT_ANALYZED));
				docs.add(doc);
			}
			luceneUtils.createBatch(C.LUCENE_DIRECTORY_KNOWLEDGE_BASE, docs);
		} 
		System.out.println("init knowledge lucene compeleted");
	}
	
	public  List<Document> search(Long numiid)throws IOException ,ParseException{
		TermQuery query=new TermQuery(new Term(C.LUCENE_FIELD_KNOWLEDGE_ITEMID,String.valueOf(numiid)));
		return luceneUtils.search(C.LUCENE_DIRECTORY_KNOWLEDGE_BASE, query);
	}
	
	public List<Document> search(String seller,String keyword)throws IOException ,ParseException{
		//TermQuery query=new TermQuery(new Term(C.LUCENE_FIELD_KNOWLEDGE_ITEMID,String.valueOf(numiid)));
		//return luceneUtils.search(C.LUCENE_DIRECTORY_KNOWLEDGE_BASE, query);
		return null;
	}
	
	public void  add(Knowledge knowledge) throws IOException{
		Document doc=new Document();
		doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_ID,knowledge.getId(),Store.YES,Index.NOT_ANALYZED));
		doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_ITEMID,String.valueOf(knowledge.getItemId()),Store.YES,Index.NOT_ANALYZED));
		if(!StringUtils.isNullOrEmpty(knowledge.getTitle())){
			doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_TITLE,knowledge.getTitle().toLowerCase(),Store.YES,Index.ANALYZED));
		}
		if(!StringUtils.isNullOrEmpty(knowledge.getContent())){
			doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_CONTENT,knowledge.getContent().toLowerCase(),Store.YES,Index.ANALYZED));
		}
		if(!StringUtils.isNullOrEmpty(knowledge.getCreator())){
			doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_CREATOR,knowledge.getCreator().toLowerCase(),Store.YES,Index.NOT_ANALYZED));
		}
		if(!StringUtils.isNullOrEmpty(knowledge.getSubjectId())){
			doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_SUBJECT_ID,knowledge.getSubjectId().toLowerCase(),Store.YES,Index.NOT_ANALYZED));
		}
		if(!StringUtils.isNullOrEmpty(knowledge.getSubjectName())){
			doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_SUBJECT_NAME,knowledge.getSubjectName().toLowerCase(),Store.YES,Index.ANALYZED));
		}
		if(!StringUtils.isNullOrEmpty(knowledge.getSellerNick())){
			doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_SELLER,knowledge.getSellerNick().toLowerCase(),Store.YES,Index.NOT_ANALYZED));
		}
		doc.add(new Field(C.LUCENE_FIELD_KNOWLEDGE_SEND_NUMS,String.valueOf(knowledge.getSendNums()),Store.YES,Index.NOT_ANALYZED));
		luceneUtils.create(C.LUCENE_DIRECTORY_KNOWLEDGE_BASE, doc);
	}
	public void delete(String id)throws IOException{
		Term term=new Term(C.LUCENE_FIELD_KNOWLEDGE_ID,id);
		luceneUtils.delete(C.LUCENE_DIRECTORY_KNOWLEDGE_BASE, term);
	}
	
	public static void main(String ags[]) throws ParseException{
		String seller = "良无限Home";
		System.out.println(seller.toLowerCase());
		
	}

	/**
	 * 搜索知识库
	 * @param seller 店铺名称 为空则不做为条件
	 * @param keywords 关键字 为空则不做为条件
	 * @param itemid 商品id 大于0 商品关联知识，0 通用知识，-1 自定义知识
	 * @param creator 知识创建人 为空则不做为条件
	 * @param subjectid 知识主题 为空则不做为条件
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private List<Document> search(String seller,String keywords,Long itemid,String creator,String subjectid) throws IOException,
			ParseException {
		BooleanQuery  mainQ = new BooleanQuery();
		BooleanQuery  itemQ = new BooleanQuery();
		
		TermQuery item = new TermQuery(new Term(C.LUCENE_FIELD_KNOWLEDGE_ITEMID,String.valueOf(itemid)));
		itemQ.add(item, BooleanClause.Occur.MUST);
		
		if(seller!=null){
			TermQuery sellerQ = new TermQuery(new Term(C.LUCENE_FIELD_KNOWLEDGE_SELLER,seller.toLowerCase()));
			itemQ.add(sellerQ, BooleanClause.Occur.MUST);
		}
		
		if(!StringUtils.isNullOrEmpty(creator)){
			TermQuery creatorQ = new TermQuery(new Term(C.LUCENE_FIELD_KNOWLEDGE_CREATOR,creator.toLowerCase()));
			itemQ.add(creatorQ, BooleanClause.Occur.MUST);
		}
		
		if(!StringUtils.isNullOrEmpty(subjectid)){
			TermQuery subjectQ = new TermQuery(new Term(C.LUCENE_FIELD_KNOWLEDGE_SUBJECT_ID,subjectid.toLowerCase()));
			itemQ.add(subjectQ, BooleanClause.Occur.MUST);
		}
		
		if(keywords!=null){
			keywords = keywords.toLowerCase();
		}
		
		Query ikquery = IKQueryParser.parseMultiField(new String[]{C.LUCENE_FIELD_KNOWLEDGE_TITLE,C.LUCENE_FIELD_KNOWLEDGE_CONTENT}, keywords);
		mainQ.add(itemQ, BooleanClause.Occur.MUST);
		mainQ.add(ikquery, BooleanClause.Occur.MUST);
		
		return luceneUtils.search(C.LUCENE_DIRECTORY_KNOWLEDGE_BASE, mainQ);
		
	}

	public List<Document> search(String keywords) throws IOException,
			ParseException {
		return search(null,keywords,-1l,null,null);
	}

	public List<Document> search(String seller,String keywords, Long itemid,String creator)
			throws IOException, ParseException {
		return search(seller,keywords,itemid,creator,null);
	}
	

	public List<Document> searchBySubject(String keywords, String subjectid)
			throws IOException, ParseException {
		return search(null,keywords,-1l,null,subjectid);
	}

	@Override
	public List<Document> search(String seller, String keywords, String creator)
			throws IOException, ParseException {
		return search(seller,keywords,-1l,creator,null);
	}

	@Override
	public List<Document> searchByCondition(String seller, String keywords,
			Long itemid, String creator, String subjectid) throws IOException,
			ParseException {
		return search(seller,keywords,itemid,creator,subjectid);
	}

	@Override
	public void updateDoc(String id) throws IOException, ParseException {
		// TODO Auto-generated method stub
		TermQuery item = new TermQuery(new Term(C.LUCENE_FIELD_KNOWLEDGE_ID,id));
		List<Document> list = luceneUtils.search(C.LUCENE_DIRECTORY_KNOWLEDGE_BASE, item);
		if(list!=null&&list.size()>0){
			Document doc = list.get(0);
			Knowledge know = new Knowledge();
			know.setId(doc.get(C.LUCENE_FIELD_KNOWLEDGE_ID));
			know.setTitle(doc.get(C.LUCENE_FIELD_KNOWLEDGE_TITLE));
			know.setContent(doc.get(C.LUCENE_FIELD_KNOWLEDGE_CONTENT));
			know.setSubjectId(doc.get(C.LUCENE_FIELD_KNOWLEDGE_SUBJECT_ID));
			know.setCreator(doc.get(C.LUCENE_FIELD_KNOWLEDGE_CREATOR));
			know.setSubjectName(doc.get(C.LUCENE_FIELD_KNOWLEDGE_SUBJECT_NAME));
			know.setSendNums(Integer.parseInt(doc.get(C.LUCENE_FIELD_KNOWLEDGE_SEND_NUMS))+1);
			know.setItemId(Long.parseLong(doc.get(C.LUCENE_FIELD_KNOWLEDGE_ITEMID)));
			know.setSellerNick(doc.get(C.LUCENE_FIELD_KNOWLEDGE_SELLER));
			this.delete(id);
			this.add(know);
		}
	}
}
