/**
 * 
 */
package com.chamago.pcrm.worktable.performance.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author gavin.peng
 *
 */
public interface EserviceMapper {
	
	/**
	 * 根据店铺，客服，日期查接待客户数 
	 * @param nick 店铺名称
	 * @param seller 客服
	 * @param day 日期
	 * @return  对象数组，数组只要个值。
	 */
	List<Object[]> getReceiveNums(String nick,String seller,Date day);
	
	
	/**
	 * 根据店铺，日期查每日店铺总接待客户数 
	 * @param seller 客服
	 * @param startDate 起始日期
	 * @param endDate 截至日期
	 * @return  接待总数。
	 */
	List<Object[]> getNickTotalReceiveNums(Map<String,Object> maps);
	
	
	/**
	 * 统计店铺每个客服每日接待客户数, maps需要的参数为
	 * @param seller 店铺名称
	 * @param qDate 查询日期
	 * @return  接待总数。
	 */
	List<Object[]> getEveryOneReceiveNumsByDate(Map<String,Object> maps);
	
	
	/**
	 * 根据店铺，日期查每日店铺总未回复数 
	 * @param seller 店铺
	 * @param startDate 起始日期
	 * @param endDate 截至日期
	 * @return 数组 日期和未回复数。
	 */
	List<Object[]> getNickTotalNoreplyNums(Map<String,Object> maps);
	
	/**
	 * 统计店铺每个客服每日未回复数, maps需要的参数为
	 * @param seller 店铺名称
	 * @param qDate 查询日期
	 * @return  数组 客户和未回复数。。
	 */
	List<Object[]> getEveryOneNoreplyNumsByDate(Map<String,Object> maps);
	
	/**
	 * 根据店铺，客服，日期查该客服人员的平均响应时间 
	 * @param nick 店铺名称
	 * @param seller 客服
	 * @param day 日期
	 * @return 对象数组，数组只要个值.
	 */ 
	List<Object[]> getResponseTime(String nick,String seller,Date day);
	
	
	/**
	 * 根据店铺，客服，日期查该客服人员的服务转化率 
	 * @param nick 店铺名称
	 * @param seller 客服
	 * @param day 日期
	 * @return 对象数组，数组只要个值.
	 */ 
	List<Object[]> getServiceTransform(String nick,String seller,Date day);
	
	/**
	 * 取客服人员
	 * @return
	 */
	List<Object[]> getCustomMemeberList();
	
	
	/**
	 * 取客服seller所在组的客服人员列表
	 * @param seller 客服
	 * @return
	 */
	List<Object[]> getGroupMemeberList(String seller);
	
	/**
	 * 取客服店铺分组
	 * @param seller 客服
	 * @return
	 */
	List<Object[]> getGroupsByNick(String nick);
	
	
	/**
	 * 取一个组的客服
	 * @param seller 客服
	 * @return
	 */
	List<Object[]> getMembersByGroupId(String groupid);
	
	/**
	 * 获取一组group下的所有客服信息
	 * @param groups 组集
	 * @return 一组group下的所有客服信息
	 */
	List<Object[]> getMembersByGroupS(String groups);
	
	/**
	 * 取客服seller一天内的聊天对象
	 * @param seller 客服
	 * @return
	 */
	List<Object[]> getChatpeersList(String seller,Date date);
	
	/**
	 * 取客服seller一天内的聊天对象总数
	 * @param seller 客服
	 * @param date 日期
	 * @return
	 */
	int getChatpeersNums(String chatId,Date date);
	
	
	/**
	 * 取客服member所属组号group_id
	 * @param member 客服
	 * @return 组id
	 */
	long getGroupIdByMember(String member);
	
	/**
	 * 根据店铺，客服，日期查该客服人员的平均响应时间 
	 * @param nick 店铺名称
	 * @param seller 客服
	 * @param day 日期
	 * @return 对象数组，数组只要个值.
	 */ 
	List<Object[]> getSomesResponseTime(String nick,String seller,Date day);
	
	
	/**
	 * 根据店铺查客服和聊天 记录下载url
	 * @param nick 店铺名称
	 * @return 对象数组，旺旺和url
	 */ 
	List<Object[]> getSellerChatRecordList(String seller);
	
	/**
	 * 根据买家和时间查询这个时间范围内的聊天对象
	 * @param buyer 买家名称  
	 * @param statusDate 订单状态变更时间
	 * @param interval 时间差 以秒为单位
	 * @return 对象数组，聊天旺旺号和时间
	 */ 
	List<Object[]> getNickFromChatRecordDetailByBuyerAndChattime(Map<String,Object> maps);
	
	/**
	 * 根据买家在订单创建时间和付款时间范围内的聊天客服
	 * @param buyer 买家名称  
	 * @param created 订单创建时间
	 * @param paydate 订单付款时间
	 * @return 对象数组，聊天旺旺号和聊天的次数
	 */ 
	List<Object[]> getNickFromChatRecordDetailByCPTIME(String buyer,Date created,Date paydate);
}
