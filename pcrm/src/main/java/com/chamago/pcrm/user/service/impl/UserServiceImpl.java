package com.chamago.pcrm.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.user.mapper.UserMapper;
import com.chamago.pcrm.user.pojo.User;
import com.chamago.pcrm.user.service.UserService;

/**
 * 用户管理业务实现
 * @author James.wang
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 保存用户
	 * @param user 用户信息 
	 */ 
	public void saveUser(User user) {
		user.setId(ObjectId.get().toString());
		getUserMapper().saveUser(user);
	}
	
	/**
	 * 修改用户信息
	 * @param user 用户信息
	 */
	public void updateUser(User user) {
		getUserMapper().updateUser(user);
	}
	
	/**
	 * 删除用户信息
	 * @param id 用户标识
	 * @param status 用户状态
	 */
	public void updateUserStatus(String id, String status) {
		getUserMapper().updateUserStatus(id, status);
	}
	
	/**
	 * 分页获取用户信息
	 * @param pageModel 分页信息实体
	 * @param param     查询参数 
	 */
	public void loadUserList(PageModel pageModel, Map<String, Object> param) {
		getUserMapper().loadUserList(pageModel, param);
	}
	
	/**
	 * 获取当前员工号 是否已用
	 * @param employeeNum 员工号
	 * @param id  用户标识
	 * @return    使用个数
	 */
	public Integer getEmployeeNumCount(String employeeNum, String id, String sellerNick) {
		return getUserMapper().getEmployeeNumCount(employeeNum, id, sellerNick);
	}
	
	/**
	 * 用户员工详细信息
	 * @param id  员工编号
	 * @return    员工信息详细
	 */
	public User getUserById(String id) {
		return getUserMapper().getUserById(id);
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
}
