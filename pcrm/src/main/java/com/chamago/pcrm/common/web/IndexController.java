package com.chamago.pcrm.common.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sy.hbm.Syresource;
import sy.service.ResourceServiceI;
import com.chamago.pcrm.behavior.service.BehaviorService;
import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.PCRMSpringApplicationContext;
import com.chamago.pcrm.common.utils.SessionAttribute;
import com.chamago.pcrm.worktable.connect.service.SysMgtService;
import com.danga.MemCached.MemCachedClient;

@Controller
public class IndexController {
	
	private static String COUPON_MODULE = "/marketing/coupon";

	private static String ROOT_MODULE = "0";
	
	@Autowired
	BehaviorService behaviorService;

	private ResourceServiceI resourceService;

	
	public ResourceServiceI getResourceService() {
		return resourceService;
	}

	@Autowired
	public void setResourceService(ResourceServiceI resourceService) {
		this.resourceService = resourceService;
	}

	@Autowired
	private MemCachedClient mc;

	
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	@RequestMapping("/dispatcher")
	public String index(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		
		if(StringUtils.isNotEmpty(request.getParameter("seller"))){
			String tmp=request.getParameter("seller");
			String[] str=tmp.replace("cntaobao", "").split(":");
			String seller=str[0];
			
			String nick=null;
			try {
				nick=request.getParameter("nick").replace("cntaobao", "");
			} catch (NullPointerException e) {
				nick=request.getParameter("seller").replace("cntaobao", "");
			}
			request.setAttribute("seller", seller);
			request.setAttribute("nick",nick );
		}
		if(StringUtils.isNotEmpty(request.getParameter("buyer"))){
			String buyer = request.getParameter("buyer").replace("cntaobao", "");
			request.setAttribute("buyer",buyer );
		}
		
		if(StringUtils.isNotEmpty(request.getParameter("numiid")))
			request.setAttribute("numiid", request.getParameter("numiid"));
		
		if(StringUtils.isNotEmpty(request.getParameter("tid")))
			request.setAttribute("tid", request.getParameter("tid"));
		
		String nick = (String) request.getAttribute("nick");
		String seller = (String) request.getAttribute("seller");
		String buyer = (String) request.getAttribute("buyer");
		String str_numiid = (String) request.getAttribute("numiid");
		if (null != str_numiid) {
			Long numiid = Long.valueOf(str_numiid);
			//insertConsultation方法先查找是否存在，不存在再插入。
			behaviorService.insertConsultation(seller, buyer, nick, numiid);
		}
		moduleAccess(request,response);
		genernateCookie(request,response);
		String buyerLastPath = getBuyerLastPathFromCookie(request,buyer);
		if(buyerLastPath!=null){
			String[] arr = buyerLastPath.split(",");
			if(arr!=null&&arr.length>1&&!arr[1].equals("-1")){
				Cookie navCookie = new Cookie(SessionAttribute.CURR_NAV,arr[1]); 
				navCookie.setPath("/");
				navCookie.setMaxAge(60 * 60 * 24 *1);
				response.addCookie(navCookie);	
			}
		    request.setAttribute("indexPage", arr[0]);
		}else{
			request.setAttribute("indexPage", "/");
		}
		
		return "/mainframe";
		
	}
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	@RequestMapping("/")
	public String front(HttpServletRequest request) {
		//导航条切换到服务台时不需要再次做插入咨询的记录
		//String nick = (String) request.getAttribute("nick");
		//behaviorService.insertConsultation(seller, buyer, nick, numiid);
		return "/index";
	}
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	@RequestMapping("/back")
	public String back(HttpServletRequest request, String seller, String buyer,
			Long numiid, Long tid) {

		return "/back/index";
	}

	
	@RequestMapping("/shortcut")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public String shortcut() {
		return "/common/shortcut";
	}

