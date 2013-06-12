/**
 * 
 */
package com.chamago.pcrm.worktable.advice.service;

import java.util.List;

import com.chamago.pcrm.worktable.advice.pojo.Advice;
import com.chamago.pcrm.worktable.advice.pojo.AdviceType;

/**
 * 建议Service层
 * @author gavin.peng
 *
 */
public interface AdviceService {

	/**
	 * 新增建议
	 * @param advice
	 * @return 成功返回 1 否则返回 0 
	 */
	int insertAdvice(Advice advice) throws Exception;
	
	
	/**
	 * 新增建议类型
	 * @param advice
	 * @return 成功返回 1 否则返回 0 
	 */
	int insertAdviceType(AdviceType adviceType) throws Exception;
	
	/**
	 * 删除建议类型
	 * @param advice
	 * @return 成功返回 1 否则返回 0 
	 */
	int delAdviceType(String id);
	
	/**
	 * 根据创建人和类型查询建议数
	 * @param creator 创建人
	 * @param status  0: 未读  1:已读未采纳 2：已读已采纳 3：以采纳未评分 4 以采纳已评分 
	 * @return 采纳建议总个数和总平分
	 */
	Object[] findGoodAdvicesByCreatorAndStauts(String creator,int status) throws Exception;
	
	
	/**
	 * 根据状态查询建议
	 * @param status 0 未读，1  已读未采纳，2 已读以采纳未评分 3 已读以采纳已评分
	 * @return 建议集合
	 */
	List<Advice> findAdvicesByStauts(int orientedType,int status) throws Exception;

	
	/**
	 * 根据id查询建议
	 * @param ids 建议id，多个用逗号隔开
	 * @return 建议集合
	 */
	List<Advice> findAdvicesByIds(String ids) throws Exception;
	
	/**
	 * 根据建议的归属类型和建议类型以及状态查询建议 
	 * @param orientedType 1 为给客服主管提的建议，2 为给运营团队的建议
	 * @param type 建议分类
	 * @param status 建议状态 -1  ：所有状态  0: 未读  1:已读未采纳 2：已读已采纳 3：以采纳未评分 4 以采纳已评分 
	 * @return list 建议列表
	 */
	List<Advice> findAdvicesByOrientedTypeAndTypeAndStauts(int orientedType,String type,int status,String sellerNick) throws Exception;
	
	
	/**
	 * 根据创建人查询建议 
	 * @param creator 创建人 
	 * @return list 建议列表
	 */
	List<Advice> findAdvicesByCreator(String creator) throws Exception;
	
	
	/**
	 * 返回所有建议类型
	 * @return
	 */
	List<Object[]> getAdviceTypeList(int orientedtype);
	
	
	/**
	 * 返回所有建议类型
	 * @return
	 */
	List<Object[]> getAdviceTypeList(String sellerNick) throws Exception;
	
	/**
	 * 根据建议类型名称查建议个数
	 * @param name 建议名称
	 * @param seller 店铺名称
	 * @return list 建议列表
	 */
	int findAdvicesTypeByName(String name,String seller) throws Exception;
	
	/**
	 * 根据ids 删除建议 ,多个ID以逗号隔开
	 * @param ids 
	 * @return
	 */
	int deleteAdvicesByIds(String ids) throws Exception;
	
	/**
	 * 更新建议的类型
	 * @param ids 多个建议ID,以逗号隔开
	 * @param type 要更新的的目标类型
	 * @return
	 */
	int updateAdviceById(String id,String type) throws Exception;
	
	
	/**
	 * 更新建议类型
	 * @param id 建议类型ID
	 * @param name 类型名称
	 * @return
	 */
	int updateAdviceTypeNameById(String id,String name) throws Exception;
	
	
	/**
	 * 批量更新建议的类型
	 * @param ids 多个建议ID,以逗号隔开
	 * @param type 要更新的的目标类型
	 * @return
	 */
	int batchUpdateAdviceByIds(String ids,String type) throws Exception;
	
	
	/**
	 * 更新建议的状态
	 * @param id 建议ID
	 * @param status 要更新的目标状态
	 * @return
	 */
	int updateAdviceStatusById(String id,int status,float score) throws Exception;
}
