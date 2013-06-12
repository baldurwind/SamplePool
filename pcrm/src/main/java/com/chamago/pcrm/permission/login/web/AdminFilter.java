/**
 * 
 */
package com.chamago.pcrm.permission.login.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import sy.hbm.Syresource;
import sy.service.ResourceServiceI;

import com.chamago.pcrm.common.utils.PCRMSpringApplicationContext;
import com.chamago.pcrm.common.utils.SessionAttribute;
import com.chamago.pcrm.common.utils.Utils;

/**
 * @author Kevin
 *
 */
@WebFilter(asyncSupported=false, filterName="adminFilter",urlPatterns="/*",servletNames="rest")
public class AdminFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		  
		
		// 过滤每次请求，获取客户端cookie   
	    HttpServletRequest res = (HttpServletRequest) request;   
	    String path = res.getRequestURI();
	    if(!path.contains("admin")){
	    	chain.doFilter(res, response);
	    	return;
	    }
	    	
	    
	    if(path!=null&&path.endsWith("error")){
	    	chain.doFilter(res, response);
	    	return;
	    }else if(path!=null&&(path.endsWith("getserailnumber")||path.endsWith("checkserailnumber"))){
	    	chain.doFilter(res, response);
	    	return;
	    }
	    else if(path!=null&&path.endsWith("admin")){
	    	chain.doFilter(res, response);
	    	return;
	    }
	    if(path!=null&&path.indexOf(".")<=0&&path.indexOf("admin")>0){
	    	
//	    	boolean cont = false;
//		    Cookie[] cookies = res.getCookies();   
//		    if (cookies != null) {   
//		        for (Cookie cookie : cookies) {   
//		            if (SessionAttribute.USER.equals(cookie.getName())||SessionAttribute.ADMIN_USER.equals(cookie.getName())) {
//		                cont = true;
//		            }   
//		        }
//		        if(!cont&&path.indexOf("login")<=0){
//		        	res.getRequestDispatcher("/WEB-INF/jsp/admin/temp.jsp").forward(res, response);
//		        	return;
//		        }
//		    }  
		    if(path.contains("top")){
		    	moduleAccess(res);
		    }
	    }
	    
	    chain.doFilter(res, response);
	    
	    
	}

	
	
	
	private void moduleAccess(HttpServletRequest request){
		String userid = "";
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equalsIgnoreCase(SessionAttribute.USER)||cookie.getName().equalsIgnoreCase(SessionAttribute.ADMIN_USER)) {
					try {
						userid = URLDecoder.decode(cookie.getValue(),"UTF-8");
						userid=userid.split(":")[0];
						
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		List<Syresource> tempRes = new ArrayList<Syresource>();
		if(!StringUtils.isEmpty(userid)){
			String reqPath =  request.getRequestURI();
			ResourceServiceI resourceService = (ResourceServiceI)PCRMSpringApplicationContext.getApplicationContext().getBean(ResourceServiceI.class);
			List<Syresource> resources = resourceService.getAccessResourceByRequestPath(userid);
			
			for (Syresource res : resources) {
                 if(res.getUrl().equals("/adviceservice/admin/index")){
                	tempRes.add(res);
                 }else if(res.getUrl().equals("/knowledge/admin/index")){
                	tempRes.add(res);
                 }else if(res.getUrl().equals("/customerService/admin/index")){
                	tempRes.add(res);
                 }else if(res.getUrl().equals("/userController?login")){
                	tempRes.add(res);
                 }else if(res.getUrl().equals("/usermanage/admin/index")){
                	tempRes.add(res);
                 }
			}
			request.setAttribute("resadmin", tempRes);
		}
		
	
	}
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	private String decodeParms(String value){
		return value;
	}
	
}
