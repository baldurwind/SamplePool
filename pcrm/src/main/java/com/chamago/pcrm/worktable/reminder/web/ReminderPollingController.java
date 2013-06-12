package com.chamago.pcrm.worktable.reminder.web;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chamago.pcrm.common.utils.Constants;
import com.chamago.pcrm.worktable.reminder.polling.ReminderPolling;

@Scope("prototype")
@Controller
public class ReminderPollingController {
	/**
	 * 加载客服未处理备注个数
	 */
	@Autowired
	private  ReminderPolling reminderPolling;
	
	
	@RequestMapping("/reminder/loadNotReminderCount")
	public void loadNotReminderCount(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		String nick = request.getParameter("nick");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "no-cache");
		response.setCharacterEncoding("UTF-8");
		
		AsyncContext asyncContext = request.startAsync();
		reminderPolling.setAsyncContext(asyncContext);
		reminderPolling.setNick(nick);
		reminderPolling.setTimeoutMinutes(Integer.parseInt(Constants.REMINDER_TIMEOUTMINUTES));
		reminderPolling.setIntervalMinutes(Integer.parseInt(Constants.REMINDER_INTERVALMINUTES));
		asyncContext.start(reminderPolling);
		
	}
}
