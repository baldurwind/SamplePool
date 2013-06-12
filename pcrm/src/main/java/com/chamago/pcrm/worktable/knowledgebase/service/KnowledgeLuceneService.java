package com.chamago.pcrm.worktable.knowledgebase.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;

import com.chamago.pcrm.worktable.knowledgebase.pojo.Knowledge;

public interface KnowledgeLuceneService {

	public void init()throws IOException;
	
	public  List<Document> search(Long numiid)throws IOException ,ParseException;
	
	public void  add(Knowledge knowledge) throws IOException;
	
	public void delete(String id)throws IOException;
	
	public void updateDoc(String id)throws IOException, ParseException;
	
	public List<Document> search(String keywords) throws IOException ,ParseException;
	
	public List<Document> search(String seller,String keywords,Long itemid,String creator) throws IOException ,ParseException;
	
	public List<Document> searchByCondition(String seller,String keywords,Long itemid,String creator,String subjectid) throws IOException ,ParseException;
	
	public List<Document> search(String seller,String keywords,String creator) throws IOException ,ParseException;
	
	public List<Document> search(String keywords,String subjectid) throws IOException ,ParseException;
}
