/**
 * 
 */
package com.chamago.pcrm.permission.login.web;

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
 * @author gavin.peng
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginController {
	
	@Autowired
	private SysMgtService sysMgtService;
	
	@RequestMapping("/index")
	public String toIndex(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return "/admin/index";
	}
	
	@RequestMapping("/workframe")
	public String toWorkFrame(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "/admin/workframe";
	}
	
	@RequestMapping("/error")
	public String toErrorPage(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return "/admin/error";
	}
	

	@RequestMapping("/top")
	public String toTopFrame(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nick = request.getParameter("nick");
		if(nick!=null){
			String seller = nick.split(":")[0];
			request.setAttribute("seller", seller);
			
		}
		request.setAttribute("nick", nick);
		return "/admin/common/menu";
	}
	@RequestMapping
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String seller = request.getParameter("seller");
		String nick = request.getParameter("nick");
		if(nick==null){
			throw new Exception("没有收到参数nick");
		}
		if(seller==null){
			seller = nick.split(":")[0];
		}
		try{
			String userid = nick;
			Object[] sysUser = sysMgtService.findSysUserByName(1,nick,seller);
			if(sysUser!=null){
				userid = sysUser[0].toString();
			}
			Cookie cookie = new Cookie(SessionAttribute.ADMIN_USER, URLEncoder.encode(userid,"utf-8"));   
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 14);
			response.addCookie(cookie);
			
			request.setAttribute("seller", seller);
			request.setAttribute("nick", nick);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/workframe";
	}
	
	
	@RequestMapping("/login")
	public String loginSystem(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
		
		String seller = request.getParameter("seller");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String cookieExpirte = request.getParameter("cookie");
		try{
			Object[] sysUser = sysMgtService.findSysUserByName(1,userName,seller);
			if(sysUser!=null){
				String userid = sysUser[0].toString();
				String name = sysUser[1].toString();
				String pwd = sysUser[2].toString();
				String epwd = PCRMEncrypt.e(password);
				if(epwd!=null){
					if(name.equals(name)&&pwd.equals(epwd)){
						//登录成功 写用户信息到cookie
						Cookie cookie = new Cookie(SessionAttribute.ADMIN_USER, userid);   
						//此cookie服务器所有页面都支持
						cookie.setPath("/");
						if(cookieExpirte!=null&&cookieExpirte.equals("save")){
							cookie.setMaxAge(60 * 60 * 24 * 14); 
						}
					    response.addCookie(cookie);
					    response.sendRedirect(request.getContextPath()+"/admin/workframe");
					}else{
						map.put("seller", seller);
						map.put("username", userName);
						map.put("pwderror", "密码不正确");
					}
				}else{
					map.put("seller", seller);
					map.put("username", userName);
					map.put("pwderror", "密码不正确");
				}
			}else{
				map.put("seller", seller);
				map.put("unerror", "用户名"+userName+"不存在");
			}
			
		}catch(IllegalArgumentException e){
			map.put("seller", seller);
			map.put("username", userName);
			map.put("pwderror", "密码不正确");
		}catch(Exception e){
			e.printStackTrace();
			return "/admin/login";
		}
		return "/admin/login";
		
	}

	
	@RequestMapping("/logout")
	public String logOutSystem(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		 
		try{
			//清除cookie
		    Cookie[] cookies=request.getCookies();   
		    if(cookies!=null){   
		        for(Cookie cookie:cookies){   
		            if(SessionAttribute.ADMIN_USER.equals(cookie.getName())){   
		                cookie.setValue("");   
		                cookie.setMaxAge(0); 
		                cookie.setPath("/");
		                response.addCookie(cookie);   
		            }   
		        }
		       
		    }   
		    response.sendRedirect(request.getContextPath()+"/admin/login");
		}catch(Exception e){
			e.printStackTrace();
		}

		return "/admin/login";
		
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
	        cookie.setPath(request.getContextPath()+"/admin");
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
	
	private String getUserIdFromCookie(HttpServletRequest res){
		String userid = null;
		Cookie[] cookies = res.getCookies();   
	    if (cookies != null){   
	        for (Cookie cookie : cookies) {   
	            if (SessionAttribute.USER.equals(cookie.getName())||SessionAttribute.ADMIN_USER.equals(cookie.getName())) { 
	                String userInfo = cookie.getValue();
	                if(!StringUtils.isNullOrEmpty(userInfo)){
	                	userid = userInfo.split(",")[0];
	                	break;
	                }
	            }   
	        }
	    }
	    return userid;
	}
}