	@RequestMapping("/saveDefaultIndex")
	public void saveDefaultIndex(HttpServletRequest request, String userinfo,
			String page) {
		mc.add("dp_" + userinfo, page);
	}
	
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	@RequestMapping("/findResourceByUserAndPath")
	public String findResourceByUserAndPath(HttpServletRequest req,
			HttpServletResponse response, String userid, String resourceUrl)
			throws Exception {

		// String requestPath = RequestUtil.getRequestPath(req);// 用户访问的资源地址
		if (resourceUrl.indexOf("?") != -1) {
			resourceUrl = resourceUrl.substring(0, resourceUrl.indexOf("?"));
		}
		List<Syresource> resources = resourceService.getAccessResourceByRequestPath(userid);
		List<Syresource> tempRes = new ArrayList<Syresource>();
		for (Syresource res : resources) {
			if (res.getUrl().startsWith(resourceUrl)) {
				tempRes.add(res);
			}
		}

		StringBuilder json = new StringBuilder("[");
		try {
			int i = 0;
			if (tempRes != null && tempRes.size() > 0) {
				for (Syresource brs : tempRes) {
					Map<String, String> param = new Hashtable<String, String>();
					param.put("resourceUrl", brs.getUrl());
					if (i > 0) {
						json.append(",");
					}
					JSONObject jsp = JSONObject.fromObject(param);
					json.append(jsp.toString());
					i++;
				}
			}
			json.append("]");
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
		return null;

	}
	
	private String getBuyerLastPathFromCookie(HttpServletRequest res,String buyer){
		Cookie[] cookies = res.getCookies();   
	    if (cookies != null) {
	    	String lastPath = null;
	        if(buyer!=null){
		        for (Cookie cookie : cookies){   
	            	try {
	            		if (buyer.equals(URLDecoder.decode(cookie.getName(),"UTF-8")))
	            			lastPath = URLDecoder.decode(cookie.getValue(),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
		        }
		        return lastPath;
	        }
	        
	     }
	   
	    return null;
	}
	
	@RequestMapping("/maintop")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public String maintop() {
		return "/common/navigation";
	}
	
	@RequestMapping("/mainfooter")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public String mainfooter() {
		return "/common/footer";
	}
	
	private void moduleAccess(HttpServletRequest request,
			HttpServletResponse response) {
		String userid = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equalsIgnoreCase(SessionAttribute.USER)) {
					userid = cookie.getValue().split(",")[0];
				}
			}
		}
		if (StringUtils.isEmpty(userid)) {
			String nick = getNickFromRequest(request);
			if (nick != null) {
				String[] str = nick.replace("cntaobao", "").split(":");
				String seller = str[0];
				userid = getUserIdSeller(nick, seller);
			}
		}
		String reqPath = request.getRequestURI();
		ResourceServiceI resourceService = (ResourceServiceI) PCRMSpringApplicationContext
				.getApplicationContext().getBean(ResourceServiceI.class);
		List<Syresource> resources = resourceService
				.getAccessResourceByRequestPath(userid);
		if (resources != null && resources.size() > 0) {
			Set<Syresource> tempRes = new HashSet<Syresource>();
			for (Syresource res : resources) {
				if (ROOT_MODULE.equals(res.getSyresource() == null ? "-1" : res
						.getSyresource().getId())
						|| COUPON_MODULE.equals(res.getUrl())) {
					tempRes.add(res);
				}
				if(res.getUrl().startsWith(reqPath)) {
					tempRes.add(res);
				}
			}

			request.setAttribute("syresources", tempRes);
		} else {
			String nick = getNickFromRequest(request);
			if (nick != null && nick.contains(":")) {
				try {
					request.setAttribute("msg",
							"亲，请联系掌柜开通权限，或拨打售后服务电话400-821-3925！");
					request.getRequestDispatcher("/authMsg.jsp").forward(
							request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	/**
	 * 取nick对应的userid
	 * 
	 * @param name
	 * @param sellerNick
	 * @return
	 */
	private String getUserIdSeller(String name, String sellerNick) {
		try {
			SysMgtService sysMgtService = (SysMgtService) PCRMSpringApplicationContext
					.getApplicationContext().getBean(SysMgtService.class);
			Object[] sysUser = sysMgtService.findSysUserByName(1, name,
					sellerNick);
			if (sysUser != null) {
				return sysUser[0].toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取nick参数
	 * 
	 * @param request
	 * @return
	 */
	public String getNickFromRequest(HttpServletRequest request) {
		String nick = request.getParameter("nick");
		String seller = request.getParameter("seller");
		if (nick == null && seller != null && seller.contains(":")) {
			nick = seller.replace("cntaobao", "");
		}
		if (nick != null) {
			nick = nick.replace("cntaobao", "");
		}
		return nick;
	}

	
	private String getValueFromCookie(HttpServletRequest res,String cookieKey) {
		Cookie[] cookies = res.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieKey.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}

		}

		return null;
	}

	private void genernateCookie(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		String nick = (String) request.getAttribute("nick");
		String seller = (String) request.getAttribute("seller");
		String cookieUserId = getValueFromCookie(request,SessionAttribute.USER);
		String userid = getUserIdSeller(nick,seller);
		if(userid==null){
			try {
				request.setAttribute("msg", "亲，用户信息不存在请联系掌柜 ，或拨打售后服务电话400-821-3925！");
				request.getRequestDispatcher("/authMsg.jsp").forward(request, response);
				return ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//如果旺旺没有下班，
		//记录当前旺旺的id到cookie，只有在第一次的时间写，后面切换买家的时候不用写
		//cookieUserId和userid相等说明是在切换,不用写cookie
		if(!userid.equals(cookieUserId)){
			Cookie cookie = new Cookie(SessionAttribute.USER, URLEncoder.encode(userid,"utf-8")); 
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24);//有效期一天
			response.addCookie(cookie);	
		}
		String buyer = (String) request.getAttribute("buyer");
		if(buyer!=null){
			buyer = buyer.replace("cntaobao", "");
			//记录当前的买家，为切换回来的时候找到他的最近访问的页面
			Cookie buyCookie = new Cookie(SessionAttribute.CURR_BUYER,URLEncoder.encode(buyer,"utf-8")); 
			buyCookie.setPath("/");
			buyCookie.setMaxAge(60 * 60 * 24);
			response.addCookie(buyCookie);
		}
	}
}
