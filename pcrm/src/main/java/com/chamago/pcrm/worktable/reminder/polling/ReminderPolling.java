package com.chamago.pcrm.worktable.reminder.polling;

import javax.servlet.AsyncContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.chamago.pcrm.worktable.reminder.mapper.ReminderMapper;

@Scope("prototype")
@Component
public class ReminderPolling implements Runnable {

	private AsyncContext ac;
	
	@Autowired
	private ReminderMapper reminderMapper;
	
	private String nick; //客服昵称
	
	private Integer timeoutMinutes;  //长连接时间(分)
	
	private Integer intervalMinutes; //循环周期(分)

	public void run() {
		/*ac.setTimeout(timeoutMinutes*1000*60);
		while(timeoutMinutes!=0) {
			try {
			
				String str="";
				Integer count = reminderMapper.getNotReminderByCurrentNick(nick);
				if(count > 0) 
					str = "<script type='text/javascript'>window.parent.updateReminder(" + count + ")</script>";
				
				ac.getResponse().getWriter().write(str);
				ac.getResponse().getWriter().flush();
				Thread.sleep(intervalMinutes*1000*60);
				timeoutMinutes--;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}*/
	}
	
	public void setAsyncContext(AsyncContext ac){
		this.ac=ac;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public void setTimeoutMinutes(Integer timeoutMinutes) {
		this.timeoutMinutes = timeoutMinutes;
	}

	public void setIntervalMinutes(Integer intervalMinutes) {
		this.intervalMinutes = intervalMinutes;
	}
}