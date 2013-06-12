package com.source;

import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;
import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;
import com.chamago.pcrm.common.utils.PCRMSpringApplicationContext;
import com.chamago.pcrm.worktable.connect.service.SysMgtService;

import com.chamago.pcrm.common.utils.Subcriber;
import com.chamago.pcrm.common.utils.polling.MsgEventSource;

public class AEXStocksEventPullSources {
	public static class NoticeEventPullSource extends EventPullSource {
	
	   
		public long getSleepTime() {
			return 1000;
		}
	
		public Event pullEvent() {
			Map<String,MsgEventSource> msgMap = Subcriber.subjectI.get(Subcriber.NOTICE_MSG);
			Set<String> keySet = msgMap.keySet();
			if(!keySet.isEmpty()){
				Event event = Event.createDataEvent(Subcriber.NOTICE_MSG);
				return event;
			}
			return null;
			
		}

	
	}
	
	public static class RemindEeventPullSource extends EventPullSource {
	
		/* (non-Javadoc)
		 * @see nl.justobjects.pushlet.core.EventPullSource#getSleepTime()
		 */
		@Override
		protected long getSleepTime() {
			// TODO Auto-generated method stub
			return 1000;
		}
	
		/* (non-Javadoc)
		 * @see nl.justobjects.pushlet.core.EventPullSource#pullEvent()
		 */
		@Override
		protected Event pullEvent() {
			Map<String,MsgEventSource> msgMap = Subcriber.subjectI.get(Subcriber.REMIND_MSG);
			Set<String> keySet = msgMap.keySet();
			if(!keySet.isEmpty()){
				Event event = Event.createDataEvent(Subcriber.REMIND_MSG);
				return event;
			}
			return null;
		}
	
	}
	
	
	public static class ChatpeersEventPullSource extends EventPullSource {
		
		public ChatpeersEventPullSource(){
			LoadChatpeerNumsThread lcpn = new LoadChatpeerNumsThread();
			lcpn.start();
		}
		
		@Override
		public long getSleepTime() {
				return 1000;
		}
		
		@Override
		public Event pullEvent() {
			Map<String,MsgEventSource> msgMap = Subcriber.subjectI.get(Subcriber.CHATPEER_MSG);
			Set<String> keySet = msgMap.keySet();
			if(!keySet.isEmpty()){
				Event event = Event.createDataEvent(Subcriber.CHATPEER_MSG);
				return event;
			}
			return null;
			
		}
		
		
		private class LoadChatpeerNumsThread extends Thread{
			
			public void run(){
				
				try{
					
					preperData();
					Thread.sleep(1000*60*30);
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
			public void preperData(){
				 SysMgtService sms = (SysMgtService)PCRMSpringApplicationContext.getApplicationContext().getBean(SysMgtService.class);
				 try{
						SessionManager sessionManager = SessionManager.getInstance();
						Session[] sessions = sessionManager.getSessions();
						if(sessions!=null&&sessions.length>0){
							StringBuffer ids = new StringBuffer("");
							for(Session s:sessions){
								if(ids.length()>0){
									ids.append(",");
								}
								ids.append(s.getId());
							}
							List<Object[]> cpList = sms.getEveryOneChatpeersNums(ids.toString());
							if(cpList!=null&&cpList.size()>0){
								for(Object[] obj:cpList){
									String userId = obj[0].toString();
									int chatpeerNum = obj[1]==null? 0:Integer.parseInt(obj[1].toString());
									Map<String,MsgEventSource> cpgMap = Subcriber.subjectI.get(Subcriber.CHATPEER_MSG);
									cpgMap.put(userId, new MsgEventSource(userId,chatpeerNum));
								}
							}
						}
						
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			
		}
	
	}

}