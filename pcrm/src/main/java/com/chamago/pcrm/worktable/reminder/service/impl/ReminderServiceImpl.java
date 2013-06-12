package com.chamago.pcrm.worktable.reminder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.worktable.reminder.mapper.ReminderMapper;
import com.chamago.pcrm.worktable.reminder.pojo.Reminder;
import com.chamago.pcrm.worktable.reminder.service.ReminderService;

/**
 * 客服备忘业务接口
 * @author James.wang
 */
@Service
public class ReminderServiceImpl implements  ReminderService {
	
	//客服备忘数据库访问接口
	@Autowired
	private ReminderMapper reminderMapper;

	/**
	 * 保存客服备忘信息
	 * @param 客户备忘信息
	 * @return 影响行数
	 */
	public void saveReminder(Reminder reminder) throws Exception {
		try {
			getReminderMapper().saveReminder(reminder);
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 获取当前客服的备忘信息已完成的个数
	 * @param nick　客服昵称
	 * @return      备忘信息已完成的个数	 
	 */
	public Integer getReminderSuccessCountByNick(String nick) {
		return getReminderMapper().getReminderSuccessCountByNick(nick);
	}
	
	/**
	 * 获取当前客服的备忘信息已过期的个数
	 * @param nick　客服昵称
	 * @return      备忘信息已过期的个数
	 */
	public Integer getReminderOverdueCountByNick(String nick) {
		return getReminderMapper().getReminderOverdueCountByNick(nick);
	}
	
	/**
	 * 获取当前客服的备忘信息未开始的个数
	 * @param nick　客服昵称
	 * @return      备忘信息未开始的个数
	 */
	public Integer getReminderNoBeginCountByNick(String nick) {
		return getReminderMapper().getReminderNoBeginCountByNick(nick);
	}
	
	/**
	 * 查询当前客服的备忘信息
	 * @return  备忘信息列表
	 */
	public void queryReminderList(PageModel pageModel, String nick, String status) {
		getReminderMapper().queryReminderList(pageModel, nick, status);
	}
	
	/**
	 * 修改备忘状态
	 * @return 影响行数
	 */
	public void updateReminderStatus(String id, Integer status) throws Exception {
		try {
			getReminderMapper().updateReminderStatus(id, status);
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 *　获取当前备忘信息
	 * @param id  备忘信息标识
	 * @return    备忘信息实体 
	 */
	public Reminder getReminderById(String id) {
		return getReminderMapper().getReminderById(id);
	}
	
	/**
	 * 修改备忘信息
	 * @param 备忘信息实体
	 * @return 影响行数
	 */
	public void updateReminder(Reminder reminder) throws Exception {
		try {
			getReminderMapper().updateReminder(reminder);
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 根据条件查询客服备忘个数
	 */
	public Integer getReminderCountByParam(String nick, String status) {
		return getReminderMapper().getReminderCountByParam(nick, status);
	}
	public void setReminderMapper(ReminderMapper reminderMapper) {
		this.reminderMapper = reminderMapper;
	}

	public ReminderMapper getReminderMapper() {
		return reminderMapper;
	}

	@Override
	public Integer getNotReminderByCurrentNick(String nick) {
		return reminderMapper.getNotReminderByCurrentNick(nick);
	}
}
