package sy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.model.AjaxJson;
import sy.model.DataGrid;
import sy.model.DataGridJson;
import sy.model.Resource;
import sy.service.ResourceServiceI;

/**
 * 
 * @author ben
 *
 */
@Controller
@RequestMapping("/resourceController")
public class ResourceController extends BaseController {

	private ResourceServiceI resourceService;

	public ResourceServiceI getResourceService() {
		return resourceService;
	}

	@Autowired
	public void setResourceService(ResourceServiceI resourceService) {
		this.resourceService = resourceService;
	}

	@RequestMapping(params = "resource")
	public String resource() {
		return "/admin/resource";
	}

	@RequestMapping(params = "datagrid")
	@ResponseBody
	public DataGridJson datagrid(DataGrid dg, Resource resource) {
		return resourceService.datagrid(dg, resource);
	}

	@RequestMapping(params = "delete")
	@ResponseBody
	public AjaxJson delete(String id) {
		AjaxJson j = new AjaxJson();
		try {
			resourceService.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return j;
		
	}
	
	
	@RequestMapping(params = "add")
	public  String add(Resource res,HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
		    res.set_parentId(req.getParameter("_parentId"));
			Resource r = resourceService.add(res);
			j.setMsg("添加成功！");
			j.setObj(r);
			j.setSuccess(true);
			
		} catch (Exception e) {
			j.setMsg("资源名已存在!");
			e.printStackTrace();
		}
		
		return "redirect:/resourceController.do?resource"; 
	}

	@RequestMapping(params = "edit")
	@ResponseBody
	public AjaxJson edit(Resource res) {
		AjaxJson j = new AjaxJson();
		try {
			Resource r = resourceService.edit(res);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(r);
		} catch (Exception e) {
			j.setMsg("资源名已存在！");
			e.printStackTrace();
		}
	//	return "redirect:/resourceController.do?resource";
	return j;
	}

}
