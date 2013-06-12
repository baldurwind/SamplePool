/**
 * 
 */
package com.chamago.pcrm.worktable.knowledgebase.mapper;

import java.util.List;
import java.util.Map;

import com.chamago.pcrm.worktable.knowledgebase.pojo.Knowledge;
import com.chamago.pcrm.worktable.knowledgebase.pojo.KnowledgeSubject;

/**
 * @author gavin.peng
 * 知识库持久层接口
 */
public interface KnowledgeMapper {
	
	
	/**For Lucene Index;
	 * */
	List<Knowledge> findAllKnowledge();
		
	
	/**
	 * 根据产品ID,查找相关的知识
	 * @param productId
	 *  @param sellerNick
	 * @return 对象数组
	 */
	 List<Object[]> findKnowledgeByProductId(long productId,String sellerNick) throws Exception;
	 
	 
	 /**
	 * 根据创建人查找相关的知识
	 * @param creatorid
	 *  @param sellerNick
	 * @return 对象数组
	 */
	 List<Object[]> findDefinedKnowledgeByCreatorId(String creatorid);
	 
	 
	 /**
	 * 根据主题ID,查找主题
	 * @param id 主题ID
	 * @return KnowledgeSubject
	 */
	 KnowledgeSubject findKnowledgeSubjectById(String id) ;
	 
	 
	 /**
	  * 根据关键字检索相关的知识
	  * @param keyword 关键字
	  * @return 对象数组
	  */
	 List<Object[]> searchKnowledgeByKeywords(String keyword);
	 
	 
	 
	 /**
	  * 添加知识
	  * @param knowledge
	  * @return 
	  */
	 int insertKnowledge(Knowledge knowledge);
	 
	 /**
	  * 添加用户自定义知识
	  * @param knowledge
	  * @return 
	  */
	 int insertDefinedKnowledge(Knowledge knowledge);
	 
	 /**
	  * 添加知识主题
	  * @param subject
	  * @return
	  */
	 int insertSubject(KnowledgeSubject subject);
	 
	 
	 /**
	  * 添加自定义知识主题
	  * @param subject
	  * @return
	  */
	 int insertDefinedSubject(KnowledgeSubject subject);
	 
	 
	 /**
	  * 更新知识库
	  * @param subject
	  * @return
	  */
	 int updateKnowledge(Knowledge knowledge);
	 
	 /**
	  * 根据主题ID查找相关知识
	  * @param subjectId
	  * @return
	  */
	 List<Object[]> findKnowledgeBySubjectId(String subjectId,String sellerNick);
	 
	 /**
	  * 获取主题
	  * params parentid 主题父id
	  * params sellerNick 店铺名称
	  * @return  知识主题集合 
	  */
	 List<KnowledgeSubject> getSubjectByParentid(String parentid,String sellerNick);
	 
	 
	 /**
	  * 获取主题以及相关主题个数
	  * params parentid 主题父id
	  * params sellerNick 店铺名称
	  * @return  知识主题集合 
	  */
	 List<Object[]> getSubjectAndKnowNumsByParentid(String parentid,String sellerNick);
	 
	 
	 /**
	 * 根据主题名称查主题个数
	 * @param name 主题名称
	 * @param seller 店铺名称
	 * @return 主题个数 
	 */
	int findSubjectByName(String name,String seller);
	 
	 /**
	  * 更新主题叶子节点状态
	  * @param params
	  * @return
	  */
	 int updateSubjectLeaf(Map params);
	 
	 /**
	 * 更新知识库的类型
	 * @param id 建议ID
	 * @param type 要更新的的目标类型
	 * @return
	 */
	int updateKnowledgeById(String id,String subjectId);
	
	
	 /**
	 * 更新知识库的使用次数
	 * @param id 知识库ID
	 * @param type 知识库类型 1 自定义知识，2 其他所有知识
	 * @return
	 */
	int updateKnowledgeSendNumsById(String type,String id);
	
	/**
	 * 更新知识库主题名称
	 * @param id 主题ID
	 * @param subject 新主题
	 * @return
	 */
	int updateKnowledgeSubjectById(String id,String subject);
		
	 /**
	 * 根据ids 删除知识库 ,多个ID以逗号隔开
	 * @param ids 
	 * @return
	 */
	int deleteKnowledgesByIds(String ids);
	
	/**
	 * 根据id 删除用户自定义知识库 
	 * @param ids 
	 * @return
	 */
	int deleteDefinedKnowledgesById(String id);
	
	
	 /**
	 * 根据id 删除知识库主题 
	 * @param id 知识主题ID 
	 * @return
	 */
	int deleteKnowledgeSubjectById(String id);
	
	 /**
	 * 根据id 删除自定义知识库主题 
	 * @param id 知识主题ID 
	 * @return
	 */
	int deleteDefinedKnowledgeSubjectById(String id);
}
