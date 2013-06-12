package com.chamago.pcrm.customerservice.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.Constants;
import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.customerservice.pojo.CSICustomAlertTime;
import com.chamago.pcrm.customerservice.pojo.CSIDetail;
import com.chamago.pcrm.customerservice.pojo.CSIQuery;
import com.chamago.pcrm.customerservice.pojo.CSIType;
import com.chamago.pcrm.customerservice.pojo.CustomerServiceIssues;
import com.chamago.pcrm.customerservice.service.CustomerServiceService;
import com.chamago.pcrm.taobao.top.service.TopService;
import com.chamago.pcrm.trade.service.TradeService;
import com.taobao.api.ApiException;
import com.taobao.api.response.CrmMembersGetResponse;
import com.taobao.api.response.RefundsApplyGetResponse;

/**
 * 售后Controller
 * @author James.wang
 */
@Controller
@RequestMapping("/customerService")
public class CustomerServiceController {

	@Autowired
	private CustomerServiceService customerServiceService;  //售后业务接口
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private TopService topService;
	
	private final static Logger logger = Logger.getLogger(CustomerServiceController.class);

	@RequestMapping("/index")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String index(ModelMap map, HttpServletRequest request) {
		String tradeId = request.getParameter("tradeId");
		String buyer = request.getParameter("buyer");
		String status = "list";
		if(tradeId != null) {
			status = "open";
			map.put("tradeId", tradeId);
		} else if (null != buyer) {
			map.put("buyer", buyer); 
			map.put("userid", request.getParameter("userid"));
		}
		map.put("status", status);
		return "/customer_service/index";
	}
	
	/**
	 * 客服售后面板首页
	 */
	@RequestMapping("/back/index")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String backIndex(ModelMap map, HttpServletRequest request) throws ApiException{
		String buyer = request.getParameter("buyer");
		String sellerNick = request.getParameter("sellerNick");
		String nick = request.getParameter("nick");
		
		//获取当前买家正在处理的售后列表
		List<CustomerServiceIssues> customerServiceIssuess = getCustomerServiceService().getCustomerServiceIssuesByPresent(buyer, sellerNick);
		//获取其订单信息
		for(int i = 0; i < customerServiceIssuess.size(); i++) {
			customerServiceIssuess.get(i).setTrade(getTradeService().findTradeByTid(Long.parseLong(customerServiceIssuess.get(i).getTradeId())));
			customerServiceIssuess.get(i).setTradeOrder(getTradeService().findOrderByTid(customerServiceIssuess.get(i).getTradeId()));
		}
		
		//获取当前买家未处理的售后列表
		List<CustomerServiceIssues> customerServiceIssuess2 = getCustomerServiceService().getCustomerServiceIssuesByUndisposed(buyer, sellerNick);
		//获取其订单信息
		for(int i = 0; i < customerServiceIssuess2.size(); i++) {
			customerServiceIssuess2.get(i).setTrade(getTradeService().findTradeByTid(Long.parseLong(customerServiceIssuess2.get(i).getTradeId())));
			customerServiceIssuess2.get(i).setTradeOrder(getTradeService().findOrderByTid(customerServiceIssuess2.get(i).getTradeId()));
		}
		map.put("buyer", buyer);
		map.put("sellerNick", sellerNick);
		map.put("customerServiceIssuess", customerServiceIssuess);
		map.put("customerServiceIssuess2", customerServiceIssuess2);
		map.put("csCount", getCustomerServiceService().getCustomerServiceCountByBuyerNick(buyer, sellerNick));
		
		CrmMembersGetResponse res = topService.getCrmMembers(sellerNick, buyer);
		RefundsApplyGetResponse res2 = topService.getBuyerRefundsCount(sellerNick, buyer);
		if(res.isSuccess()) {
			map.put("tradeCount",  res.getMembers().get(0).getTradeCount());
		}
		if(res2.isSuccess()) {
			if(res2.getRefunds() != null) {
				map.put("refundCount", res2.getRefunds().size());
			} else {
				map.put("refundCount", 0);
			}
			
		}
		return "/back/index";
	}
	
