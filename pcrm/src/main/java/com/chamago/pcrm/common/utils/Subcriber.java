package com.chamago.pcrm.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chamago.pcrm.worktable.connect.service.SysMgtService;

import com.chamago.pcrm.common.utils.polling.MsgEventSource;
import com.chamago.pcrm.worktable.notice.service.NoticeService;
import com.chamago.pcrm.worktable.reminder.service.ReminderService;
import com.mysql.jdbc.StringUtils;

/**
 * 
 * @author ben
 *
 */
public class Subcriber {

	private static Logger logger = LoggerFactory
	.getLogger(Subcriber.class);
	
	//通知消息 
	public static String NOTICE_MSG = "NOTICE";
	//提醒消息
	public static String REMIND_MSG = "REMIND";
	
	public static String CHATPEER_MSG = "CHATPEER";
	
	public static String USERID = "p_id";
	//消息值
	public static String MSG_NUMS = "msgcount";
	
	public static Byte[] muex = new Byte[0];
	//public static Queue<Map<String,String>> queue = new ConcurrentLinkedQueue<Map<String,String>>();
	public static Map<String,ConcurrentHashMap<String,MsgEventSource>> subjectI = new HashMap<String,ConcurrentHashMap<String,MsgEventSource>>();
	
	static{
		subjectI.put(NOTICE_MSG, new ConcurrentHashMap<String,MsgEventSource>());
		subjectI.put(REMIND_MSG, new ConcurrentHashMap<String,MsgEventSource>());
		subjectI.put(CHATPEER_MSG, new ConcurrentHashMap<String,MsgEventSource>());
	}
	
	
	public static void initPullMessage(String userId){
		if(StringUtils.isNullOrEmpty(userId)){
			return ;
		}
		try {
			//TODO 以后优化未并发执行
			//加载未读通知个数
			NoticeService ns = (NoticeService)PCRMSpringApplicationContext.getApplicationContext().getBean(NoticeService.class);
			int ncount = ns.findNoReadNoticeCount(userId);
			Map<String,MsgEventSource> msgMap = Subcriber.subjectI.get(Subcriber.NOTICE_MSG);
			msgMap.put(userId, new MsgEventSource(userId,ncount));
			
			//加载未读备忘个数
			ReminderService rs = (ReminderService)PCRMSpringApplicationContext.getApplicationContext().getBean(ReminderService.class);
			int rcount = rs.getNotReminderByCurrentNick(userId);
			Map<String,MsgEventSource> rmsgMap = Subcriber.subjectI.get(Subcriber.REMIND_MSG);
			rmsgMap.put(userId, new MsgEventSource(userId,rcount));
			//加载聊天人数
			SysMgtService sms = (SysMgtService)PCRMSpringApplicationContext.getApplicationContext().getBean(SysMgtService.class);
			List<Object[]> cpList = sms.getEveryOneChatpeersNums(userId);
			if(cpList!=null&&cpList.size()>0){
				for(Object[] obj:cpList){
					String chatId = obj[0].toString();
					int chatpeerNum = obj[1]==null? 0:Integer.parseInt(obj[1].toString());
					Map<String,MsgEventSource> cpgMap = Subcriber.subjectI.get(Subcriber.CHATPEER_MSG);
					cpgMap.put(userId, new MsgEventSource(chatId,chatpeerNum));
				}
			}
			
		} catch (Exception e) {
			logger.error("初始化用户ID["+userId+"]未读消息出错");
			e.printStackTrace();
		}
	}

}
