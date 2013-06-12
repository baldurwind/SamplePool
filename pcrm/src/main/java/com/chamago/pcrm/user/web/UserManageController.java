package com.chamago.pcrm.user.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.user.pojo.User;
import com.chamago.pcrm.user.service.UserService;

/**
 * 用户管理
 * @author James.wang
 */
@RequestMapping("usermanage/admin")
@Controller
public class UserManageController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public String index(ModelMap map, HttpServletRequest request) {
		map.put("seller", request.getParameter("seller"));
		return "/admin/user/index";
	}
	
	@RequestMapping("/tousermanageindex")
	public String toUserManageIndex(ModelMap map, HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		String seller = request.getParameter("seller");
		String name = request.getParameter("name");
		String empnum = request.getParameter("employee");
		if(null != name && name.trim().length() > 0) {
			param.put("name", name);
		}
		if(null != empnum && empnum.trim().length() > 0) {
			param.put("employeenum", empnum);
		}
		param.put("sellerNick", seller);
		PageModel pageModel = new PageModel();
		pageModel.setPageSize(10);
		int pageNo = 1;
		if(request.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(request.getParameter("pageNo").toString());
			if(pageNo <= 0) {
				pageNo = 1;
			}
		}
		pageModel.setPageNo(pageNo);
		getUserService().loadUserList(pageModel, param);
		if(pageModel.getList().size() > 0) {
			map.put("users", pageModel);
		}
		return "/admin/user/user_m_list";
	}
	
	@RequestMapping("/tosaveuser")
	public String toSaveUser(ModelMap map, HttpServletRequest request) {
		String seller = request.getParameter("seller");
		map.put("seller", seller);
		return "/admin/user/user_panel";
	}
	
	@RequestMapping("/saveuser")
	public String saveUser(@ModelAttribute("user") User user, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if(getUserService().getEmployeeNumCount(user.getEmployeeNum(), null, user.getSellerNick()) > 0) {
				map.put("result", "false");
				map.put("message", "当前员工号已存在");
			} else {
				getUserService().saveUser(user);
				map.put("result", "true");
			}
		} catch(Exception e) {
			e.printStackTrace();
			map.put("result", "false");
			map.put("message", "员工信息添加失败，请稍后重试");
		}
		
		JSONObject obj = JSONObject.fromObject(map);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/toupdateuser")
	public String toUpdateUser(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		map.put("user", userService.getUserById(id));
		return "/admin/user/user_panel";
	}

	@RequestMapping("/updateuser")
	public String updateUser(@ModelAttribute("user") User user, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if(getUserService().getEmployeeNumCount(user.getEmployeeNum(), user.getId(), user.getSellerNick()) > 0) {
				map.put("result", "false");
				map.put("message", "员工编号已存在");
			} else {
				getUserService().updateUser(user);
				map.put("result", "true");
			}
		} catch(Exception e) {
			e.printStackTrace();
			map.put("result", "false");
			map.put("message", "员工信息修改失败");
		}
		
		JSONObject obj = JSONObject.fromObject(map);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/updatestatus")
	public String updateStatus(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String id = request.getParameter("id");
			String status = request.getParameter("status");
			getUserService().updateUserStatus(id, status);
			map.put("result", "true");
		} catch(Exception e) {
			map.put("result", "false");
		}
		
		JSONObject obj = JSONObject.fromObject(map);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
