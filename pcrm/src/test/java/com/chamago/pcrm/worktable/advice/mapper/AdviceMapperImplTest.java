package com.chamago.pcrm.worktable.advice.mapper;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.worktable.advice.pojo.Advice;
import com.chamago.pcrm.worktable.advice.pojo.AdviceType;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})  
public class AdviceMapperImplTest {
	
	@Autowired
	private AdviceMapper adviceMapper;
	
	@Test
	public void testInsertAdvice(){
		Advice a = new Advice();
		a.setId(ObjectId.get().toString());
		a.setTitle("测试");
		a.setContent("测试内容");
		a.setStatus(0);
		a.setOrientedType(1);
		a.setType("888");
		a.setCreated(new Date());
		a.setModified(new Date());
		a.setCreator("cntaobao良无限home:栀子花");
		
		adviceMapper.insertAdvice(a);
		
		List<Advice> list = adviceMapper.findAdvicesByCreator("cntaobao良无限home:栀子花");
		Assert.assertTrue(list.size()==4);
		
	}
	
	@Test
	public void testInsertAdviceType(){
		AdviceType at = new AdviceType();
		at.setName("团队建设");
		at.setId(ObjectId.get().toString());
		at.setCreator("三叶草");
		at.setCreated(new Date());
		at.setModified(new Date());
		
		adviceMapper.insertAdviceType(at);
		
		List<Object[]> list = adviceMapper.getAdviceTypeList(1);
		Assert.assertTrue(list.size()==10);
		
	}
	
	@Test
	public void testFindAdvicesByOrientedTypeAndTypeAndStauts(){
		int orientedType = 2;
		//已读建议
		int status = 2;
		//所有类型建议
		String type = "all";
		String nick = "良无限Home";
		List<Advice> list = adviceMapper.findAdvicesByOrientedTypeAndTypeAndStauts(orientedType, type,status,nick);
		Assert.assertTrue(list.size()==3);
		
	}
	
	@Test
	public void testDeleteAdvicesByIds(){
		try{
		String ids = "4f58686df47d00738eba7ce6,4f586a10f47d8c2374ac21a9";
		int rs = adviceMapper.deleteAdvicesByIds(ids);
		//Assert.assertTrue(rs == 1);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateAdviceById(){
		String ids = "4f5868a6f47dc78410e75928";
		String type = "6";
		int rs = adviceMapper.updateAdviceById(ids, type);
		Assert.assertTrue(rs == 1);
	}
	
	@Test
	public void testUpdateAdviceStatusById(){
		String id = "4f597b281b204d8655079d98";
		int status = 2;
		int rs = adviceMapper.updateAdviceStatusById(id, status,0F);
		
		Object[] obj= adviceMapper.findGoodAdvicesByCreatorAndStauts("stevemadden旗舰店", 2);
		Assert.assertTrue(obj.length == 1);
	}
	
	@Test
	public void findGoodAdvicesByCreatorAndStauts(){
		String creator = "stevemadden旗舰店";
		int status = 2;
		Object[] count = adviceMapper.findGoodAdvicesByCreatorAndStauts(creator, status);
		Assert.assertTrue(count.length == 2);
	}
}
