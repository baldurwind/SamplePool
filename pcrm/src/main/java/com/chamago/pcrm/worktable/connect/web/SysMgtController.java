/**
 * 
 */
package com.chamago.pcrm.worktable.connect.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.PCRMEncrypt;
import com.chamago.pcrm.common.utils.SerialNumberGenerator;
import com.chamago.pcrm.common.utils.SessionAttribute;
import com.chamago.pcrm.worktable.connect.pojo.LoginLog;
import com.chamago.pcrm.worktable.connect.service.SysMgtService;
import com.mysql.jdbc.StringUtils;

/**
 * 登录日志Controller
 * @author gavin.peng
 *
 */
@Controller
@RequestMapping("userservice")
public class SysMgtController {
	
	private static Logger logger = LoggerFactory
	.getLogger(SysMgtController.class);
	
	@Autowired
	private SysMgtService sysMgtService;
	
	
		
	
	@RequestMapping("/logout")
	public String logOutSystem(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		 
		String nick = request.getParameter("nick");
		String sysUser = request.getParameter("sysuser");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			boolean conte = true;
			if(StringUtils.isNullOrEmpty(nick)){
				param.put("result", "-1");
				conte = false;
			}
			if(StringUtils.isNullOrEmpty(sysUser)){
				param.put("result", "-1");
				conte = false;
			}
			if(conte){
				LoginLog llg = new LoginLog();
				llg.setEndTime(new Date());
				llg.setNick(nick);
				llg.setSysUser(sysUser);
				llg.setModified(new Date());
				sysMgtService.updateLoginLogEndTime(llg);
				//清除cookie
				Cookie[] cookies=request.getCookies();   
			    if(cookies!=null){   
			        for(Cookie cookie:cookies){   
			            if("employeeInfo".equals(cookie.getName())){   
			                cookie.setValue("");   
			                cookie.setMaxAge(0); 
			                cookie.setPath("/");
			                response.addCookie(cookie);   
			            }   
			        }
			       
			    }   
			    
			    param.put("result", "true");
//			    request.setAttribute("seller", nick.split(":")[0]);
//			    request.setAttribute("nick", nick);
//			    request.setAttribute("target", "");
//			    return "/worktable/temp";
			    
			    //String targetPath = request.getContextPath();
			    //response.sendRedirect(targetPath+"?seller="+java.net.URLEncoder.encode(nick.split(":")[0],"UTF-8")+"&nick="+java.net.URLEncoder.encode(nick,"UTF-8"));
			}
			
		}catch(Exception e){
			logger.error(nick+"客服"+sysUser+"注销出错!");
			e.printStackTrace();
			param.put("result", "false");
		}
		JSONObject jsp = JSONObject.fromObject(param);
		try {
			response.getWriter().write(jsp.toString());

		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			request.getSession().invalidate();
			response.getWriter().close();
		}

		return null;
	}
	
	
	@RequestMapping("/getonlinetime")
	public String getLoginLogRecord(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String nick = request.getParameter("nick");
		String sysUser = request.getParameter("sysuser");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			boolean conte = true;
			if(StringUtils.isNullOrEmpty(nick)){
				param.put("result", "-1");
				conte = false;
			}
			if(StringUtils.isNullOrEmpty(sysUser)){
				param.put("result", "-1");
				conte = false;
			}
			if(conte){
				LoginLog llg = sysMgtService.findLoginLogByNickAndSysuser(nick, sysUser);
				if(llg!=null){
					long startTime = llg.getStartTime().getTime();
					long logoutTime = System.currentTimeMillis();
					long onlinetime = logoutTime - startTime;
					long worktime = onlinetime/(60*1000);
					if(worktime>0){
						long hourPart = worktime/60;
						long minPart = worktime%60;
						param.put("onlinehourse", String.valueOf(hourPart));
						param.put("onlineminiter", String.valueOf(minPart));
					}else{
						param.put("onlinehourse","0");
						param.put("onlineminiter", "0");
					}
					param.put("result", "true");
				}else{
					param.put("result", "norecord");
				}
				
			}
			
		}catch(Exception e){
			logger.error(nick+"客服"+sysUser+"注销出错!");
			e.printStackTrace();
			param.put("result", "false");
		}
		JSONObject jsp = JSONObject.fromObject(param);
		try {
			response.getWriter().write(jsp.toString());

		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			
			response.getWriter().close();
		}

		return null;
	}

}
