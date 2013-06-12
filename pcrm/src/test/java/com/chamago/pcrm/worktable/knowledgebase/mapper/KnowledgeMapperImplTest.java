package com.chamago.pcrm.worktable.knowledgebase.mapper;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.worktable.knowledgebase.pojo.Knowledge;
import com.chamago.pcrm.worktable.knowledgebase.pojo.KnowledgeSubject;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})  
public class KnowledgeMapperImplTest {
	
	@Autowired
	private KnowledgeMapper knowledgeMapper;
	
	@Test
	public void testFindKnowledgeByProductId(){
		try{
		List<Object[]> results = knowledgeMapper.findKnowledgeByProductId(201l,"良无限Home");
		for(Object obj:results){
			System.out.println(obj.toString());
		}
		Assert.assertEquals(1, 1==results.size());
		}catch(Exception e){
			
		}
		
	}
	
	@Test
	public void testFindKnowledgeBySubjectId(){
		
		List<Object[]> results = knowledgeMapper.findKnowledgeBySubjectId("4f4ef12a76cf255a4bcb108d","良无限Home");
		for(Object obj:results){
			System.out.println(obj.toString());
		}
		Assert.assertEquals(1, 1==results.size());
		
		
	}
	
	@Test
	public void testInsertKnowledge(){
		Knowledge test = new Knowledge();
		test.setId(ObjectId.get().toString());
		
		test.setContent("茶马古道介绍 ");
		test.setTitle("公司");
		test.setItemId(100001l);
		test.setSubjectId(ObjectId.get().toString());
		
		test.setCreator("1001");
		test.setCreated(new Date());
		test.setModified(new Date());
		
		int rs = knowledgeMapper.insertKnowledge(test);
		System.out.println("======================"+rs);
		//Assert.assertEquals(-1, rs==1);
		
		Knowledge nukl = null;
		int rss = knowledgeMapper.insertKnowledge(nukl);
		
		
	}
	
	@Test
	public void testInsertSubject(){
		KnowledgeSubject subject = new KnowledgeSubject();
		subject.setId(ObjectId.get().toString());
		subject.setSubject("推广");
		subject.setParentId(ObjectId.get().toString());
		subject.setModified(new Date());
		subject.setCreated(new Date());
		subject.setCreator("1001");
		
		int rs = knowledgeMapper.insertSubject(subject);
		System.out.println("======================"+rs);
		//Assert.assertEquals(-1, rs==1);
		
		KnowledgeSubject nusubject = null;
		knowledgeMapper.insertSubject(nusubject);
		
		
	}

}
