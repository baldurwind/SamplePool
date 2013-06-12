package sy.controller;

import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chamago.pcrm.common.utils.SessionAttribute;
import com.mysql.jdbc.StringUtils;

import sy.model.AjaxJson;
import sy.model.DataGrid;
import sy.model.DataGridJson;
import sy.model.SessionInfo;
import sy.model.User;
import sy.service.UserServiceI;
import sy.util.IpUtil;
import sy.util.RSACoder;
import sy.util.ResourceUtil;


/**
 * 
 * @author ben
 *
 */
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserController.class);
	private static final String ADMIN_COOKIE = "adminUserInfo";
	private UserServiceI userService;

	private String publicKey;

	private String privateKey;

	public UserServiceI getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	@RequestMapping(params = "datagrid")
	@ResponseBody
	public DataGridJson datagrid(DataGrid dg, User user) {
		return userService.datagrid(dg, user);
	}

	@RequestMapping(params = "roleDatagrid")
	@ResponseBody
	public DataGridJson roleDatagrid(DataGrid dg) {
		return userService.roleDatagrid(dg);
	}

	@RequestMapping(params = "loginDatagrid")
	@ResponseBody
	public DataGridJson loginDatagrid(DataGrid dg, User user) {
		return userService.datagrid(dg, user);
	}

	@RequestMapping(params = "loginCombobox")
	@ResponseBody
	public List<User> loginCombobox(String q) {
		return userService.combobox(q);
	}

	@RequestMapping(params = "combobox")
	@ResponseBody
	public List<User> combobox(String q) {
		return userService.combobox(q);
	}

	@RequestMapping(params = "reg")
	@ResponseBody
	public AjaxJson reg(User user) {
		AjaxJson j = new AjaxJson();
		try {
			User u = userService.add(user);
			j.setSuccess(true);
			j.setMsg("注册成功！");
		} catch (Exception e) {
			j.setMsg("用户名已存在！");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping(params = "add")
	@ResponseBody
	public AjaxJson add(User user,HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			
			User u = userService.add(user);
			
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(u);
		} catch (Exception e) {
			j.setMsg("用户名已存在或没有刷新用户角色列表！");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping(params = "edit")
	@ResponseBody
	public AjaxJson edit(User user) {
		AjaxJson j = new AjaxJson();
		try {
			User u = userService.edit(user);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(u);
		} catch (Exception e) {
			j.setMsg("用户名已存在或没有刷新用户角色列表！");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping(params = "modifyPassword")
	@ResponseBody
	public AjaxJson modifyPassword(User user) {
		AjaxJson j = new AjaxJson();
		if (userService.modifyPassword(user)) {
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} else {
			j.setMsg("原密码错误！");
		}
		return j;
	}  

	@RequestMapping(params = "delete")
	@ResponseBody
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		try {
			userService.delete(ids);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping(params = "login")
	public String login(User user, HttpServletRequest request, HttpSession session) throws Exception {
		AjaxJson j = new AjaxJson();
		//int length = en_result;
		//byte[] en_result = new BigInteger(user.getPassword(), 16).toByteArray();
		String userId = getUserIdFromCookie(request);
		if(userId!=null){
			request.setAttribute("sellernick", user.getSellernick());
			return "/admin/perindex";
		}
		
		User u = userService.login(user);
		if (u != null) {
			u.setIp(IpUtil.getIpAddr(request));
			j.setMsg("登录成功！");
			j.setSuccess(true);
			j.setObj(u);

//			SessionInfo sessionInfo = new SessionInfo();
//			sessionInfo.setUser(u);
//			session.setAttribute(ResourceUtil.getSessionInfoName(), sessionInfo);
		} else {
			j.setMsg("用户名或密码错误！");
		}
		return "index";
	}

	@RequestMapping(params = "logout")
	@ResponseBody
	public AjaxJson logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		return j;
	}

	@RequestMapping(params = "user")
	public String user() {
		return "/admin/user";
	}

	@RequestMapping(params = "info")
	public String info() {
		return "user/info";
	}

	@RequestMapping(params = "getInfo")
	@ResponseBody
	public AjaxJson getInfo(HttpSession session, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ResourceUtil.getSessionInfoName());
		j.setSuccess(true);
		j.setObj(userService.getUser(sessionInfo.getUser().getId()));
		return j;
	}

	@RequestMapping(params = "getUserRoles")
	@ResponseBody
	public AjaxJson getUserRoles(String userId) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		j.setObj(userService.getUserRoles(userId));
		return j;
	}

	@RequestMapping(params = "modifyUsersPassword")
	@ResponseBody
	public AjaxJson modifyUsersPassword(String ids, String password) {
		AjaxJson j = new AjaxJson();
		userService.modifyUsersPassword(ids, password);
		j.setMsg("修改密码成功！");
		j.setSuccess(true);
		return j;
	}
	
	
	@RequestMapping(params = "loginindex")
	public String loginIndex(HttpServletRequest req) {
		try {
			//initKey();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    
		return "/login";
	}
	
	private void initKey()throws Exception{
		Map<String, Object> keyMap = RSACoder.initKey();

		publicKey = RSACoder.getPublicKey(keyMap);
		privateKey = RSACoder.getPrivateKey(keyMap);
	}

	
	private String getUserIdFromCookie(HttpServletRequest res){
		String userid = null;
		Cookie[] cookies = res.getCookies();   
	    if (cookies != null) {   
	        for (Cookie cookie : cookies) {   
	            if (ADMIN_COOKIE.equals(cookie.getName()) || SessionAttribute.USER.equals(cookie.getName())) { 
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
