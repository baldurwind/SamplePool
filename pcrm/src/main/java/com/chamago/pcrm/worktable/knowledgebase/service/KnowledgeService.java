package com.chamago.pcrm.worktable.knowledgebase.service;

import java.io.IOException;
import java.util.List;

import org.springframework.util.Assert;

import com.chamago.pcrm.worktable.knowledgebase.pojo.Knowledge;
import com.chamago.pcrm.worktable.knowledgebase.pojo.KnowledgeSubject;

/**
 * 
 * 知识库Service层接口
 * @author gavin.peng
 *
 */
public interface KnowledgeService {
	
	/**
	 * 查找指定商品相关的知识
	 * @param productId 商品ID
	 * @param sellerNick 卖家ID
	 * @return 数组
	 */
	List<Object[]> findKnowledgeByProductId(long productId,String sellerNick);
	
	
	 /**
	 * 根据创建人查找相关的知识
	 * @param creatorid
	 * @return 对象数组
	 */
	 List<Object[]> findDefinedKnowledgeByCreatorId(String creatorid) throws Exception;
	
	/**
	 * 根据关键字搜索相关的知识
	 * @param keywords 关键字
	 * @return
	 */
	 List<Knowledge> searchKnowledgeByKeywords(String seller,String keywords,Long itemid,String creator,String subjectid);
	
	
	/**
	  * 获取主题以及相关主题个数
	  * params parentid 主题父id
	  * params sellerNick 店铺名称
	  * @return  知识主题集合 
	  */
	 List<Object[]> getSubjectAndKnowNumsByParentid(String parentid,String sellerNick) throws Exception;
	
	/**
	 * 根据根据商品id搜索商品是否有促销 
	 * @param numid 商品id
	 * @return 有返回true，否则返回false
	 */
	boolean searchItemById(Long itemid);
	
	/**
	 * 新建知识
	 * @param knowledge 
	 * @return 1  成功， -1 失败 
	 */
	int insertKnowledge(Knowledge knowledge) throws Exception;
	
	
	/**
	 * 新建自定义知识
	 * @param knowledge 
	 * @return 1  成功， -1 失败 
	 */
	int insertDefinedKnowledge(Knowledge knowledge) throws Exception;
	
	
	 /**
	  * 更新知识库
	  * @param knowledge
	  * @return
	  */
	 int updateKnowledge(Knowledge knowledge) throws Exception;
	 
	 
	 /**
	 * 更新知识库的使用次数
	 * @param id 知识库ID
	 * @param type 知识库类型 1 自定义知识，2 其他所有知识
	 * @return
	 */
	int updateKnowledgeSendNumsById(String type,String id) throws Exception;
	
	/**
	 * 新增知识主题
	 * @param subject 主题
	 * @return return 1  成功， -1 失败
	 */
	int insertKnowledgeSubject(KnowledgeSubject subject);
	
	/**
	 * 新增自定义知识主题
	 * @param subject 主题
	 * @return return 1  成功， -1 失败
	 */
	int insertKnowledgeDefinedSubject(KnowledgeSubject subject) throws Exception;
	
	
	/**
	 * 获取知识主题 
	 * @return
	 */
	List<KnowledgeSubject> getSubjectByParentid(String parentid,String sellerNick);
	
	
	/**
	 * 根据主题ID找知识
	 * @param subjectId
	 * @return
	 */
	public List<Object[]> findKnowledgeBySubjectId(String subjectId,String sellerNick);
	
	
	/**
	 * 根据主题名称查主题个数
	 * @param name 主题名称
	 * @param seller 店铺名称
	 * @return 主题个数
	 */
	int findSubjectByName(String name,String seller) throws Exception;


	
	List<Object[]> findItemBySaller(String saller);
	
	
	List<Object[]> findItemById(long itemId);
	
	
	/**
	 * 批量更新知识的主题的类型
	 * @param ids 多个建议ID,以逗号隔开
	 * @param subjectId 要更新的的目标类型
	 * @return
	 */
	int batchUpdateAdviceByIds(String ids,String subjectId) throws Exception;
	
	
	 /**
	 * 根据ids 删除知识库 ,多个ID以逗号隔开
	 * @param ids 
	 * @return
	 */
	int deleteKnowledgesByIds(String ids) throws Exception;
	
	/**
	 * 根据ids 删除自定义知识库 ,多个ID以逗号隔开
	 * @param ids 
	 * @return
	 */
	int deleteDefinedKnowledgesByIds(String ids) throws Exception;
	
	
	/**
	 * 更新建议的类型
	 * @param id 建议ID
	 * @param type 要更新的的目标类型
	 * @return
	 */
	int updateKnowledgeSubjectById(String id,String subject) throws Exception;
		
	/**
	 * 根据id 删除知识库主题 
	 * @param id 知识主题ID 
	 * @return
	 */
	int deleteKnowledgeSubjectById(String id) throws Exception;
	
	
	/**
	 * 根据id 删除自定义知识库主题 
	 * @param id 知识主题ID 
	 * @return
	 */
	int deleteKnowledgeDefinedSubjectById(String id) throws Exception;
}