	/**
	 * 跳转至售后问题详细页
	 */
	@RequestMapping("/back/detail")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String backDetail(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String sellerNick = request.getParameter("sellerNick");
		try {
			getCustomerServiceService().csiHandlingTimeoutSetting(id, sellerNick);
		} catch(Exception e) {
			logger.error("detail csiHandlingTimeoutSetting", e);
		}
		Object[] o = getCustomerServiceService()
				.getCustomerServiceIssueByCSIId(id);
			map.put("customerServiceIssuesDetail", o);
			map.put("csiDetailList", getCustomerServiceService()
					.getCSIDetailByCSIId(id));
		return "/back/cs_detail";
	}
	
	/**
	 * 跳转至售后问题添加页
	 * 如果当前交易已存在售后问题，则跳转至详细页
	 */
	@RequestMapping("/open")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String open(ModelMap map, HttpServletRequest request) {
		String tradeId = request.getParameter("tradeId");
		String sellerNick = request.getParameter("sellerNick");
		map.put("CSITypeList", getCustomerServiceService()
				.getCSITypeListBySellerNick(sellerNick));
		map.put("tradeId", tradeId);
		return "/customer_service/add";
	}
	
	/**
	 * 判断当前TradeId是否存在售后问题
	 */
	@RequestMapping("/checkTradeIdExists")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String checkTradeIdExists(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		String tradeId = request.getParameter("tradeId");
		String id = getCustomerServiceService()
				.getCustomerServiceIssueCountByTradeId(tradeId);
		if(null != id && id.trim().length() > 0) {
			map.put("result", "true");
			map.put("id", id);
		} else {
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

	/**
	 * 跳转至售后问题详细页
	 */
	@RequestMapping("/detail")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String detail(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String sellerNick = request.getParameter("sellerNick");
		String userId = request.getParameter("userid");
		try {
			getCustomerServiceService().csiHandlingTimeoutSetting(id, sellerNick);
		} catch(Exception e) {
			logger.error("detail csiHandlingTimeoutSetting", e);
		}
		Object[] o = getCustomerServiceService()
				.getCustomerServiceIssueByCSIId(id);
			map.put("customerServiceIssuesDetail", o);
			map.put("csiDetailList", getCustomerServiceService()
					.getCSIDetailByCSIId(id));
		//获取
		map.put("userList", getCustomerServiceService().getUserListBySellerNick(sellerNick));
		if(null != o && null != o[14]) {
			map.put("statusList", getCustomerServiceService().getCSIStatusOperatSeqListById(o[14].toString()));
		}
		map.put("userId", userId);
		return "/customer_service/detail";
	}

	/**
	 * 查询售后列表信息
	 */
	@RequestMapping("/find")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String find(ModelMap map, HttpServletRequest request) {
		String nick = request.getParameter("nick");
		if(null != nick && nick.trim().length() > 0) {
			map.put("nick", nick);
		}
		return "/customer_service/find";
	}
	
	/**
	 * 查询自已需要解决的售后问题
	 */
	@RequestMapping("/findDetail")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String findDetail(ModelMap map, HttpServletRequest request) {
		String tradeId = request.getParameter("tradeId");
		String nick = request.getParameter("wangwangNick");
		String buyer = request.getParameter("buyerNick");
		String sellerNick = request.getParameter("sellerNick");
		PageModel pageModel = new PageModel();
		pageModel.setPageSize(5);
		findCommon(map, request, tradeId, null, null, nick, buyer, sellerNick, pageModel);
		return "/customer_service/find_detail";
	}

	/**
	 * 保存当前交易编号的售后问题
	 */
	@RequestMapping("/save")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String save(
			@ModelAttribute("customerServiceIssues") CustomerServiceIssues customerServiceIssues,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> param = new HashMap<String, String>();
		try {
			customerServiceIssues.setStatus("1");
			customerServiceIssues.setId(ObjectId.get().toString());
			getCustomerServiceService().saveCustomerServiceIssueInfo(
					customerServiceIssues);
			param.put("result", "true");
			param.put("id", customerServiceIssues.getId());
		} catch(Exception e) {
			logger.error("save saveCustomerServiceIssueInfo", e);
			param.put("result", "false");	
		}
			
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传图片
	 */
	@RequestMapping("/uploadImg")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public void uploadImg(MultipartHttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//判断目录是否存在，若不存在，则新建
		String savePath = request.getSession().getServletContext().getRealPath("/")+"customerService/";
		Utils.checkFileExist(savePath);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String generationfileName = ObjectId.get().toString();
		String updateP = request.getParameter("updateP");
		String fileNameSuffix = null;
		String fileName = null;
		if (null != updateP && !"".equals(updateP)) {
			try {
				MultipartFile mf = request.getFile(updateP);
				fileName = mf.getOriginalFilename();
				if (null != mf && !"".equals(mf)) {
					fileNameSuffix = fileName.substring(fileName
							.lastIndexOf(".") + 1, fileName.length());
					SaveFileFromInputStream(mf.getInputStream(), savePath,
							generationfileName + "." + fileNameSuffix);
					out.write("{'state':'0','fileName':'" + generationfileName
							+ "." + fileNameSuffix + "','updateP':'" + updateP
							+ "'}");
				}
			} catch (Exception e) {
				logger.error("uploadImg", e);
				out.write("{'state':'1'}");
			}
		}
	}
	
	/**
	 * 保存对售后问题的处理
	 */
	@RequestMapping("/saveDisposal")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String saveDisposal(@ModelAttribute("csiDetail") CSIDetail csiDetail, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = new Hashtable<String, String>();
		try {
			getCustomerServiceService().saveCSIDetailInfo(csiDetail);
			param.put("result", "true");
		} catch(Exception e) {
			e.printStackTrace();
			logger.debug("saveDisposal saveCSIDetailInfo", e);
			param.put("result", "false");
		}
		
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * 保存售后问题分配
	 */
	@RequestMapping("/saveAdmeasure")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String saveAdmeasure(ModelMap map, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("customerServiceIssues") CustomerServiceIssues customerServiceIssues) {
		Map<String, String> param = new Hashtable<String, String>();
		try {
			getCustomerServiceService().saveAdmeasure(customerServiceIssues.getId(), customerServiceIssues.getCsNick());
			param.put("result", "true");
		} catch(Exception e) {
			logger.error("saveAdmeasure saveAdmeasure", e);
			param.put("result", "false");
		}
		
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 图片下载
	 */
	@RequestMapping("/downloadFile")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String downloadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		String fileName = request.getParameter("fileName");
		String tempPath = request.getSession().getServletContext().getRealPath("/")+"customerService/" + fileName;
		try {
			long fileLength = new File(tempPath).length();
			response.setContentType("content-type: image/*;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(tempPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			logger.error("downloadFile", e);
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
		return null;
	}
	
	/**
	 * 删除附件
	 */
	@RequestMapping("/deleteFile")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String deleteFile(HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getParameter("fileName");
		String tempPath = Constants.FILE_PATH + "/" + fileName;
		Map<String, String> map = new HashMap<String, String>();
		try {
			new File(tempPath).delete();
			map.put("result", "true");
		} catch(Exception e) {
			e.printStackTrace();
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
	
	/**
	 * 售后后台管理首页
	 */
	@RequestMapping("/admin/index")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String admin(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		String sellerNick = request.getParameter("seller");
		//加载售后类型
		map.put("CSITypeList", getCustomerServiceService()
				.getCSITypeListBySellerNick(sellerNick));
		//加载店铺下所有的客服信息
		map.put("customerServiceList", getCustomerServiceService().getUserListBySellerNick(sellerNick));
		map.put("sellerNick", sellerNick);
		return "/admin/customer_service/index";
	}
	
	/**
	 * 后台查询售后列表
	 */
	@RequestMapping("/admin/find")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String adminFind(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		String tradeId = request.getParameter("tradeId");
		String buyerNick = request.getParameter("buyerNick");
		String type = request.getParameter("type");
		String priority = request.getParameter("priority");
		String csNick = request.getParameter("csNick");
		String sellerNick = request.getParameter("sellerNick");
		PageModel pageModel = new PageModel();
		if(request.getParameter("pageSize") != null) {
			try {
				pageModel.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
			} catch(Exception e) {
				pageModel.setPageSize(10);
			}
		} else {
			pageModel.setPageSize(10);
		}
		map.put("pageSize", pageModel.getPageSize());
		//TODO 权限验证,如果当前登录者为掌柜则可搜索csNick,如果为客服，则只能搜索自已的
		findCommon(map, request, tradeId, type, priority, csNick, buyerNick, sellerNick, pageModel);
		map.put("sellerNick", sellerNick);
		return "/admin/customer_service/find";
	}
	
	/**
	 * 获取售后详细
	 */
	@RequestMapping("/admin/detail")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String getCsDetail(ModelMap map, HttpServletRequest request) {
		String id = request.getParameter("id");
		Object[] o = getCustomerServiceService()
				.getCustomerServiceIssueByCSIId(id);
		String sellerNick = request.getParameter("sellerNick");
		String userId = request.getParameter("userid");
		map.put("customerServiceIssuesDetail", o);
		map.put("csiDetailList", getCustomerServiceService()
					.getCSIDetailByCSIId(id));
		if(null != o && null != o[14]) {
			map.put("statusList", getCustomerServiceService().getCSIStatusOperatSeqListById(o[14].toString()));
		}
		
		map.put("users", getCustomerServiceService().getUserListBySellerNick(sellerNick));
		map.put("userId", userId);
		map.put("seller", sellerNick);
		return "/admin/customer_service/show_cs_detail";
	}
	
	/**
	 * 加载当前客服组下所有客服列表
	 */
	@RequestMapping("/admin/loadCSListByGroup")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String loadCSListByGroup(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			List<Object[]> csList = getCustomerServiceService().getCSListByGroup(request.getParameter("group"));
			StringBuffer options = new StringBuffer();
			for(Object[] o : csList) {
				options.append("<option value='" + o[0] + "'>" + o[0] + "</option>");
			}
			map.put("result", "true");
			map.put("message", options.toString());
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
	
	/**
	 * 获取当前店铺下的售后类型列表
	 */
	@RequestMapping("/admin/loadCSITypeList")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String loadCSITypeList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		String sellerNick = request.getParameter("sellerNick");
		try {
			List<CSIType> csiTypes = getCustomerServiceService().getCSITypeListBySellerNick(sellerNick);
			StringBuffer options = new StringBuffer();
			for(CSIType csiType : csiTypes) {
				options.append("<option value='" + csiType.getId() + "'>" + csiType.getName() + "</option>");
			}
			map.put("result", "true");
			map.put("message", options.toString());
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
	
	/**
	 * 删除售后问题类型
	 */
	@RequestMapping("/admin/deleteCSType")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String deleteCSType(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new Hashtable<String, String>();
		try {
			String id = request.getParameter("id");
			getCustomerServiceService().deleteCSType(id);
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
	
	/**
	 * 跳转至修改售后问题类型页
	 */
	@RequestMapping("/admin/toUpdateCsiType")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String toUpdateCsiType(ModelMap map, HttpServletRequest request) {
		String id = request.getParameter("id");
		map.put("csiType", getCustomerServiceService().getCsiTypeById(id));
		return "/admin/customer_service/type";
	}
	
	/**
	 * 修改售后问题类型
	 */
	@RequestMapping("/admin/updateCSType")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String updateCSType(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			CSIType csiType = new CSIType();
			csiType.setId(id);
			csiType.setName(name);
			getCustomerServiceService().updateCSIType(csiType);
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
	
	
	/**
	 * 删除售后问题
	 */
	@RequestMapping("/admin/delete")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String adminDelete(HttpServletRequest request, HttpServletResponse response) {
		String tradeId = request.getParameter("tradeId");
		Map<String, String> map = new HashMap<String, String>();
		try {
			getCustomerServiceService().deleteCustomerService(tradeId);
			map.put("result", "true");
		} catch(Exception e) {
			logger.error("delete customerService error", e);
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
	
	/**
	 * 售后类型管理
	 */
	@RequestMapping("/admin/type")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String type(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		return "/admin/customer_service/type";
	}
	
	/**
	 * 加载售后类型列表
	 */
	@RequestMapping("/admin/typeFindDetail")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String typeFindDetail(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		String sellerNick = request.getParameter("sellerNick");
		map.put("CSITypeList", getCustomerServiceService()
				.getCSITypeListBySellerNick(sellerNick));
		return "/admin/customer_service/type_findDetail";
	}

	/**
	 * 读取店铺售后类型
	 */
	@RequestMapping("/admin/loadcsType")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String loadcsType(ModelMap map, HttpServletRequest request) {
		String sellerNick = request.getParameter("sellerNick");
		map.put("CSITypeList", getCustomerServiceService()
				.getCSITypeListBySellerNick(sellerNick));
		map.put("allCount", getCustomerServiceService().getCustomerServiceCountBySellerNick(sellerNick));
		return "/admin/customer_service/cs_type"; 
	}
	
	/**
	 * 售后类型添加
	 */
	@RequestMapping("/admin/saveType")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String saveType(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		String sellerNick = request.getParameter("sellerNick");
		try {
			CSIType csiType = new CSIType();
			csiType.setName(request.getParameter("name"));
			csiType.setSellerNick(sellerNick);
			//过滤script, html, style　代码
			csiType.setName(Utils.getNoHTMLString(csiType.getName()));
			if(getCustomerServiceService().getTypeCountbyName(csiType.getName(), csiType.getSellerNick()) > 0) {
				map.put("result", "false");
				map.put("message", "售后类型名称已存在");
			} else {
				map.put("result", "true");
				getCustomerServiceService().saveType(csiType);
			}
		} catch(Exception e) {
			logger.error("saveType error", e);
			map.put("result", "false");
			map.put("message", "售后类型添加失败");
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
	
	/**
	 * 删除Type
	 */
	@RequestMapping("/admin/deleteType")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String deleteType(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		Map<String, String> map = new HashMap<String, String>();
		try {
			getCustomerServiceService().deleteType(id);
			map.put("result", "true");
		} catch(Exception e) {
			logger.error("delete type error", e);
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
	
	/**
	 * 客服自设提醒时间
	 */
	@RequestMapping("/admin/customAlertTime")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String customAlertTime(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		String sellerNick = request.getParameter("sellerNick");
		//获取系统配置
		map.put("customAlertTimeList", getCustomerServiceService().getCsiStatusBySellerNick(sellerNick));
		map.put("sellerNick", sellerNick);
		return "/admin/customer_service/custom_alert_time";
	}
	
	/**
	 * 修改客服自设提醒设置
	 */
	@RequestMapping("/admin/updateCustomAlertTime")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String updateCustomAlertTime(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String sellerNick = request.getParameter("sellerNick");
			String id = request.getParameter("id");
			Integer y = Integer.parseInt(request.getParameter("y"));
			Integer r = Integer.parseInt(request.getParameter("r"));
			getCustomerServiceService().UpdateCustomAlertTime(sellerNick, id, y, r);
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
	
	/**
	 * 保存客服自设提醒设置
	 */
	@RequestMapping("/admin/saveCustomAlertTime")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String saveCustomAlertTime(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {	
			String sellerNick = request.getParameter("sellerNick");
			String id = request.getParameter("id");
			Integer y = Integer.parseInt(request.getParameter("y"));
			Integer r = Integer.parseInt(request.getParameter("r"));
			CSICustomAlertTime csiCustomAlertTime = new CSICustomAlertTime();
			csiCustomAlertTime.setSellerNick(sellerNick);
			csiCustomAlertTime.setStatusId(id);
			csiCustomAlertTime.setYelloLightAlertTime(y);
			csiCustomAlertTime.setRedLightAlertTime(r);
			getCustomerServiceService().saveCustomAlertTime(csiCustomAlertTime);
			map.put("result", "true");
		} catch(Exception e) {
			logger.error("save customAlertTime error", e);
			map.put("result", "false");
			map.put("message", "当前售后处理状态设置失败");
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
	
	/**
	 * 删除客服自设提醒设置
	 */
	@RequestMapping("/admin/deleteCustomAlertTime")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String deleteCustomAlertTime(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			getCustomerServiceService().deleteCustomAlertTime(request.getParameter("id"));
			map.put("result", "true");
		} catch(Exception e) {
			logger.error("delete customAlertTime error", e);
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
	
	/**
	 * 上传图片
	 */
	@RequestMapping("/admin/uploadImg")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public void adminUploadImg(MultipartHttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//判断目录是否存在，若不存在，则新建
		String savePath = request.getSession().getServletContext().getRealPath("/")+"customerService/";
		Utils.checkFileExist(savePath);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String generationfileName = ObjectId.get().toString();
		String updateP = request.getParameter("updateP");
		String fileNameSuffix = null;
		String fileName = null;
		if (null != updateP && !"".equals(updateP)) {
			try {
				MultipartFile mf = request.getFile(updateP);
				fileName = mf.getOriginalFilename();
				if (null != mf && !"".equals(mf)) {
					fileNameSuffix = fileName.substring(fileName
							.lastIndexOf(".") + 1, fileName.length());
					SaveFileFromInputStream(mf.getInputStream(), savePath,
							generationfileName + "." + fileNameSuffix);
					out.write("{'state':'0','fileName':'" + generationfileName
							+ "." + fileNameSuffix + "','updateP':'" + updateP
							+ "'}");
				}
			} catch (Exception e) {
				logger.error("uploadImg", e);
				out.write("{'state':'1'}");
			}
		}
	}
	
	/**
	 * 保存对售后问题的处理
	 */
	@RequestMapping("/admin/saveDisposal")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String adminSaveDisposal(@ModelAttribute("csiDetail") CSIDetail csiDetail, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = new Hashtable<String, String>();
		try {
			getCustomerServiceService().saveCSIDetailInfo(csiDetail);
			param.put("result", "true");
		} catch(Exception e) {
			e.printStackTrace();
			logger.debug("saveDisposal saveCSIDetailInfo", e);
			param.put("result", "false");
		}
		
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * 保存售后问题分配
	 */
	@RequestMapping("/admin/saveAdmeasure")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String adminSaveAdmeasure(ModelMap map, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("customerServiceIssues") CustomerServiceIssues customerServiceIssues) {
		Map<String, String> param = new Hashtable<String, String>();
		try {
			getCustomerServiceService().saveAdmeasure(customerServiceIssues.getId(), customerServiceIssues.getCsNick());
			param.put("result", "true");
		} catch(Exception e) {
			logger.error("saveAdmeasure saveAdmeasure", e);
			param.put("result", "false");
		}
		
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 图片下载
	 */
	@RequestMapping("/admin/downloadFile")
	@AopLogModule(name=C.LOG_MODULE_CUSTOMERSERVICE,layer=C.LOG_LAYER_CONTROLLER) 
	public String adminDownloadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		String fileName = request.getParameter("fileName");
		String tempPath = request.getSession().getServletContext().getRealPath("/")+"customerService/" + fileName;
		try {
			long fileLength = new File(tempPath).length();
			response.setContentType("content-type: image/*;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(tempPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			logger.error("downloadFile", e);
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
		return null;
	}
	
	/**
	 * 搜索公共部份
	 */
	private void findCommon(ModelMap map, HttpServletRequest request, String tradeId, String type, String priority, String nick, String buyer, String sellerNick, PageModel pageModel) {
		CSIQuery csiQuery = new CSIQuery();
		csiQuery.setTradeId(tradeId);
		csiQuery.setType(type);
		csiQuery.setPriority(priority);
		csiQuery.setNick(nick);
		csiQuery.setBuyer(buyer);
		csiQuery.setSellerNick(sellerNick);
		int pageNo = 1;
		if(request.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(request.getParameter("pageNo").toString());
			if(pageNo <= 0) {
				pageNo = 1;
			}
		}
		pageModel.setPageNo(pageNo);	
		getCustomerServiceService().findByCurrentNick(pageModel, csiQuery);
		if(pageModel.getList().size() > 0) {
			map.put("customerLists", pageModel);
		}
	}
	
	// 保存文件 1,文件 2，保存路径 3，文件名称
	private void SaveFileFromInputStream(InputStream stream, String path,
			String filename) throws IOException {
		FileOutputStream fs = new FileOutputStream(path + "/" + filename);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}
	
	public CustomerServiceService getCustomerServiceService() {
		return customerServiceService;
	}

	public void setCustomerServiceService(
			CustomerServiceService customerServiceService) {
		this.customerServiceService = customerServiceService;
	}

	public TradeService getTradeService() {
		return tradeService;
	}

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
}