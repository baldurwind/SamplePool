package com.chamago.pcrm.worktable.reminder.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.common.utils.Subcriber;
import com.chamago.pcrm.common.utils.polling.MsgEventSource;
import com.chamago.pcrm.worktable.reminder.pojo.Reminder;
import com.chamago.pcrm.worktable.reminder.service.ReminderService;

/**
 * 客服备忘
 * 
 * @author James.wang
 */
@Controller()
@RequestMapping("/reminder")
public class ReminderController {
	
	//客服备忘业务接口
	@Autowired
	private ReminderService reminderService;
	
	private Logger logger = Logger.getLogger(ReminderController.class);
	
	/**
	 * 跳转至首页
	 */
	@RequestMapping("/index")
	@AopLogModule(name=C.LOG_MODULE_REMINER,layer=C.LOG_LAYER_CONTROLLER)
	public String index(ModelMap map, HttpServletRequest request) {
		return "/reminder/index";
	}

	/**
	 * 跳转至备忘添加
	 */
	@RequestMapping("/toSave")
	@AopLogModule(name=C.LOG_MODULE_REMINER,layer=C.LOG_LAYER_CONTROLLER)
	public String toSave(ModelMap map, HttpServletRequest request) {
		//获取当前在询客户
		map.put("buyerNick", request.getParameter("buyerNick"));
		map.put("sellerNick", request.getParameter("sellerNick"));
		map.put("nick", request.getParameter("nick"));
		return "/reminder/add";
	}
	
