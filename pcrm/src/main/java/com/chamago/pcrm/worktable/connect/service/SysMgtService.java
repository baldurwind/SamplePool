/**
 * 
 */
package com.chamago.pcrm.worktable.connect.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chamago.pcrm.worktable.connect.pojo.LoginLog;

/**
 * 
 * @author gavin.peng
 * 
 */
public interface SysMgtService {
	
	/**
	 * 新增登录日志
	 * @param loginLog
	 * @return 
	 */
	int insertLoginLog(LoginLog loginLog) throws Exception;
	
	/**
	 * 更新用户退出时间
	 * @param loginLog
	 * @return
	 */
	int updateLoginLogEndTime(LoginLog loginLog) throws Exception;
	
	
	/**
	 * 根据旺旺nick，用户，查找登录日志
	 * @param nick 旺旺id
	 * @param sysUser 淘个性用户 
	 * @return 
	 */
	LoginLog findLoginLogByNickAndSysuser(String nick,String sysUser) throws Exception;
	
	/**
	 * 根据旺旺，日期，查找该旺旺的登录日志
	 * @param nick 旺旺ID
	 * @param startDate 起始日期
	 * @param endDate 截至日期
	 * @return 登录日志集合，如果有多个取最早的一个。 
	 */
	List<LoginLog> findLoginDetailByNickAndDate(String nick,String startDate,String endDate) throws Exception;
	
	
	/**
	 * 根据用户名查找用户
	 * @param name 用户名
	 * @param seller 卖家 
	 * @param type 0 员工表,1 nick表
	 * @return 
	 */
	Object[] findSysUserByName(int type,String name,String seller) throws Exception;
	
	/**
	 * 根据用户ID查找用户名和店铺名称
	 * @param id 用户名
	 * @return 
	 */
	Object[] getUserNameAndSellerByID(String id) throws Exception;
	
	/**
	 * 取一个店铺的客服
	 * @param seller 卖家
	 * @return
	 */
	List<Object[]> getMembersBySeller(String seller);
	
	
	/**
	 * 查询用户Acookie的关键字
	 * @return
	 */
	Collection<String> getKeywordsFromAccokie() throws Exception;
	
	
	/**
	 * 取客服seller一天内的聊天对象总数
	 * @param seller 客服
	 * @param date 日期
	 * @return
	 */
	int getChatpeersNums(String chatId,Date startDate,Date endDate) throws Exception;
	
	
	/**
	 * 取客服seller某段时间内的未回复对象总数 
	 * @param params 需要的值为，是staff,startDate,endDate
	 * @return 未回复的总人数
	 */
	int getStaffNoreplyNums(String staff,Date startDate,Date endDate) throws Exception;
	
	/**
	 * 取客服seller一天内的聊天对象总数
	 * @param params 需要的值为，是chatId,startDate,endDate
	 * @return 聊天的总人数
	 */
	int getChatpeersNums(Map<String,Object> params) throws Exception;
	
	
	/**
	 * 取客服seller某段时间内的未回复对象总数 
	 * @param params 需要的值为，是staff,startDate,endDate
	 * @return 未回复的总人数
	 */
	int getStaffNoreplyNums(Map<String,Object> params) throws Exception;
	
	
	/**
	 * 查询多个客服ID当天的聊天人数,
	 * @param ids 客服ID,多个ID以逗号隔开
	 * @return
	 */
	List<Object[]> getEveryOneChatpeersNums(String ids);

}
