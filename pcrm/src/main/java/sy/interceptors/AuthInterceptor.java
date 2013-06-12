package sy.interceptors;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.chamago.pcrm.common.utils.SessionAttribute;
import sy.model.Resource;
import sy.service.ResourceServiceI;
import sy.util.RequestUtil;
/**
 * 权限拦截器
 * 
 * @author 徐浩亮
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);

	private ResourceServiceI resourceService;

	public ResourceServiceI getResourceService() {
		return resourceService;
	}

	@Autowired
	public void setResourceService(ResourceServiceI resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	
	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		
		return true;
		
//		String requestPath = RequestUtil.getRequestPath(request);// 用户访问的资源地址
//
//		boolean b = false;
//		List<Resource> offResourceList = resourceService.offResourceList(); // 不需要权限验证的资源集合
//		for (Resource resource : offResourceList) {
//			if (resource.getUrl().equals(requestPath)) {
//				b = true;
//				break;
//			}
//		}
//		if (requestPath.equals("/repairController.do?repair")) {// 修复数据库
//			b = true;
//		}
//		
//		String nick = request.getParameter("nick");
//		 
//		if (nick==null || !nick.contains(":")) {// 超级管理员不需要验证权限
//				return true;
//		}
////		if (b) {
////			return true;// 当前访问资源地址是不需要验证的资源
////		}
//        if(requestPath.startsWith("/dispatcher")||requestPath.contains("resource")||requestPath.contains("app/update")||requestPath.contains("pushlet.srv")
//        		||requestPath.contains("app/test")
//        		||requestPath.contains("findResourceByUserAndPath")){
//        	return true;
//        }
//        
//        if(requestPath.contains("?")&&!requestPath.contains("Controller")){
//        	requestPath=requestPath.substring(0,requestPath.indexOf("?"));
//     	}else if(requestPath.contains("Controller")&&requestPath.contains("&")){
//     		requestPath=requestPath.substring(0,requestPath.indexOf("&"));
//     	}
//        if(requestPath.contains("knowledge")){
//        	return true;
//        }
        
//		Resource resource = resourceService.getResourceByRequestPath(requestPath);
//		if (resource == null) {// 当前访问资源地址没有在数据库中存在
////			forward("亲，请联系掌柜开通权限，或拨打售后服务电话400-821-3925！", request, response);
////			return false;
//			return true;
//		}
//		return true;
//		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
//		if (sessionInfo == null) {// 没有登录系统，或登录超时
//			forward("您没有登录或登录超时，请重新登录！", request, response);
//			return false;
//		}
//		String userid = "";
//	
//		
//		Cookie[] cookies = request.getCookies();   
//	    if (cookies != null) {   
//	        for (Cookie cookie : cookies) {
//	            if (SessionAttribute.USER.equals(cookie.getName())) { 
//	            	userid = cookie.getValue();
//	            	break;
//	            }   
//	        }
//	    }
//		if (resourceService.checkAuth(userid, requestPath)) {// 验证当前用户是否有权限访问此资源
//			return true;
//		} else {
//			forward("您没有【" + resource.getName() + "】权限！", request, response);
//			return false;
//		}

	}

	private void forward(String msg, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/authMsg.jsp").forward(request, response);
	}

}
