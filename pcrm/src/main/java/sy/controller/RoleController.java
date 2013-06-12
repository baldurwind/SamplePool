package sy.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.model.AjaxJson;
import sy.model.DataGrid;
import sy.model.DataGridJson;
import sy.model.Role;
import sy.model.ZtreeNode;
import sy.service.RoleServiceI;

/**
 * 
 * @author ben
 *
 */
@Controller
@RequestMapping("/roleController")
public class RoleController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RoleController.class);

	private RoleServiceI roleService;

	public RoleServiceI getRoleService() {
		return roleService;
	}

	@Autowired
	public void setRoleService(RoleServiceI roleService) {
		this.roleService = roleService;
	}

	@RequestMapping(params = "datagrid")
	@ResponseBody
	public DataGridJson datagrid(DataGrid dg, Role role) {
		return roleService.datagrid(dg, role);
	}

	@RequestMapping(params = "role")
	public String role() {
		return "/admin/roles";
	}

	@RequestMapping(params = "resourceTree")
	@ResponseBody
	public List<ZtreeNode> resourceTree() {
		return roleService.resourceTree();
	}

	@RequestMapping(params = "getRoleResources")
	@ResponseBody
	public AjaxJson getRoleResources(String roleId) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		j.setObj(roleService.getRoleResources(roleId));
		return j;
	}

	@RequestMapping(params = "delete")
	@ResponseBody
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		try {
			roleService.delete(ids);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping(params = "add")
	@ResponseBody
	public AjaxJson add(Role role) {
		AjaxJson j = new AjaxJson();
		try {
			Role r = roleService.add(role);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(r);
		} catch (Exception e) {
			j.setMsg("角色名称已存在或没有刷新角色权限树！");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping(params = "edit")
	@ResponseBody
	public AjaxJson edit(Role role) {
		AjaxJson j = new AjaxJson();
		try {
			Role r = roleService.edit(role);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(r);
		} catch (Exception e) {
			j.setMsg("角色名称已存在或没有刷新角色权限树！");
			e.printStackTrace();
		}
		return j;
	}

}
