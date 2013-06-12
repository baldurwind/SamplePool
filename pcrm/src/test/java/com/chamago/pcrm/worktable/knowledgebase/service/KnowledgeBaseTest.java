package com.chamago.pcrm.worktable.knowledgebase.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.worktable.knowledgebase.pojo.Knowledge;
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})  
public class KnowledgeBaseTest {
	
	@Autowired
	private KnowledgeLuceneService service;
	
	
	@Test
	public void  testAll(){
		try {
			Knowledge obj=new Knowledge();
			obj.setId("iamid");
			obj.setContent("iamcontent");
			obj.setTitle("iamtitle");
			obj.setItemId(239823982323L);
			service.add(obj);
			
			List<Document> doc=service.search(obj.getItemId());
			String content=doc.get(0).get(C.LUCENE_FIELD_KNOWLEDGE_CONTENT);
			Assert.assertTrue(content.equals(obj.getContent()));
	//	service.delete(obj.getId());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
