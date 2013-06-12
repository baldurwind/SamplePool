package com.chamago.pcrm.worktable.reminder.mapper.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.worktable.reminder.mapper.ReminderMapper;
import com.chamago.pcrm.worktable.reminder.pojo.Reminder;

/**
 * 客户备忘数据访问实现
 * @author James.wang
 */
public class ReminderMapperImpl extends SqlSessionDaoSupport implements ReminderMapper {
	
	/**
	 * 保存客服备忘信息
	 * @param 客户备忘信息
	 * @return 影响行数
	 */
	public void saveReminder(Reminder reminder) throws Exception {
		reminder.setId(ObjectId.get().toString());
		//过滤script html style
	//	reminder.setContent(Utils.getNoHTMLString(reminder.getContent()));
		getSqlSession().insert("ReminderMapper.saveReminder", reminder);
	}
	
	/**
	 * 获取当前客服的备忘信息已完成的个数
	 * @param nick　客服昵称
	 * @return      备忘信息已完成的个数
	 */
	public Integer getReminderSuccessCountByNick(String nick) {
		return (Integer)getSqlSession().selectOne("ReminderMapper.getReminderSuccessCountByNick", nick);
	}
	
	/**
	 * 获取当前客服的备忘信息已过期的个数
	 * @param nick　客服昵称
	 * @return      备忘信息已过期的个数
	 */
	public Integer getReminderOverdueCountByNick(String nick) {
		return (Integer)getSqlSession().selectOne("ReminderMapper.getReminderOverdueCountByNick", nick);
	}
	
	/**
	 * 获取当前客服的备忘信息未开始的个数
	 * @param nick　客服昵称
	 * @return      备忘信息未开始的个数
	 */
	public Integer getReminderNoBeginCountByNick(String nick) {
		return (Integer)getSqlSession().selectOne("ReminderMapper.getReminderNoBeginCountByNick", nick);
	}
	
	/**
	 * 查询当前客服的备忘信息
	 * @param pageModel  分页组件
	 * @param nick       客服昵称
	 * @param status     信息状态       
	 * @return  备忘信息列表
	 */
	@SuppressWarnings("unchecked")
	public void queryReminderList(PageModel pageModel, String nick, String status) {
		//查询记录总数
	    Integer totalRecords = getReminderCountByParam(nick, status);
		pageModel.setTotalRecords(totalRecords);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nick", nick);
		map.put("status", status);
		map.put("start", (pageModel.getPageNo() - 1) * pageModel.getPageSize());
		map.put("end", pageModel.getPageSize());
		//查询记录数据
		pageModel.setList((List<Reminder>)getSqlSession().selectList("ReminderMapper.getReminderList", map));
	}
	
	/**
	 * 修改备忘状态
	 * @return 影响行数
	 */
	public void updateReminderStatus(String id, Integer status) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("status", status.toString());
		getSqlSession().update("ReminderMapper.updateReminderStatus", params);
	}
	
	/**
	 *　获取当前备忘信息
	 * @param id  备忘信息标识
	 * @return    备忘信息实体 
	 */
	public Reminder getReminderById(String id) {
		return (Reminder)getSqlSession().selectOne("ReminderMapper.getReminderById", id);
	}
	
	/**
	 * 修改备忘信息
	 * @param 备忘信息实体
	 * @return 影响行数
	 */
	public void updateReminder(Reminder reminder) throws Exception {
		//过滤script html style
		//reminder.setContent(Utils.getNoHTMLString(reminder.getContent()));
		getSqlSession().update("ReminderMapper.updateReminder", reminder);
	}
	
	/**
	 * 修改备忘信息
	 * @param reminder 备忘信息实体
	 */
	public void updateReminderByLeadsMemo(Reminder reminder) throws Exception {
		getSqlSession().update("ReminderMapper.updateReminderByLeadsMemo", reminder);
	}
	
	/**
	 * 根据条件查询客服备忘个数
	 */
	public Integer getReminderCountByParam(String nick, String status) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("nick", nick);
		map.put("status", status);
		return (Integer)getSqlSession().selectOne("ReminderMapper.getReminderCountByParams", map);
	}
	
	/**
	 * 获取客户当前时间需处理的数目
	 * @param nick　客服昵称
	 * @return      　　客服未处理的数目　
	 */
	public Integer getNotReminderByCurrentNick(String nick) {
		Map<String, String> map = new HashMap<String, String>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 1);
		map.put("nick", nick);
		map.put("currentDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
		return (Integer)getSqlSession().selectOne("ReminderMapper.getNotReminderByCurrentNick", map);
	}
}