	/**
	 * 客户备忘信息添加
	 */
	@RequestMapping("/save")
	@AopLogModule(name=C.LOG_MODULE_REMINER,layer=C.LOG_LAYER_CONTROLLER)
	public String save(@ModelAttribute("reminder") Reminder reminder, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = new HashMap<String, String>();
		String tipTime = request.getParameter("tipTime1");
		try {
			reminder.setTipTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tipTime));
		} catch(Exception e) {
			logger.error("tipTime 时间转换出错", e);
		}
		try {
			getReminderService().saveReminder(reminder);
			final String userid = reminder.getNick();
			new Thread(new Runnable(){
				public void run(){
					Map<String,MsgEventSource> msgMap = Subcriber.subjectI.get(Subcriber.REMIND_MSG);
					try {
							int count = getReminderService().getNotReminderByCurrentNick(userid);
							MsgEventSource eventSource = new MsgEventSource(userid,count);
							msgMap.put(userid, eventSource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			param.put("result", "true");
		} catch(Exception e) {
			logger.error("save saveReminder", e);
			param.put("result", "false");
		}
		
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询当前客服的备忘信息列表
	 */
	@RequestMapping("/list")
	@AopLogModule(name=C.LOG_MODULE_REMINER,layer=C.LOG_LAYER_CONTROLLER)
	public String list(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		String nick = request.getParameter("nick");
		//未开始的个数
		map.put("noBeginCount", getReminderService().getReminderCountByParam(nick, "0"));
		//已过期的个数
		map.put("overdueCount", getReminderService().getReminderCountByParam(nick, "2"));
		//已成功的个数
		map.put("successCount", getReminderService().getReminderCountByParam(nick, "1"));
		return "/reminder/list";
	}
	
	/**
	 * 加载信息状态个数
	 */
	@RequestMapping("/loadStatusCount")
	@AopLogModule(name=C.LOG_MODULE_REMINER,layer=C.LOG_LAYER_CONTROLLER)
	public String loadStatusCount(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = new HashMap<String, String>();
		try {
			String nick = request.getParameter("nick");
			String type = request.getParameter("type");
			//查询消息个数
			param.put("message", getReminderService().getReminderCountByParam(nick, type).toString());
			param.put("result", "true");
		} catch(Exception e) {
			param.put("result", "false");
		}
		
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				response.getWriter().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 查询客服备忘列表
	 */
	@RequestMapping("/listDetail")
	@AopLogModule(name=C.LOG_MODULE_REMINER,layer=C.LOG_LAYER_CONTROLLER)
	public String list(ModelMap map, HttpServletRequest request) {
		String nick = request.getParameter("nick");
		String type = request.getParameter("type");
		
		PageModel pageModel = new PageModel();
		pageModel.setPageSize(7);
		int pageNo = 1;
		if(request.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(request.getParameter("pageNo").toString());
			if(pageNo <= 0) {
				pageNo = 1;
			}
		}
		pageModel.setPageNo(pageNo);
		//当前客服的备忘信息
		getReminderService().queryReminderList(pageModel, nick, type);
		List<Reminder> reminderList = (List<Reminder>)pageModel.getList();
		for(int i = 0; i < reminderList.size(); i++) {
			Reminder reminder = reminderList.get(i);
			reminder.setDateDifferentMin(DateDifferentMin(reminder.getTipTime()));
			reminder.setDateDifferentMsg(DateDifferentTimeMsg(reminder.getTipTime()));
		}
		if(pageModel.getList().size() > 0) {
			pageModel.setList(reminderList);
			map.put("reminders", pageModel);
		}
		return "/reminder/listDetail";
	}
	
	/**
	 * 修改客服备忘状态
	 */
	@RequestMapping("/updateStatus")
	@AopLogModule(name=C.LOG_MODULE_REMINER,layer=C.LOG_LAYER_CONTROLLER)
	public String updateStatus(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = new HashMap<String, String>();
		String id = request.getParameter("id");
		final String userid = request.getParameter("userid");
		String status = request.getParameter("status");
		try {
			getReminderService().updateReminderStatus(id, Integer.parseInt(status));
			new Thread(new Runnable(){
				public void run(){
					Map<String,MsgEventSource> msgMap = Subcriber.subjectI.get(Subcriber.REMIND_MSG);
					try {
						int count = getReminderService().getNotReminderByCurrentNick(userid);
						MsgEventSource eventSource = new MsgEventSource(userid,count);
						msgMap.put(userid, eventSource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			param.put("result", "true");
		} catch(Exception e) {
			logger.debug("updateStatus updateReminderStatus", e);
			param.put("result", "false");;
		}
		
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 跳转至备忘信息修改页
	 */
	@RequestMapping("/toUpdate")
	@AopLogModule(name=C.LOG_MODULE_REMINER,layer=C.LOG_LAYER_CONTROLLER)
	public String toUpdate(ModelMap map, HttpServletRequest request) {
		String id = request.getParameter("id");
		Reminder reminder = getReminderService().getReminderById(id);
		if(reminder.getBuyerNick() == null || reminder.getBuyerNick().trim().length() == 0) {
			map.put("buyerNick", request.getParameter("buyerNick"));
		}
		map.put("reminder", reminder);
		return "/reminder/update";
	}

	/**
	 * 修改备忘信息
	 */
	@RequestMapping("/update")
	@AopLogModule(name=C.LOG_MODULE_REMINER,layer=C.LOG_LAYER_CONTROLLER)
	public String update(@ModelAttribute("reminder") Reminder reminder, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = new HashMap<String, String>();
		String tipTime = request.getParameter("tipTime1");
		try {
			reminder.setTipTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tipTime));
		} catch(Exception e) {
			logger.error("tipTime 时间转换出错", e);
		}
		try {
			getReminderService().updateReminder(reminder);
			param.put("result", "true");
		} catch(Exception e) {
			logger.error("update updateReminder", e);
			param.put("result", "false");
		}
		
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 提醒时间与当前时间相差的天数，小时数，分钟数
	private String DateDifferentTimeMsg(Date date) {
	   if(date == null) {return "";}
	   java.util.Date now = new Date();
	   long l = date.getTime() - now.getTime();
	   long day = l/(24*60*60*1000);
	   long hour = (l/(60*60*1000)-day*24);
	   long min = ((l/(60*1000))-day*24*60-hour*60);
	   long s = (l/1000-day*24*60*60-hour*60*60-min*60);

	   String msg = "";
	   if(day != 0) {
		   long day1 = day > 0 ? day : (0 - day);
		   msg = day1 + "天";
	   } else if (hour != 0) {
		   long hour1 = hour > 0 ? hour : (0 - hour);
		   msg = hour1 + "小时";
	   } else if (min != 0) {
		   long min1 = min > 0 ? min : (0 - min);
		   msg = min1 + "分钟";
	   } else if(s != 0) {
		   msg = s > 0 ? "1分钟内" : "近1分钟";
	   }
	   return msg;
	}
	
	/**
	 * 提醒时间与当前时间相差的分钟数
	 */
	private Long DateDifferentMin(Date date) {
		if(date == null) {return 0l;}
		 java.util.Date now = new Date();
		 long l = date.getTime() - now.getTime();
		 long min = (l/(60*1000));
		 return min;
	}
	
	public void setReminderService(ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	public ReminderService getReminderService() {
		return reminderService;
	}
}