package com.chamago.pcrm.worktable.reminder.mapper;
import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.worktable.reminder.pojo.Reminder;

/**
 * 客户备忘数据访问接口
 * @author James.wang
 */
public interface ReminderMapper {
	
	/**
	 * 保存客服备忘信息
	 * @param 客户备忘信息
	 */
	public void saveReminder(Reminder reminder) throws Exception;
	
	/**
	 * 获取当前客服的备忘信息已完成的个数
	 * @param nick　客服昵称
	 * @return      备忘信息已完成的个数
	 */
	public Integer getReminderSuccessCountByNick(String nick);
	
	/**
	 * 获取当前客服的备忘信息已过期的个数
	 * @param nick　客服昵称
	 * @return      备忘信息已过期的个数
	 */
	public Integer getReminderOverdueCountByNick(String nick);
	
	/**
	 * 获取当前客服的备忘信息未开始的个数
	 * @param nick　客服昵称
	 * @return      备忘信息未开始的个数
	 */
	public Integer getReminderNoBeginCountByNick(String nick);
	
	/**
	 * 查询当前客服的备忘信息
	 * @param pageModel  分页组件
	 * @param nick       客服昵称
	 * @param status     信息状态       
	 * @return  备忘信息列表
	 */
	public void queryReminderList(PageModel pageModel, String nick, String status);
	
	/**
	 * 修改备忘状态
	 */
	public void updateReminderStatus(String id, Integer status) throws Exception;
	
	/**
	 *　获取当前备忘信息
	 * @param id  备忘信息标识
	 * @return    备忘信息实体 
	 */
	public Reminder getReminderById(String id);
	
	/**
	 * 修改备忘信息
	 * @param 备忘信息实体
	 * @return 影响行数
	 */
	public void updateReminder(Reminder reminder) throws Exception;
	
	/**
	 * 修改备忘信息
	 * @param reminder 备忘信息实体
	 */
	public void updateReminderByLeadsMemo(Reminder reminder) throws Exception;
	
	/**
	 * 根据条件查询客服备忘个数
	 */
	public Integer getReminderCountByParam(String nick, String status);
	
	/**
	 * 获取客户当前时间需处理的数目
	 * @param nick　客服昵称
	 * @return      　　客服未处理的数目　
	 */
	public Integer getNotReminderByCurrentNick(String nick);
}