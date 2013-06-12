/**
 * 
 */
package com.chamago.pcrm.worktable.connect.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.worktable.connect.mapper.SysMgtMapper;
import com.chamago.pcrm.worktable.connect.pojo.LoginLog;
import com.chamago.pcrm.worktable.connect.service.SysMgtService;
import com.mysql.jdbc.StringUtils;

/**
 * @author gavin.peng
 *
 */
@Service
public class SysMgtServiceImpl implements SysMgtService {

	@Autowired
	private SysMgtMapper  sysMgtMapper;
	
	public int insertLoginLog(LoginLog loginLog) throws Exception {
		if(loginLog == null){
			throw new Exception("新增登录日志实例为空");
		}
		return sysMgtMapper.insertLoginLog(loginLog);
	}

	public int updateLoginLogEndTime(LoginLog loginLog) throws Exception {
		if(loginLog == null){
			throw new Exception("更新登录日志实例参数loginLog为空");
		}
		return sysMgtMapper.updateLoginLogEndTime(loginLog);
	}

	public LoginLog findLoginLogByNickAndSysuser(String nick,
			String sysUser) throws Exception {
		if(nick == null){
			throw new Exception("查询参数nick为空");
		}
		if(sysUser == null){
			throw new Exception("查询参数sysUser为空");
		}
		List<LoginLog> list = sysMgtMapper.findLoginLogByNickAndSysuser(nick, sysUser);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public List<LoginLog> findLoginDetailByNickAndDate(String nick,String startDate,String endDate) throws Exception {
		
		if(nick == null){
			throw new Exception("查询参数nick为空");
		}
		if(startDate == null){
			throw new Exception("查询参数startDate为空");
		}
		if(endDate == null){
			throw new Exception("查询参数endDate为空");
		}
		return sysMgtMapper.findLoginDetailByNickAndDate(nick, startDate, endDate);
	}

	public Object[] findSysUserByName(int type,String name,String seller) throws Exception {
		if(StringUtils.isNullOrEmpty(name)||StringUtils.isNullOrEmpty(seller)){
			throw new Exception("查询用户的参数name,或者seller不能为空");
		}
		return sysMgtMapper.findSysUserByName(type,name,seller);
	}

	public List<Object[]> getMembersBySeller(String seller) {
		return sysMgtMapper.getMembersBySeller(seller);
	}

	public Object[] getUserNameAndSellerByID(String id) throws Exception {
		if(id == null){
			throw new Exception("根据用户ID查找用户名参数id为空");
		}
		return sysMgtMapper.getUserNameAndSellerByID(id);
	}

	@Override
	public Collection<String> getKeywordsFromAccokie()throws Exception {
		// TODO Auto-generated method stub
		Collection<String> accookieKeywords = new ArrayList<String>();
		Map<String,String> wdMap = new HashMap<String,String>();
		List<Object> list = sysMgtMapper.getKeywordsFromAccokie();
		if(list!=null&&list.size()>0){
			for(Object obj:list){
				String keyword = obj.toString();
				String[] kws = keyword.split(" ");
				for(String kw:kws){
					if(kw.trim().equals("")||kw.trim().equals(" ")){
						continue;
					}
					if(!wdMap.containsKey(kw)){
						accookieKeywords.add(kw);
						wdMap.put(kw, kw);
					}
				}
			}
			return accookieKeywords;
		}
		return null;
	}

	@Override
	public int getChatpeersNums(String chatId,Date startDate,Date endDate) throws Exception{
		if(chatId == null){
			throw new Exception("查询参数chatId为空");
		}
		if(startDate == null){
			throw new Exception("查询参数startDate为空");
		}
		if(endDate == null){
			throw new Exception("查询参数endDate为空");
		}
		int peerNums = sysMgtMapper.getChatpeersNums(chatId,startDate,endDate);
		return peerNums;
	}

	@Override
	public int getChatpeersNums(Map<String, Object> params) throws Exception {
		if(params==null){
			throw new Exception("查询参数params集为空");
		}
		String chatId = (String)params.get("chatId");
		Date qsDate =(Date) params.get("startDate");
		Date qeDate =(Date) params.get("endDate");
		
		return sysMgtMapper.getChatpeersNums(chatId, qsDate, qeDate);
	}

	@Override
	public int getStaffNoreplyNums(Map<String, Object> params) throws Exception {
		if(params==null){
			throw new Exception("查询参数params集为空");
		}
		String staff = (String)params.get("staff");
		Date qsDate =(Date) params.get("startDate");
		Date qeDate =(Date) params.get("endDate");
		
		return sysMgtMapper.getEveryOneNoreplyNums(staff, qsDate, qeDate);
	}

	@Override
	public int getStaffNoreplyNums(String staff, Date startDate, Date endDate)
			throws Exception {
		return sysMgtMapper.getEveryOneNoreplyNums(staff, startDate, endDate);
	}

	@Override
	public List<Object[]> getEveryOneChatpeersNums(String ids) {
		return sysMgtMapper.getEveryOneChatpeersNums(ids);
	}

	
}
