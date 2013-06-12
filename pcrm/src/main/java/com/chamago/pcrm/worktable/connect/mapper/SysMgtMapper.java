/**
 * 
 */
package com.chamago.pcrm.worktable.connect.mapper;

import java.util.Date;
import java.util.List;

import com.chamago.pcrm.worktable.connect.pojo.LoginLog;

/**
 * 交接班日志
 * 
 * @author gavin.peng
 *
 */
public interface SysMgtMapper {
	/**
	 * 新增登录日志
	 * @param loginLog
	 * @return 
	 */
	int insertLoginLog(LoginLog loginLog);
	
	/**
	 * 更新用户退出时间
	 * @param loginLog
	 * @return
	 */
	int updateLoginLogEndTime(LoginLog loginLog);
	
	/**
	 * 根据旺旺nick，用户，查找登录日志
	 * @param nick 旺旺id
	 * @param sysUser 淘个性用户 
	 * @return 
	 */
	List<LoginLog> findLoginLogByNickAndSysuser(String nick,String sysUser);
	
	
	/**
	 * 根据用户名查找用户
	 * @param name 用户名
	 * @param seller 卖家 
	 * @return 
	 */
	Object[] findSysUserByName(int type,String name,String seller);
	
	
	/**
	 * 根据用户ID查找用户名和店铺名称
	 * @param id 用户名
	 * @return 
	 */
	Object[] getUserNameAndSellerByID(String id);
	
	/**
	 * 根据用户名查找用户
	 * @param name 用户名
	 * @return 
	 */
	Object[] findUserRoleByName(String name);
	
	
	/**
	 * 取一个店铺的客服
	 * @param seller 卖家
	 * @return
	 */
	List<Object[]> getMembersBySeller(String seller);
	
	/**
	 * 根据旺旺，日期，查找该旺旺的登录日志
	 * @param nick 旺旺ID
	 * @param startDate 起始日期
	 * @param endDate 截至日期
	 * @return 登录日志集合，如果有多个取最早的一个。 
	 */
	List<LoginLog> findLoginDetailByNickAndDate(String nick,String startDate,String endDate);
	
	/**
	 * 查询Acookie的关键字
	 * @return
	 */
	List<Object> getKeywordsFromAccokie();
	
	/**
	 * 取客服seller一天内的聊天对象总数,
	 * @param chatId 客服
	 * @param startDate 日期
	 * @param endDate
	 * @return
	 */
	int getChatpeersNums(String chatId,Date startDate,Date endDate);
	
	
	/**
	 * 取客服staffId一天内的未回复的总数
	 * @param staffId 客服
	 * @param startDate 日期
	 * @param endDate
	 * @return
	 */
	int getEveryOneNoreplyNums(String staffId,Date startDate,Date endDate);
	
	/**
	 * 查询多个客服ID当天的聊天人数,
	 * @param ids 客服ID,多个ID以逗号隔开
	 * @return
	 */
	List<Object[]> getEveryOneChatpeersNums(String ids);

}
