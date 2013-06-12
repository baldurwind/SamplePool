package com.chamago.pcrm.permission.login.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

@Controller
@RequestMapping("/loginservice")
public class LoginController  {

	@Autowired
	private SysMgtService sysMgtService;
	
	@RequestMapping("/login")
	public String loginSystem(ModelMap map,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		String nick = request.getParameter("nick");
		String seller = request.getParameter("seller");
		
		String userName = request.getParameter("username");
		//String password = request.getParameter("j_password");
		String cookieExpirte = request.getParameter("cookie");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			
			Object[] sysUser = sysMgtService.findSysUserByName(0,userName,seller);
			if(sysUser!=null){
				String userid = sysUser[0].toString();
				String name = sysUser[1].toString();
				//String pwd = sysUser[2].toString();
				//String epwd = PCRMEncrypt.e(password);
				boolean pass = false;
				//if(password.equals(pwd)){
					//是dispatch过来的，不记录日志
				//	pass = true;
				//}else if(epwd.equals(pwd)){
					//记录登录日志
					LoginLog ll = new LoginLog();
					ll.setId(ObjectId.get().toString());
					ll.setNick(nick);
					ll.setSysUser(userid);
					Date today = new Date();
					ll.setStartTime(today);
					ll.setCreated(today);
					ll.setModified(today);
					sysMgtService.insertLoginLog(ll);
					pass = true;
					
				//}
				//if(pass){
					//登录成功 写用户信息到cookie
					Cookie cookie = new Cookie(SessionAttribute.EMPLOYEE_INFO, userid+","+name);   
					//此cookie服务器所有页面都支持
					cookie.setPath("/");
					if(cookieExpirte!=null&&cookieExpirte.equals("save")){
						cookie.setMaxAge(-1); 
					}
					//cookie.setDomain(request.getServerName());
					cookie.setMaxAge(60 * 60 * 24 * 14);
				    response.addCookie(cookie);
				    
				    param.put("result", "true");
				    param.put("userName", name);
//				    request.setAttribute("seller", seller);
//				    request.setAttribute("nick", nick);
//				    request.setAttribute("target", "worktable");
//				    return "/worktable/temp";
				//}
				
			}else{
				param.put("result", "false");
			}
		}catch(IllegalArgumentException e){
			param.put("result", "-1");
		}catch(Exception e){
			e.printStackTrace();
			param.put("result", "-1");
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

	
	
	@RequestMapping
	public String index() {
		return "/login";
	}

	@RequestMapping("/error")
	public String toErrorPage(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		map.put("unerror", "认证失败");
		return "/login";
	}
	
	
	@RequestMapping("/getserailnumber")
	public String getSerailNumber(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		ServletOutputStream out = null;
		try{
			out = response.getOutputStream();
			SerialNumberGenerator rs=new SerialNumberGenerator();
			response.setContentType("image/jpeg");
	        response.addHeader("pragma","NO-cache");
	        response.addHeader("Cache-Control","no-cache");
	        response.addDateHeader("Expries",0);
	        BufferedImage bi = rs.randomAlphanumeric(4);
	        Cookie cookie = new Cookie(SessionAttribute.SERIAL_NUMBER,rs.getSerialNumber());  
	        cookie.setPath(request.getContextPath()+"/loginservice");
		    response.addCookie(cookie);
	        ImageIO.write(bi,"JPEG",out);
	        
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.close();
		}
		return null;
	}
	
	
	@RequestMapping("/checkserailnumber")
	public String checkSerailNumber(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		try{
			String serailNumber = request.getParameter("verCode");
			if(serailNumber!=null&&serailNumber.length()>0){
			     Cookie[] cookies = request.getCookies();  
			     boolean checkOk = false;
			     if(cookies != null){   
			        for (Cookie cookie : cookies) {   
			            if (SessionAttribute.SERIAL_NUMBER.equals(cookie.getName())) {  
			            	String oldsn = cookie.getValue();
			            	if(oldsn.equals(serailNumber.toLowerCase())){
			            		checkOk = true;
			            		break;
			            	}
			            }   
			        }   
			     }   
				
				if(checkOk){
					response.getWriter().write("{\"result\":\"yes\"}");
				}else{
					response.getWriter().write("{\"result\":\"no\"}");
				}
			}else{
				response.getWriter().write("{\"result\":\"no\"}");
			}
	        
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}
		return null;
	}


}
