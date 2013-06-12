/**
 * 
 */
package com.chamago.pcrm.worktable.knowledgebase.mapper.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.Assert;

import com.chamago.pcrm.worktable.knowledgebase.mapper.KnowledgeMapper;
import com.chamago.pcrm.worktable.knowledgebase.pojo.Knowledge;
import com.chamago.pcrm.worktable.knowledgebase.pojo.KnowledgeSubject;
import com.mysql.jdbc.StringUtils;

/**
 * @author gavin.peng
 *
 */
@SuppressWarnings("unchecked")
public class KnowledgeMapperImpl extends SqlSessionDaoSupport implements
		KnowledgeMapper {

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.knowledgebase.mapper.KnowledgeMapper#findKnowledgeByProductId(long)
	 */
	public List<Object[]> findKnowledgeByProductId(long productId,String sellerNick) throws Exception {
		Assert.notNull(sellerNick);
		Map<String,Object> map = new HashMap<String ,Object>();
		map.put("productId", productId);
		map.put("sellerNick", sellerNick);
		return getSqlSession().selectList("KnowledgeMapper.findKnowledgeByProductId",map);
	}

	public int insertKnowledge(Knowledge knowledge) {
		Assert.notNull(knowledge);
		return getSqlSession().insert("KnowledgeMapper.insertKnowledge", knowledge);
	}

	public List<Object[]> searchKnowledgeByKeywords(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Knowledge> findAllKnowledge() {
		List<Object[]> list = getSqlSession().selectList("KnowledgeMapper.findAllKnowledge");
		List<Object[]> defList = getSqlSession().selectList("KnowledgeMapper.findAllDefinedKnowledge");
		if(defList!=null){
			for(Object[] def:defList){
				list.add(def);
			}
		}
		if(list!=null&&list.size()>0){
			List<Knowledge> klist = new ArrayList<Knowledge>();
			for(Object[] obj:list){
				Knowledge know = new Knowledge();
				know.setId(obj[0].toString());
				know.setTitle(obj[1].toString());
				know.setContent(obj[2].toString());
				know.setCreator(obj[3].toString());
				know.setSellerNick(obj[4].toString());
				know.setItemId(Long.parseLong(obj[5].toString()));
				know.setSendNums(Integer.parseInt(obj[6].toString()));
				if(obj.length>=9){
					know.setSubjectId(obj[7].toString());
					know.setSubjectName(obj[8].toString());
				}
				klist.add(know);
			}
			
			return klist;
		}
		return null;
	}

	public int insertSubject(KnowledgeSubject subject) {
		Assert.notNull(subject);
		return getSqlSession().insert("KnowledgeMapper.insertKnowledgeSubject", subject);
	}

	public  List<Object[]> findKnowledgeBySubjectId(String subjectId,String sellerNick) {
		Assert.notNull(subjectId);
		Map<String,String> map = new HashMap<String ,String>();
		if(!"0".equals(subjectId)){
			map.put("subjectId", subjectId);
		}
		map.put("sellerNick", sellerNick);
		return getSqlSession().selectList("KnowledgeMapper.findKnowledgeBySubjectid", map);
	}

	public List<KnowledgeSubject> getSubjectByParentid(String parentid,String sellerNick) {
		Assert.notNull(parentid);
		Assert.notNull(sellerNick);
		Map<String,String> map = new HashMap<String ,String>();
		map.put("parentId", parentid);
		map.put("sellerNick", sellerNick);
		return getSqlSession().selectList("KnowledgeMapper.findKnowledgeSubjectByParentid",map);
	}

	public int updateSubjectLeaf(Map params) {
		return getSqlSession().update("KnowledgeMapper.updateSubjectLeaf", params);
	}

	public KnowledgeSubject findKnowledgeSubjectById(String id){
		 Object obj = getSqlSession().selectOne("KnowledgeMapper.findKnowledgeSubjectById", id);
		 return obj==null? null:(KnowledgeSubject)obj;
	}

	public int deleteKnowledgesByIds(String ids) {
		Assert.notNull(ids);
		List<String> list = new ArrayList<String>();
		String[] idsArr = ids.split(",");
		for(String id:idsArr){
			list.add(id);
		}
		return getSqlSession().delete("KnowledgeMapper.deleteKnowledgeByIds", list);
	}

	public int updateKnowledgeById(String id, String subjectId) {
		Assert.notNull(id);
		Assert.notNull(subjectId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("subjectId", subjectId);
		map.put("id", id);
		return getSqlSession().update("KnowledgeMapper.updateKnowledgeSubjectIdById", map);
	}

	public int updateKnowledgeSubjectById(String id, String subject) {
		Assert.notNull(id);
		Assert.notNull(subject);
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("id", id);
		map.put("subject", subject);
		map.put("modified", new Date());
		return getSqlSession().update("KnowledgeMapper.updateKnowledgeSubjectById", map);
	}

	public int deleteKnowledgeSubjectById(String id) {
		Assert.notNull(id);
		return getSqlSession().update("KnowledgeMapper.deleteKnowledgeSubjectById", id);
	}

	public int updateKnowledge(Knowledge knowledge) {
		Assert.notNull(knowledge);
		return getSqlSession().update("KnowledgeMapper.updateKnowledgeById", knowledge);
	}

	public int findSubjectByName(String name,String seller) {
		Assert.notNull(name);
		Assert.notNull(seller);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("seller", seller);
		Object obj = getSqlSession().selectOne("KnowledgeMapper.findSubjectByName", map);
		if(obj==null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}

	@Override
	public int insertDefinedKnowledge(Knowledge knowledge) {
		Assert.notNull(knowledge);
		return getSqlSession().insert("KnowledgeMapper.insertKnowledgeDefined", knowledge);
	}

	@Override
	public int insertDefinedSubject(KnowledgeSubject subject) {
		Assert.notNull(subject);
		return getSqlSession().insert("KnowledgeMapper.insertknowledgeDefinedSubject", subject);
	}

	@Override
	public int deleteDefinedKnowledgesById(String id) {
		Assert.notNull(id);
		return getSqlSession().delete("KnowledgeMapper.deleteKnowledgeDefinedById", id);
	}

	@Override
	public int deleteDefinedKnowledgeSubjectById(String id) {
		Assert.notNull(id);
		return getSqlSession().delete("KnowledgeMapper.deleteKnowledgeDefinedSubjectById", id);
	}

	@Override
	public List<Object[]> findDefinedKnowledgeByCreatorId(String creatorid){
		Assert.notNull(creatorid);
		return getSqlSession().selectList("KnowledgeMapper.findDefinedKnowledgeByCreator", creatorid);	
	}

	@Override
	public List<Object[]> getSubjectAndKnowNumsByParentid(String parentid,
			String sellerNick) {
		Assert.notNull(parentid);
		Assert.notNull(sellerNick);
		Map<String,String> map = new HashMap<String ,String>();
		map.put("parentId", parentid);
		map.put("sellerNick", sellerNick);
		return getSqlSession().selectList("KnowledgeMapper.findSubjectAndKnowledgeCountByParentid",map);
	}

	@Override
	public int updateKnowledgeSendNumsById(String type, String id) {
		Assert.notNull(type);
		Assert.notNull(id);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		String sql = "updateKnowledgeSendNumsById";
		if("1".equals(type)){
			sql = "updateKnowledgeDefinedSendNumsById";
		}
		return getSqlSession().update(sql, map);
	}

}
