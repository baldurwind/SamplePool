/**
 * 
 */
package com.chamago.pcrm.worktable.knowledgebase.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.item.mapper.ItemMapper;
import com.chamago.pcrm.item.service.ItemLuceneService;
import com.chamago.pcrm.worktable.knowledgebase.mapper.KnowledgeMapper;
import com.chamago.pcrm.worktable.knowledgebase.pojo.Knowledge;
import com.chamago.pcrm.worktable.knowledgebase.pojo.KnowledgeSubject;
import com.chamago.pcrm.worktable.knowledgebase.service.KnowledgeLuceneService;
import com.chamago.pcrm.worktable.knowledgebase.service.KnowledgeService;
import com.mysql.jdbc.StringUtils;

/**
 * @author gavin.peng
 *
 */
@Service
public class KnowledgeServiceImpl implements KnowledgeService {

	@Autowired
	private KnowledgeMapper knowledgeMapper;
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private KnowledgeLuceneService knowledgeLuceneService; 
	
	@Autowired
	private ItemLuceneService itemLuceneService;
	
	private static int LEAF = 0;
	
	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.knowledgebase.service.KnowledgeService#findKnowledgeByProductId(long)
	 */
	public List<Object[]> findKnowledgeByProductId(long productId,String sellerNick) {
		try{
			return knowledgeMapper.findKnowledgeByProductId(productId,sellerNick);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<Knowledge> searchKnowledgeByKeywords(String seller,String keywords,Long itemid,String creator,String subjectid) {
		List<Knowledge> list = null;
		try{
			List<Document> docList = knowledgeLuceneService.searchByCondition(seller,keywords,itemid,creator,subjectid);
			if(docList!=null&&docList.size()>0){
				list = new ArrayList<Knowledge>();
				for(Document doc:docList){
					Knowledge know = new Knowledge();
					know.setId(doc.get(C.LUCENE_FIELD_KNOWLEDGE_ID));
					know.setTitle(doc.get(C.LUCENE_FIELD_KNOWLEDGE_TITLE));
					know.setContent(doc.get(C.LUCENE_FIELD_KNOWLEDGE_CONTENT));
					know.setSubjectId(doc.get(C.LUCENE_FIELD_KNOWLEDGE_SUBJECT_ID));
					know.setSubjectName(doc.get(C.LUCENE_FIELD_KNOWLEDGE_SUBJECT_NAME));
					know.setSendNums(Integer.parseInt(doc.get(C.LUCENE_FIELD_KNOWLEDGE_SEND_NUMS)));
					list.add(know); 
				}
				Collections.sort(list);
			}
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public int insertKnowledge(Knowledge knowledge) throws Exception {
		if(knowledge == null){
			throw new Exception("新增知识库 实例不能为空");
		}
		int rs = knowledgeMapper.insertKnowledge(knowledge);
		if(rs>0){
			knowledgeLuceneService.add(knowledge);
		}
		return rs;
	}

	public int insertKnowledgeSubject(KnowledgeSubject subject) {
		// TODO Auto-generated method stub
		
		int rs = knowledgeMapper.insertSubject(subject);
		if(rs >0){
			KnowledgeSubject ks = knowledgeMapper.findKnowledgeSubjectById(subject.getParentId());
			if(ks!=null&&ks.getLeaf()==LEAF){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("leaf", 1);
				params.put("id", subject.getParentId());
				rs = knowledgeMapper.updateSubjectLeaf(params);
			}
			
		}
		return rs;
	}

	public List<KnowledgeSubject> getSubjectByParentid(String parentid,String sellerNick) {
		return knowledgeMapper.getSubjectByParentid(parentid,sellerNick);
	}

	public  List<Object[]> findKnowledgeBySubjectId(String subjectId,String sellerNick) {
		return knowledgeMapper.findKnowledgeBySubjectId(subjectId,sellerNick);
	}

	public List<Object[]> findItemBySaller(String saller) {
		Assert.notNull(saller);
		return itemMapper.findItemBySeller(saller);
	}

	public int deleteKnowledgesByIds(String ids) throws Exception{
		if(StringUtils.isNullOrEmpty(ids)){
			throw new Exception("要删除知识库的参数ids不能为空");
		}
		return knowledgeMapper.deleteKnowledgesByIds(ids);
	}

	public int batchUpdateAdviceByIds(String ids, String subjectId)
			throws Exception {
		if(StringUtils.isNullOrEmpty(ids)){
			throw new Exception("要更新知识库的参数ids不能为空");
		}
		if(StringUtils.isNullOrEmpty(subjectId)){
			throw new Exception("要更新知识库的参数subjectId不能为空");
		}
		String[] idsArr = ids.split(",");
		for(String id:idsArr){
			if(id.equals("null")){
				id = null;
			}
			knowledgeMapper.updateKnowledgeById(id, subjectId);
		}
		return 1;
	}

	public int updateKnowledgeSubjectById(String id, String subject)
			throws Exception {
		if(StringUtils.isNullOrEmpty(id)){
			throw new Exception("要更新知识库主题的参数id不能为空");
		}
		if(StringUtils.isNullOrEmpty(subject)){
			throw new Exception("要更新知识库主题的参数subject不能为空");
		}
		
		knowledgeMapper.updateKnowledgeSubjectById(id, subject);
		
		return 1;
	}

	public int deleteKnowledgeSubjectById(String id) throws Exception {
		if(StringUtils.isNullOrEmpty(id)){
			throw new Exception("要删除知识库主题的参数id不能为空");
		}
		return knowledgeMapper.deleteKnowledgeSubjectById(id);
	}

	public int updateKnowledge(Knowledge knowledge) throws Exception{
		if(knowledge == null){
			throw new Exception("更新知识库 实例不能为空");
		}
		int rs = knowledgeMapper.updateKnowledge(knowledge);
		if(rs>0){
			knowledgeLuceneService.delete(knowledge.getId());
			knowledgeLuceneService.add(knowledge);
		}
		return rs;
	}

	public int findSubjectByName(String name,String seller) throws Exception {
		if(StringUtils.isNullOrEmpty(name)){
			throw new Exception("根据名称查找主题的参数name不能为空");
		}
		if(StringUtils.isNullOrEmpty(seller)){
			throw new Exception("根据店铺名称查找主题的参数seller不能为空");
		}
		return knowledgeMapper.findSubjectByName(name,seller);
	}

	public boolean searchItemById(Long itemid) {
		
		try {
			Document doc = itemLuceneService.searchItemByNumiid(itemid);
			if(doc!=null){
				String discount = doc.get(C.LUCENE_FIELD_ITEM_HAS_DISCOUNT);
				if(discount.equals("Y")){
					return true;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public int insertDefinedKnowledge(Knowledge knowledge) throws Exception {
		if(knowledge == null){
			throw new Exception("新增自定义知识库 实例不能为空");
		}
		int rs = knowledgeMapper.insertDefinedKnowledge(knowledge);
		if(rs>0){
			knowledgeLuceneService.add(knowledge);
		}
		return rs;
	}

	@Override
	public int insertKnowledgeDefinedSubject(KnowledgeSubject subject)
			throws Exception {
		if(subject == null){
			throw new Exception("新增自定义知识库主题 实例不能为空");
		}
		return knowledgeMapper.insertDefinedSubject(subject);
	}

	@Override
	public int deleteDefinedKnowledgesByIds(String ids) throws Exception {
		if(ids == null){
			throw new Exception("删除自定义知识的参数ids不能为空");
		}
		String[] idAr = ids.split(",");
		if(idAr!=null&&idAr.length>0){
			for(String id:idAr){
				knowledgeMapper.deleteDefinedKnowledgesById(id);
				knowledgeLuceneService.delete(id);
			}
		}
		return 0;
	}

	@Override
	public int deleteKnowledgeDefinedSubjectById(String id) throws Exception {
		return 0;
	}

	@Override
	public List<Object[]> findDefinedKnowledgeByCreatorId(String creatorid)
			throws Exception {
		
		if(creatorid == null){
			throw new Exception("删除自定义知识的创建人creatorid不能为空");
		}
		return knowledgeMapper.findDefinedKnowledgeByCreatorId(creatorid);
	}

	@Override
	public List<Object[]> getSubjectAndKnowNumsByParentid(String parentid,
			String sellerNick) throws Exception {
		if(StringUtils.isNullOrEmpty(parentid)){
			throw new Exception("查询主题的参数parentid不能为空");
		}
		if(StringUtils.isNullOrEmpty(sellerNick)){
			throw new Exception("查询主题的参数sellerNick不能为空");
		}
		return knowledgeMapper.getSubjectAndKnowNumsByParentid(parentid, sellerNick);
	}

	@Override
	public int updateKnowledgeSendNumsById(String type, String id)
			throws Exception {
		if(StringUtils.isNullOrEmpty(type)){
			throw new Exception("更新知识库使用次数的参数type不能为空");
		}
		if(StringUtils.isNullOrEmpty(id)){
			throw new Exception("更新知识库使用次数的参数id不能为空");
		}
		int rs = knowledgeMapper.updateKnowledgeSendNumsById(type, id);
		if(rs >0){
			//目前实时更新,以后可能定时更新
			knowledgeLuceneService.updateDoc(id);
		}
		return 0;
	}

	@Override
	public List<Object[]> findItemById(long itemId) {
		return null;
	}

}
