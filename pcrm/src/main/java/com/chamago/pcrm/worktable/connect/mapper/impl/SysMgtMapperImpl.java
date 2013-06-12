/**
 * 
 */
package com.chamago.pcrm.worktable.connect.mapper.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.Assert;

import com.chamago.pcrm.common.utils.MemCached;
import com.chamago.pcrm.worktable.connect.mapper.SysMgtMapper;
import com.chamago.pcrm.worktable.connect.pojo.LoginLog;

/**
 * @author gavin.peng
 *
 */
@SuppressWarnings("unchecked")
public class SysMgtMapperImpl extends SqlSessionDaoSupport implements
		SysMgtMapper {
	
	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.connect.mapper.LoginLogMapper#insertLoginLog(com.chamago.pcrm.worktable.connect.pojo.LoginLog)
	 */
	public int insertLoginLog(LoginLog loginLog) {
		Assert.notNull(loginLog);
		return getSqlSession().insert("SysMgtMapper.insertLoginLog", loginLog);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.connect.mapper.SysMgtMapper#updateLoginLogEndTime(com.chamago.pcrm.worktable.connect.pojo.LoginLog)
	 */
	public int updateLoginLogEndTime(LoginLog loginLog) {
		Assert.notNull(loginLog);
		return getSqlSession().insert("SysMgtMapper.updateLoginDetailEndTime", loginLog);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.connect.mapper.LoginLogMapper#findLoginLogByNickAndSysuser(java.lang.String, java.lang.String)
	 */
	public List<LoginLog> findLoginLogByNickAndSysuser(String nick,
			String sysUser) {
		Assert.notNull(nick);
		Assert.notNull(sysUser);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("nick", nick);
		map.put("sysUser", sysUser);
		return getSqlSession().selectList("SysMgtMapper.findLoginDetail", map);
		
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.connect.mapper.LoginLogMapper#findLoginDetailByNickAndDate(java.lang.String, java.util.Date)
	 */
	public List<LoginLog> findLoginDetailByNickAndDate(String nick,String startDate,String endDate) {
		Assert.notNull(nick);
		Assert.notNull(startDate);
		Assert.notNull(endDate);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("nick", nick);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return getSqlSession().selectList("SysMgtMapper.findLoginDetailByNickAndDate", map);
	}
	@MemCached
	public Object[] findSysUserByName(int type,String name,String seller) {
		Assert.notNull(name);
		Assert.notNull(seller);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", String.valueOf(type));
		map.put("name", name);
		map.put("sellerNick", seller);
		if(type==0){
			List<Object[]> list = getSqlSession().selectList("SysMgtMapper.findSysUser", map);
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
		}else{
			List<Object[]> list = getSqlSession().selectList("SysMgtMapper.findNickSysUser", map);
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
		}
		
		return null;
	}

	public List<Object[]> getMembersBySeller(String seller) {
		Assert.notNull(seller);
		return getSqlSession().selectList("SysMgtMapper.findSysUserBySeller", seller);
	}

	public Object[] findUserRoleByName(String name) {
		// TODO Auto-generated method stub
		//return getSqlSession().selectList("SysMgtMapper.findUserRoleByName", name);
		return null;
	}

	public Object[] getUserNameAndSellerByID(String id) {
		Assert.notNull(id);
		List<Object[]> list = getSqlSession().selectList("SysMgtMapper.getUserNameById", id);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Object> getKeywordsFromAccokie() {
		return getSqlSession().selectList("SysMgtMapper.findAcookieKeywords");
	}
	
	
	@Override
	public int getChatpeersNums(String chatId,Date startDate,Date endDate) {
		Assert.notNull(chatId);
		Assert.notNull(startDate);
		Assert.notNull(endDate);
		Map<String,Object> maps = new HashMap<String,Object>();
		String seller = chatId.split(":")[0];
		maps.put("seller", seller);
		maps.put("chat_id", chatId);
		maps.put("startDate", startDate);
		maps.put("endDate", endDate);
		Object peerNums = getSqlSession().selectOne("SysMgtMapper.getChatpeersNumsByChatId", maps);
		return peerNums==null? 0:Integer.parseInt(peerNums.toString());
	}

	@Override
	public int getEveryOneNoreplyNums(String staffId, Date startDate,
			Date endDate) {
		Assert.notNull(staffId);
		Assert.notNull(startDate);
		Assert.notNull(endDate);
		Map<String,Object> maps = new HashMap<String,Object>();
		String seller = staffId.split(":")[0];
		maps.put("seller", seller);
		maps.put("staffId", staffId);
		maps.put("startDate", startDate);
		maps.put("endDate", endDate);
		Object noreplyNums = getSqlSession().selectOne("SysMgtMapper.getEveryOneNoreplyNum", maps);
		return noreplyNums==null? 0:Integer.parseInt(noreplyNums.toString());
	}

	@Override
	public List<Object[]> getEveryOneChatpeersNums(String ids) {
		Assert.notNull(ids);
		List<String> list = new ArrayList<String>();
		String[] idsArr = ids.split(",");
		for(String id:idsArr){
			list.add(id);
		}
		return getSqlSession().selectList("SysMgtMapper.getChatpeersNumsByIds", list);
	}
	

}
