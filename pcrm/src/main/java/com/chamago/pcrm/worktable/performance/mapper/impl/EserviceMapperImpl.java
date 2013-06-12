/**
 * 
 */
package com.chamago.pcrm.worktable.performance.mapper.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.worktable.performance.mapper.EserviceMapper;

/**
 * @author gavin.peng
 *
 */
@SuppressWarnings("unchecked")
public class EserviceMapperImpl extends SqlSessionDaoSupport implements
		EserviceMapper {

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.performance.mapper.EserviceMapper#getReceiveNums(java.lang.String, java.lang.String, java.util.Date)
	 */
	public List<Object[]> getReceiveNums(String nick, String seller, Date day) {
		// TODO Auto-generated method stub
		Map<String,Object>  parameter = new HashMap<String,Object>();
		parameter.put("nick", nick);
		parameter.put("userid", seller);
		parameter.put("date", day);
		return getSqlSession().selectList("EserviceMapper.findReceiveNumData", parameter);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.performance.mapper.EserviceMapper#getResponseTime(java.lang.String, java.lang.String, java.util.Date)
	 */
	public List<Object[]> getResponseTime(String nick, String seller, Date day) {
		// TODO Auto-generated method stub
		Map<String,Object>  parameter = new HashMap<String,Object>();
		parameter.put("nick", nick);
		parameter.put("staffid", seller);
		parameter.put("date", day);
		return getSqlSession().selectList("EserviceMapper.findResponseTimeData", parameter);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.performance.mapper.EserviceMapper#getServiceTransform(java.lang.String, java.lang.String, java.util.Date)
	 */
	public List<Object[]> getServiceTransform(String nick, String seller,
			Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object[]> getCustomMemeberList() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("EserviceMapper.findNickMemberList");
	}

	public List<Object[]> getGroupMemeberList(String seller) {
		// TODO Auto-generated method stub
		Assert.notNull(seller);
		return getSqlSession().selectList("EserviceMapper.findGroupMembersByUserid", seller);
		
	}

	public List<Object[]> getGroupsByNick(String nick) {
		// TODO Auto-generated method stub
		Assert.notNull(nick);
		return getSqlSession().selectList("EserviceMapper.findGroupsByNick", nick);
	}

	public List<Object[]> getMembersByGroupId(String groupid) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("EserviceMapper.findMembersByGroupId", groupid);
	}

	public List<Object[]> getChatpeersList(String seller, Date date) {
		// TODO Auto-generated method stub
		Assert.notNull(seller);
		Assert.notNull(date);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userid", seller);
		map.put("date", date);
		
		return getSqlSession().selectList("EserviceMapper.findChatpeersByChatId", map);
	}
	
	/**
	 * 获取一组group下的所有客服信息
	 * @param groups 组集
	 * @return 一组group下的所有客服信息
	 */
	public List<Object[]> getMembersByGroupS(String groups) {
		return getSqlSession().selectList("EserviceMapper.getMembersByGroupS", groups.split(","));
	}

	public long getGroupIdByMember(String member) {
		Assert.notNull(member);
		Object obj = getSqlSession().selectOne("EserviceMapper.findGroupIdByUserid", member);
		if(obj!=null){
			return Long.parseLong(obj.toString());
		}
		return 0l;
	}

	@Override
	public List<Object[]> getNickTotalReceiveNums(Map<String,Object> maps) {
		Assert.notNull(maps);
		return getSqlSession().selectList("EserviceMapper.findTotalReceiveNumDataBySeller", maps);
	}

	@Override
	public List<Object[]> getSomesResponseTime(String nick, String seller,
			Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> getEveryOneReceiveNumsByDate(Map<String, Object> maps) {
		Assert.notNull(maps);
		return getSqlSession().selectList("EserviceMapper.findNickReceiveNumData", maps);
	}

	@Override
	public List<Object[]> getNickTotalNoreplyNums(Map<String, Object> maps) {
		Assert.notNull(maps);
		return getSqlSession().selectList("EserviceMapper.findTotalNoreplyNumDataBySeller", maps);
	}

	@Override
	public List<Object[]> getEveryOneNoreplyNumsByDate(Map<String, Object> maps) {
		Assert.notNull(maps);
		return getSqlSession().selectList("EserviceMapper.findEveryOneNoreplyNum", maps);
	}

	@Override
	public List<Object[]> getSellerChatRecordList(String seller) {
		Assert.notNull(seller);
		return getSqlSession().selectList("EserviceMapper.findChatRecordUrl", seller);
	}

	@Override
	public List<Object[]> getNickFromChatRecordDetailByBuyerAndChattime(Map<String,Object> maps) {
		
		Assert.notNull(maps);
		return getSqlSession().selectList("EserviceMapper.findChatRecordDetail", maps);
	}

	@Override
	public List<Object[]> getNickFromChatRecordDetailByCPTIME(String buyer,
			Date created, Date paydate) {
		Map<String,Object> mmaps = new HashMap<String,Object>();
		mmaps.put("buyer", buyer);
		mmaps.put("created", created);
		mmaps.put("paydate", paydate);
		return getSqlSession().selectList("EserviceMapper.findChatRecordDetailAfterPay", mmaps);
	}

	@Override
	public int getChatpeersNums(String chatId, Date date) {
		Assert.notNull(chatId);
		Assert.notNull(date);
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("chat_id", chatId);
		maps.put("date", date);
		Object peerNums = getSqlSession().selectOne("EserviceMapper.getChatpeersNumsByChatId", maps);
		return peerNums==null? 0:Integer.parseInt(peerNums.toString());
	}

}
