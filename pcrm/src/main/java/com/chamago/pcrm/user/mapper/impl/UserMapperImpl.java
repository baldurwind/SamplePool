package com.chamago.pcrm.user.mapper.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.user.mapper.UserMapper;
import com.chamago.pcrm.user.pojo.User;

/**
 * 用户管理数据库访问实现
 * @author James.wang
 */
public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper {
	
	/**
	 * 保存用户
	 * @param user 用户信息 
	 */ 
	public void saveUser(User user) {
		Utils.getNoHTMLString(user.getName());
		getSqlSession().insert("UserMapper.saveUser", user);
	}
	
	/**
	 * 修改用户信息
	 * @param user 用户信息
	 */
	public void updateUser(User user) {
		Utils.getNoHTMLString(user.getName());
		getSqlSession().update("UserMapper.updateUser", user);
	}
	
	/**
	 * 删除用户信息
	 * @param id 用户标识
	 */
	public void updateUserStatus(String id, String status) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("id", id);
		param.put("status", status);
		getSqlSession().update("UserMapper.updateUserStatus", param);
	}
	
	/**
	 * 分页获取用户信息
	 * @param pageModel 分页信息实体
	 * @param param     查询参数 
	 */
	@SuppressWarnings("unchecked")
	public void loadUserList(PageModel pageModel, Map<String, Object> param) {
		Integer totalRecords  = (Integer)getSqlSession().selectOne("UserMapper.loadUserListCount", param);
		pageModel.setTotalRecords(totalRecords);
		param.put("start", (pageModel.getPageNo() - 1) * pageModel.getPageSize());
		param.put("end", pageModel.getPageSize());
		pageModel.setList((List<Object[]>)getSqlSession().selectList("UserMapper.loadUserList", param));
	}
	
	/**
	 * 获取当前员工号 是否已用
	 * @param employeeNum 员工号
	 * @param id  用户标识
	 * @return    使用个数
	 */
	public Integer getEmployeeNumCount(String employeeNum, String id, String sellerNick) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("employeenum", employeeNum);
		if(null != id && id.trim().length() > 0) { 
			param.put("id", id);
		}
		param.put("sellerNick", sellerNick);
		return (Integer) getSqlSession().selectOne("UserMapper.getEmployeeNumCount", param);
	}
	
	/**
	 * 用户员工详细信息
	 * @param id  员工编号
	 * @return    员工信息详细
	 */
	public User getUserById(String id) {
		return (User) getSqlSession().selectOne("UserMapper.getUserById", id);
	}
}
