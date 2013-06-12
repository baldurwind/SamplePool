package com.chamago.pcrm.worktable.connect.mapper;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.worktable.advice.pojo.Advice;
import com.chamago.pcrm.worktable.connect.pojo.LoginLog;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})  
public class LoginLogMapperImplTest {
	
	@Autowired
	private SysMgtMapper loginLogMapper;
	
	@Test
	public void testInsertLoginLog(){
		LoginLog a = new LoginLog();
		a.setId(ObjectId.get().toString());
		a.setNick("良无限_非洲菊");
		a.setSysUser("gavin");
		a.setStartTime(new Date());
		a.setCreated(new Date());
		a.setModified(new Date());
		loginLogMapper.insertLoginLog(a);
		
		List<LoginLog> list = loginLogMapper.findLoginLogByNickAndSysuser("良无限_非洲菊", "gavin");
		Assert.assertTrue(list.size()==1);
		
	}
	
	
	@Test
	public void testUpdateLoginLogEndTime(){
		LoginLog a = new LoginLog();
		a.setId(ObjectId.get().toString());
		a.setNick("天三雪莲");
		a.setSysUser("ccccddd");
		a.setEndTime(new Date());
		a.setCreated(new Date());
		a.setModified(new Date());
		
		int rs = loginLogMapper.updateLoginLogEndTime(a);
		System.out.println(rs);
		Assert.assertTrue(rs==1);
		
	}
	
	
	@Test
	public void testFindLoginDetailByNickAndDate() throws ParseException{
		
		String nick = "雪佛兰";
		String startTime = "2012-03-15 12:00:00";
		String endTime = "2012-03-15 23:59:59";
		List<LoginLog> list = loginLogMapper.findLoginDetailByNickAndDate(nick,startTime,endTime);
		Assert.assertTrue(list.size()==1);
		
	}
}
