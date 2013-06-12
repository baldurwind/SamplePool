/**
 * 
 */
package com.chamago.pcrm.worktable.advice.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.worktable.advice.pojo.Advice;
import com.chamago.pcrm.worktable.advice.pojo.AdviceType;
import com.chamago.pcrm.worktable.advice.service.AdviceService;
import com.chamago.pcrm.worktable.connect.service.SysMgtService;
import com.mysql.jdbc.StringUtils;

/**
 * 建议控制器 
 * @author gavin.peng
 *
 */
@Controller
@RequestMapping("adviceservice")
public class AdviceController {
	
	private static Logger logger = LoggerFactory
	.getLogger(AdviceController.class);
	
	private static int STATUS_TAKE = 2;
	private static String ADVICE_STATUS = "all";
	
	@Autowired
	private AdviceService adviceService;
	
	@Autowired
	private SysMgtService sysMgtService;
	
	
	@RequestMapping("/admin/index")
	public String toAdviceMgt(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return "/admin/advice/advicemgt";
	}
	
	
	@RequestMapping("/add")
	public String insertAdvice(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String title = request.getParameter("title");
		title = title.replaceAll(" ", "");
		String content = request.getParameter("content");
		String sellerNick = request.getParameter("seller");
		String creator = request.getParameter("creator");
		String orientedType = request.getParameter("orientedtype");
		String type = request.getParameter("type");
		StringBuffer msg = new StringBuffer("");
		if (StringUtils.isNullOrEmpty(title)) {
			
			msg.append("参数title不能为空");
		}
		System.out.println(title.length());
		if (StringUtils.isNullOrEmpty(content)) {
			if(msg.length()>0){
				msg.append(",");
			}
			msg.append("参数content不能为空!");
		}
		
		if (StringUtils.isNullOrEmpty(creator)) {
			creator = null;
		}
		content = content.replaceAll("\\r\\n", "<br>");
		Advice advice = new Advice();
		advice.setTitle(title);
		advice.setContent(content);
		advice.setId(ObjectId.get().toString());
		advice.setCreator(creator);
		advice.setType(type);
		advice.setOrientedType(Integer.parseInt(orientedType));
		advice.setCreated(new Date());
		advice.setModified(new Date());
		advice.setSellerNick(sellerNick);
		Map<String, String> param = new Hashtable<String, String>();
		if(msg.length()<=0){
			try {
				adviceService.insertAdvice(advice);
				param.put("result", "true");
				logger.info("新增建议成功 ");
			} catch (Exception e) {
				logger.error("新建建议失败!");
				e.printStackTrace();
				param.put("result", "false");
			}
		}
		try {
			String rs = param.get("result");
			if ("true".equals(rs)&&msg.length()<=0) {
				response.getWriter()
						.print("<html><body><script>parent.addsucc();</script></body></html>");
			} else {
				response.getWriter()
						.print("<html><body><script>parent.addfail();</script></body></html>");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
		
		return null;
	}

	@RequestMapping("/admin/advicetypeadd")
	public String insertAdviceType(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		String creator = request.getParameter("creator");
		String sellerNick = request.getParameter("seller");
		StringBuffer msg = new StringBuffer("");
		if (StringUtils.isNullOrEmpty(name)) {
			msg.append("参数name不能为空");
		}
		
		AdviceType adviceType = new AdviceType();
		adviceType.setName(name);
		adviceType.setId(ObjectId.get().toString());
		adviceType.setCreator(creator);
		adviceType.setCreated(new Date());
		adviceType.setModified(new Date());
		adviceType.setSellerNick(sellerNick);
		Map<String, String> param = new Hashtable<String, String>();
		if(msg.toString().length()<=0){
			try {
				int count = adviceService.findAdvicesTypeByName(name,sellerNick);
				if(count<=0){
					adviceService.insertAdviceType(adviceType);
					param.put("result", "true");
				}else{
					logger.error("建议类型["+name+"]已经存在!");
					param.put("result", "-1");
				}
				
			} catch (Exception e) {
				logger.error("新建建议失败!");
				e.printStackTrace();
				param.put("result", "false");
			}
		}
		try {
			
			JSONObject jsp = JSONObject.fromObject(param);
			response.getWriter().write(jsp.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
		logger.info("新增建议类型成功 ");
		return null;
	}
	
	@RequestMapping("/admin/advicepage")
	public String findAdvicePageListByUserid(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setHeader("ContentType", "text/json");
		
		String sellerNick = request.getParameter("seller");
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pagesize");
		String orientedType = request.getParameter("orientedtype");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
//		if (StringUtils.isNullOrEmpty(userid)) {
//			throw new Exception("参数userid为空!");
//		}
		if (StringUtils.isNullOrEmpty(type)) {
			throw new Exception(" 参数type为空!");
		}
		if (StringUtils.isNullOrEmpty(page)) {
			throw new Exception(" 参数page为空!");
		}

		try {
			StringBuilder json = new StringBuilder("");
			int oriented = Integer.parseInt(orientedType);
			int adviceStatus = -1;
			if(!ADVICE_STATUS.equals(status)){
				adviceStatus = Integer.parseInt(status);
			}
			//TODO
			//accountServiceImpl.getAccountAllRoles(0l);
			List<Object[]> userList = sysMgtService.getMembersBySeller(sellerNick);
			Map<String,String> userMap = new HashMap<String,String>();
			if(userList!=null&&userList.size()>0){
				for(Object[] obj:userList){
					userMap.put(obj[0].toString(), obj[1].toString());
				}
			}
			List<Advice> adviceList = adviceService.findAdvicesByOrientedTypeAndTypeAndStauts(oriented,type,adviceStatus,sellerNick);
			if (adviceList != null && adviceList.size() > 0) {
				PagedListHolder<Advice> list = new PagedListHolder<Advice>(
						adviceList);
				int currentPage = Integer
						.valueOf(request.getParameter("cpage"));
				list.setPage(currentPage);
				Utils.pagination(Integer.parseInt(pageSize), list, request);

				int curpage = list.getPage();
				int pageCount = list.getPageCount();

				Iterator<Advice> it = list.getPageList().iterator();
				// 显示每页的内容
				json.append("{\"page\":\"" + curpage + "\",\"pagecount\":\""
						+ pageCount + "\",\"pagesize\":\""+pageSize+"\",\"row\":[");
				int i = 0;
				while (it.hasNext()) {
					Advice advice = it.next();
					Map<String, String> param = new Hashtable<String, String>();
					
					String creator = "";
					String creatorid = advice.getCreator();
					if(creatorid==null||creatorid.length()<=0){
						creator = "匿名";
					}else{
						creator = userMap.get(creatorid);
						if(creator==null){
							creator ="此人已经不存在";
						}
					}
					//TODO 根据创建人查询角色和所属组  
					String cRole = "";
					String cGroup = "";
					
					param.put("id", advice.getId());
					param.put("title", advice.getTitle());
					param.put("content", advice.getContent());
					param.put("datetime", formatDate(advice.getCreated()));
					param.put("creator", creator);
					param.put("group", cGroup);
					param.put("role", cRole);
					param.put("score", String.valueOf(advice.getScore()));
					param.put("status", String.valueOf(advice.getStatus()));
					if (i > 0) {
						json.append(",");
					}
					JSONObject jsp = JSONObject.fromObject(param);
					json.append(jsp.toString());
					i++;
				}
				json.append("]");

			} else {
				json.append("{\"page\":\"" + 0 + "\",\"pagecount\":\"" + 0
						+ "\",\"row\":[]");
			}
			json.append("}");
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
		return null;
	}
	
	
	
	@RequestMapping("/admin/advicetypelist")
	public String findAdminAdviceTypeList(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setHeader("ContentType", "text/json");
		String sellerNick = request.getParameter("seller");
		try {
			//int oriented = Integer.parseInt(orientedType);
			StringBuilder json = new StringBuilder("");
			List<Object[]> adviceTypeList = adviceService.getAdviceTypeList(sellerNick);
			if (adviceTypeList != null && adviceTypeList.size() > 0) {
				
				// 显示每页的内容
				json.append("[");
				int i = 0;
				for(Object[] obj:adviceTypeList){
					Map<String, String> param = new Hashtable<String, String>();
					param.put("id", obj[0].toString());
					param.put("name", obj[1].toString());
					if(obj[2]!=null&&!obj[2].toString().equals("null")){
						param.put("totalcount", obj[2].toString());
					}else{
						param.put("totalcount","0");
					}
					if (i > 0) {
						json.append(",");
					}
					JSONObject jsp = JSONObject.fromObject(param);
					json.append(jsp.toString());
					i++;
				}
			} else {
				json.append("[");
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
	
	
	@RequestMapping("/advicetypelist")
	public String findAdviceTypeList(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setHeader("ContentType", "text/json");
		String sellerNick = request.getParameter("seller");
		try {
			//int oriented = Integer.parseInt(orientedType);
			StringBuilder json = new StringBuilder("");
			List<Object[]> adviceTypeList = adviceService.getAdviceTypeList(sellerNick);
			if (adviceTypeList != null && adviceTypeList.size() > 0) {
				
				// 显示每页的内容
				json.append("[");
				int i = 0;
				for(Object[] obj:adviceTypeList){
					Map<String, String> param = new Hashtable<String, String>();
					param.put("id", obj[0].toString());
					param.put("name", obj[1].toString());
					if(obj[2]!=null&&!obj[2].toString().equals("null")){
						param.put("totalcount", obj[2].toString());
					}else{
						param.put("totalcount","0");
					}
					if (i > 0) {
						json.append(",");
					}
					JSONObject jsp = JSONObject.fromObject(param);
					json.append(jsp.toString());
					i++;
				}
			} else {
				json.append("[");
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
	
	
	@RequestMapping("/admin/updateadvicetype")
	public String updateKnowledgeSubject(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String seller = request.getParameter("seller");
		String id = request.getParameter("advicetypeid");
		String name = request.getParameter("name");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			int count = adviceService.findAdvicesTypeByName(name,seller);
			if(count<=0){
				adviceService.updateAdviceTypeNameById(id, name);
				param.put("result", "true");
			}else{
				logger.error("建议类型["+name+"]已经存在!");
				param.put("result", "-1");
			}
			
		}catch(Exception e){
			logger.error("根据参数ids["+id+"]更新出错!");
			e.printStackTrace();
			param.put("result", "false");
		}
		JSONObject jsp = JSONObject.fromObject(param);
		try {
			response.getWriter().write(jsp.toString());

		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}

		return null;
	}
	

	
	@RequestMapping("/admin/deleteadvicetype")
	public String deleteKnowledgeSubject(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String id = request.getParameter("id");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			adviceService.delAdviceType(id);
			param.put("result", "true");
				
		}catch(Exception e){
			logger.error("根据参数ids["+id+"]删除主题出错!");
			e.printStackTrace();
			param.put("result", "false");
		}
		JSONObject jsp = JSONObject.fromObject(param);
		try {
			response.getWriter().write(jsp.toString());

		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}

		return null;
	}

	
	@RequestMapping("/admin/updateadvices")
	public String updateAdvices(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String ids = request.getParameter("ids");
		String targetType = request.getParameter("targettype");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			adviceService.batchUpdateAdviceByIds(ids, targetType);
			param.put("result", "true");
		}catch(Exception e){
			logger.error("根据参数ids["+ids+"]更新出错!");
			e.printStackTrace();
			param.put("result", "false");
		}
		JSONObject jsp = JSONObject.fromObject(param);
		try {
			response.getWriter().write(jsp.toString());

		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}

		return null;
	}

	@RequestMapping("/admin/updatestatus")
	public String updateAdvicesStatus(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		 
		String id = request.getParameter("id");
		String targetStatus = request.getParameter("targetstatus");
		String score = request.getParameter("score");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			float adviceScore = 0;
			if(!StringUtils.isNullOrEmpty(score)){
				adviceScore = Float.parseFloat(score);
			}
			int status = -1;
			if(!StringUtils.isNullOrEmpty(targetStatus)){
				status = Integer.parseInt(targetStatus);
			}
			adviceService.updateAdviceStatusById(id, status,adviceScore);
			param.put("result", "true");
		}catch(Exception e){
			logger.error("更新建议["+id+"]状态为["+targetStatus+"]出错!");
			e.printStackTrace();
			param.put("result", "false");
		}
		JSONObject jsp = JSONObject.fromObject(param);
		try {
			response.getWriter().write(jsp.toString());

		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}

		return null;
	}
	
	
	@RequestMapping("/admin/deleteadvices")
	public String deleteAdvices(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String ids = request.getParameter("ids");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			List<Advice> list = adviceService.findAdvicesByIds(ids);
			boolean cont = true;
			if(list!=null&&list.size()>0){
				for(Advice advice:list){
					if(advice.getStatus()>1){
						param.put("result", "-1");
						cont = false;
						break;
					}
				}
			}
			if(cont){
				adviceService.deleteAdvicesByIds(ids);
				param.put("result", "true");
			}
		}catch(Exception e){
			logger.error("根据参数ids["+ids+"]删除出错!");
			e.printStackTrace();
			param.put("result", "false");
		}
		JSONObject jsp = JSONObject.fromObject(param);
		try {
			response.getWriter().write(jsp.toString());

		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}

		return null;
	}

	
	@RequestMapping("/gettakedadvicecount")
	public String getGoodAdviceCount(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String creator = request.getParameter("creator");
		
		Map<String, String> param = new Hashtable<String, String>();
		try {
			if (StringUtils.isNullOrEmpty(creator)) {
				throw new Exception("参数creator为空!");
			}
			Object[] obj = adviceService.findGoodAdvicesByCreatorAndStauts(creator, STATUS_TAKE);
			if(obj!=null){
				param.put("count", obj[0].toString());
				param.put("score", obj[1].toString());
			}else{
				param.put("count", "0");
				param.put("score", "0");
			}
			
		} catch (Exception e) {
			logger.error("获取用户采纳建议个数和得分出错");
			param.put("count", "0");
			param.put("score", "0");
			e.printStackTrace();
		} 
		
		JSONObject jsp = JSONObject.fromObject(param);
		try {
			
			response.getWriter().write(jsp.toString());

		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}
		return null;
	}

	
	
	/**
	 * 格式日期 返回 01/21 19:00格式的字符串
	 * @param dateTime
	 * @return
	 */
	public String formatDate(Date dateTime){
		StringBuffer value = new StringBuffer("");
		if(dateTime == null){
			return null;
		}
		String sdt = Utils.DateToString(dateTime, "yyyy-MM-dd HH:mm:ss");
		String[] date = sdt.split(" ");
		if(date.length>1){
			String[] ymd = date[0].split("-");
			value.append(ymd[1]);
			value.append("/");
			value.append(ymd[2]);
			value.append(" ");
			String time = date[1].substring(0,5);
			value.append(time);
		}
		return value.toString();
		
		
	}
	
	
	public static void main(String[] args){
		AdviceController ac = new AdviceController();
		System.out.println(ac.formatDate(new Date()));
	}

}
