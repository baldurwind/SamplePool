package sy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.model.AjaxJson;
import sy.service.RepairServiceI;

/**
 * 
 * @author ben
 *
 */
@Controller
@RequestMapping("/repairController")
public class RepairController extends BaseController {

	private RepairServiceI repairService;

	public RepairServiceI getRepairService() {
		return repairService;
	}

	@Autowired
	public void setRepairService(RepairServiceI repairService) {
		this.repairService = repairService;
	}

	@RequestMapping(params = "repair")
	@ResponseBody
	public AjaxJson repair(String id) {
		AjaxJson j = new AjaxJson();
	repairService.repair("tp_pcrm");
		j.setSuccess(true);
		return j;
	}

}
