package com.chamago.pcrm.user.mapper;

import java.util.Map;

import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.user.pojo.User;

/**
 * 用户管理数据库访问接口
 * @author James.wang
 *
 */
public interface UserMapper {
	
	/**
	 * 保存用户
	 * @param user 用户信息 
	 */ 
	public void saveUser(User user);
	
	/**
	 * 修改用户信息
	 * @param user 用户信息
	 */
	public void updateUser(User user);
	
	/**
	 * 删除用户信息
	 * @param id 用户标识
	 * @param status 用户状态
	 */
	public void updateUserStatus(String id, String status);
	
	/**
	 * 分页获取用户信息
	 * @param pageModel 分页信息实体
	 * @param param     查询参数 
	 */
	public void loadUserList(PageModel pageModel, Map<String, Object> param);
	
	/**
	 * 获取当前员工号 是否已用
	 * @param employeeNum 员工号
	 * @param id  用户标识
	 * @return    使用个数
	 */
	public Integer getEmployeeNumCount(String employeeNum, String id, String sellerNick);
	
	/**
	 * 用户员工详细信息
	 * @param id  员工编号
	 * @return    员工信息详细
	 */
	public User getUserById(String id);
}
