/**
 * 
 */
package com.chamago.pcrm.worktable.knowledgebase.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.item.pojo.ItemSearch;
import com.chamago.pcrm.item.service.ItemService;
import com.chamago.pcrm.worktable.knowledgebase.pojo.Knowledge;
import com.chamago.pcrm.worktable.knowledgebase.pojo.KnowledgeSubject;
import com.chamago.pcrm.worktable.knowledgebase.service.KnowledgeService;
import com.mysql.jdbc.StringUtils;



/**
 * 
 * 知识库
 * @author gavin.peng
 *
 */
@Controller
@RequestMapping("knowledge")
public class KnowledgeController {
	
	@Autowired
	private KnowledgeService knowledgeService;
	
	@Autowired
	private ItemService itemService;
	
	 
	private static Logger logger = LoggerFactory.getLogger(KnowledgeController.class);
	private static String YES = "1";
	private static String NO = "0";
	private static int PAGE_SIZE = 8;
	private static int SEARCH_PAGE_SIZE = 15;
	private static String SUBJECT_GENERNANL = "1";
	private static String SUBJECT_ITEM = "2";
	private static String SUBJECT_SEARCH = "subject";
	private static String KNOWLEDGE_SEARCH = "know";
	
	/**
	 * 1不是叶子节点，0 是叶子节点
	 */
	private static int LEAF = 1;
	
	
	@RequestMapping("/admin/index")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String toKnowledgeMgt(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return "/admin/knowledge/knowledgemgt";
	}
	
	@RequestMapping("/index")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String toKnowledgeIndex(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return "/worktable/knowledge";
	}
	
	
	
	@RequestMapping("/admin/itembaseinfo")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String getItemInfoByItemId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String itemid = request.getParameter("itemid");
		try{
			if(StringUtils.isNullOrEmpty(itemid)){
				throw new Exception("参数 [itemid]为空!");
			}
			StringBuffer json = new StringBuffer("");
			Map<String, String> param = new Hashtable<String, String>();
			if(Long.parseLong(itemid)>0){
				Object[] item = itemService.findItemById(Long.parseLong(itemid));
				if(item==null){
					logger.error("没有找到 id为["+itemid+"]的商品");
					throw new Exception("没有找到 id为["+itemid+"]的商品!");
				}
				param.put("title",item[3].toString());
				param.put("imgurl", item[16].toString());
				param.put("price", item[22].toString());
				JSONObject row = JSONObject.fromObject(param);
				json.append(row.toString());
			}
			
			response.getWriter().write(json.toString());
			
		}catch(Exception e){
			logger.error("根据商品id["+itemid+"]查找知识错误!");
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}
			
		return null;
	}
	
	
	/**
	 * 根据商品ID查找相关知识
	 * @param map
	 * @param itemid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/itemknowledge/{itemid}")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String relatedItemKnowledgeByItemId(ModelMap map, @PathVariable long itemid,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("ContentType", "text/json");
		String seller = request.getParameter("seller");
		String page = request.getParameter("page");
		if(StringUtils.isNullOrEmpty(page)){
			throw new Exception("参数page为空!");
		}
		StringBuilder json = new StringBuilder("");
		try{
			List<Object[]> itemKnowList = knowledgeService.findKnowledgeByProductId(itemid,seller);
			if(itemKnowList!=null&&itemKnowList.size()>0){
				
				PagedListHolder<Object[]> list = new PagedListHolder<Object[]>(itemKnowList);
				int currentPage=Integer.valueOf(request.getParameter("cpage"));
				list.setPage(currentPage);
				Utils.pagination(PAGE_SIZE,list, request);
				int curpage = list.getPage();
				int pageCount = list.getPageCount();
				json.append("{\"page\":\""+curpage+"\",\"pagecount\":\""+pageCount+"\",\"row\":[");
				Iterator it = list.getPageList().iterator();
				int i=0;
		        while (it.hasNext()) {
		        	Object[] obj = (Object[])it.next();
		    		Map<String, String> param = new Hashtable<String, String>();
					param.put("id", String.valueOf(obj[0]));
					param.put("title", String.valueOf(obj[1]));
					param.put("content", String.valueOf(obj[2]));
					Object fileName = obj[3];
					if(fileName!=null&&fileName.toString().indexOf("_")>0){
						String fn = fileName.toString();
						param.put("isfile", YES);
						String[] fp = fn.split("_");
						if(fp!=null&&fp.length>0){
							param.put("firstpart", fp[0]);
						}
						if(fp!=null&&fp.length>1){
							param.put("lastpart", fp[1]);
						}
					
						
					}else{	
						param.put("isfile", NO);
					}
				
		    		JSONObject jsp = JSONObject.fromObject(param);
		    		if(i>0){
		             	json.append(",");
		            }
					json.append(jsp.toString());
		            i++;
		        }
		        json.append("]");
			}else{
				logger.info("根据商品id["+itemid+"]没有找到相关知识");
				json.append("{\"page\":\""+0+"\",\"pagecount\":\""+0+"\",\"row\":[]");
			}
			json.append("}");
			response.getWriter().write(json.toString());
			
		}catch(Exception e){
			logger.error("根据商品id["+itemid+"]查询知识失败!");
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}
		return null;

	}
	
	
	/**
	 * 根据创建人查找自定义知识
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/finddefinedknowledge")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String find(ModelMap map,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String creatorid = request.getParameter("creatorid");
		String page = request.getParameter("page");
		if(StringUtils.isNullOrEmpty(page)){
			throw new Exception("参数page为空!");
		}
		StringBuilder json = new StringBuilder("");
		try{
			List<Object[]> itemKnowList = knowledgeService.findDefinedKnowledgeByCreatorId(creatorid);
			if(itemKnowList!=null&&itemKnowList.size()>0){
				PagedListHolder<Object[]> list = new PagedListHolder<Object[]>(itemKnowList);
				int currentPage=Integer.valueOf(request.getParameter("cpage"));
				list.setPage(currentPage);
				Utils.pagination(PAGE_SIZE,list, request);
				int curpage = list.getPage();
				int pageCount = list.getPageCount();
				json.append("{\"page\":\""+curpage+"\",\"pagecount\":\""+pageCount+"\",\"row\":[");
				Iterator it = list.getPageList().iterator();
				int i=0;
		        while (it.hasNext()) {
		        	Object[] obj = (Object[])it.next();
		    		Map<String, String> param = new Hashtable<String, String>();
					param.put("id", String.valueOf(obj[0]));
					param.put("title", String.valueOf(obj[1]));
					param.put("content", String.valueOf(obj[2]));
		    		JSONObject jsp = JSONObject.fromObject(param);
		    		if(i>0){
		             	json.append(",");
		            }
					json.append(jsp.toString());
		            i++;
		        }
		        json.append("]");
			}else{
				logger.info("根据自定义知识创建人id["+creatorid+"]没有找到相关知识");
				json.append("{\"page\":\""+0+"\",\"pagecount\":\""+0+"\",\"row\":[]");
			}
			json.append("}");
			response.getWriter().write(json.toString());
			
		}catch(Exception e){
			logger.error("根据商品id["+creatorid+"]查询知识失败!");
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}
		return null;

	}
	
	
	/**
	 * 根据主题ID查找相关知识
	 * @param map
	 * @param itemid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/subknowledge/{subjectid}")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String relatedSubjectKnowledgeBySubjectId(ModelMap map, @PathVariable String subjectid,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("ContentType", "text/json");
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pagesize");
		if(StringUtils.isNullOrEmpty(subjectid)){
			throw new Exception("参数subjectid为空!");
		}
		if(StringUtils.isNullOrEmpty(page)){
			throw new Exception("参数page为空!");
		}
		if(StringUtils.isNullOrEmpty(pageSize)){
			throw new Exception("参数pageSize为空!");
		}
		String sellerNick =request.getParameter("seller");
		StringBuilder json = new StringBuilder("");
		try{
			List<Object[]> subKnowList = knowledgeService.findKnowledgeBySubjectId(subjectid,sellerNick);
			if(subKnowList!=null&&subKnowList.size()>0){
				
				PagedListHolder<Object[]> list = new PagedListHolder<Object[]>(subKnowList);
				int currentPage = Integer
				.valueOf(request.getParameter("cpage"));
				list.setPage(currentPage);
				Utils.pagination(Integer.parseInt(pageSize),list, request);
				int curpage = list.getPage();
				int pageCount = list.getPageCount();
				json.append("{\"page\":\""+curpage+"\",\"pagecount\":\""+pageCount+"\",\"pagesize\":\""+pageSize+"\",\"row\":[");
				Iterator<Object[]> it = list.getPageList().iterator();
				int i=0;
		        while (it.hasNext()) {
		        	Object[] obj = it.next();
		    		Map<String, String> param = new Hashtable<String, String>();
		    		String itemid = "0";
		    		if(obj[4]!=null&&obj[4].toString().length()>0){
		    			itemid = obj[4].toString();
		    		}
					param.put("id", obj[0].toString());
					param.put("title", obj[1].toString());
					param.put("subject", obj[5].toString());
					param.put("content", obj[2].toString());
					param.put("datetime", formatDate(Utils.StringToDate(obj[3].toString(), "yyyy-MM-dd HH:mm:ss")));
					param.put("itemid", itemid);
					param.put("subjectid", obj[6].toString());
		    		JSONObject jsp = JSONObject.fromObject(param);
		    		if(i>0){
		             	json.append(",");
		            }
					json.append(jsp.toString());
		            i++;
		        }
		        json.append("]");
			}else{
				logger.info("根据主题id["+subjectid+"]没有找到相关知识");
				json.append("{\"page\":\""+0+"\",\"pagecount\":\""+0+"\",\"row\":[]");
			}
			json.append("}");
			response.getWriter().write(json.toString());
			
		}catch(Exception e){
			logger.error("根据主题id["+subjectid+"]查询知识失败!");
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}
		return null;

	}
	
	
	/**
	 * 新增知识
	 * @param map
	 * @param request
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/addknowledge")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String insertKnowledge(ModelMap map,
			MultipartHttpServletRequest request,HttpServletResponse resp) throws Exception{
		
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("ContentType", "text/json");
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String itemId = request.getParameter("itemid");
		String subjectId = request.getParameter("subjectid");
		String creator = request.getParameter("creator");
		String sellerNick = request.getParameter("seller");
		
		if(StringUtils.isNullOrEmpty(title)){
			throw new Exception("参数title为空!");
		}
		if(StringUtils.isNullOrEmpty(itemId)){
			throw new Exception("参数itemId为空!");
		}
		if(StringUtils.isNullOrEmpty(content)){
			throw new Exception("参数content为空!");
		}
		if(StringUtils.isNullOrEmpty(creator)){
			throw new Exception("参数creator为空!");
		}
		if(StringUtils.isNullOrEmpty(subjectId)){
			throw new Exception("参数subjectId为空!");
		}
		
		long item_id = Long.parseLong(itemId);
		
		String generationfileName = ObjectId.get().toString();
		content = content.replaceAll("\\r\\n", "<br>");
		// 保存路径
		String savePath = request.getSession().getServletContext().getRealPath("/")+"knowledgestore/";
		String updateP = request.getParameter("checkup");
		String fileName = null;
		Map<String, String> param = new Hashtable<String, String>();
		boolean succ = true;
		if (!StringUtils.isNullOrEmpty(updateP)) {
			try {
				MultipartFile mf = request.getFile(updateP);
				fileName = mf.getOriginalFilename();
				logger.info(fileName);
				if (!StringUtils.isNullOrEmpty(fileName)) {
					fileName = generationfileName+"_"+fileName;
					SaveFileFromInputStream(mf.getInputStream(), savePath,fileName);
				}
			} catch (Exception e) {
				logger.error("保存知识文件失败!");
				e.printStackTrace();
				param.put("result", "false");
				succ = false;
			}
		}
		if(succ){
			Knowledge knowledge =  new Knowledge();
			knowledge.setTitle(Utils.getNoHTMLString(title));
			knowledge.setContent(Utils.getNoHTMLString(content));
			knowledge.setSubjectId(subjectId);
			knowledge.setId(id);
			knowledge.setItemId(item_id);
			knowledge.setFileId(fileName);
			knowledge.setCreated(new Date());
			knowledge.setModified(new Date());
			knowledge.setCreator(creator);
			knowledge.setSellerNick(sellerNick);
			try{
				if("0".equals(id)){
					knowledge.setId(ObjectId.get().toString());
					if("-1".equals(knowledge.getSubjectId())){
						knowledgeService.insertDefinedKnowledge(knowledge);
					}else{
						knowledgeService.insertKnowledge(knowledge);
					}
				}else{
					knowledgeService.updateKnowledge(knowledge);
				}
				param.put("result", "true");
			}catch(Exception e){
				e.printStackTrace();
				param.put("result", "false");
			}
		}
		try {
			String rs = param.get("result");
			if("true".equals(rs)){
				resp.getWriter().print("<html><body><script>parent.addsucc();</script></body></html>");
			}else{
				resp.getWriter().print("<html><body><script>parent.addfail();</script></body></html>");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			resp.getWriter().close();
		}

		return null;
	}
	
	
	@RequestMapping("/admin/updateknowledge")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String updateAdvices(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String ids = request.getParameter("ids");
		String subjectid = request.getParameter("subjectid");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			knowledgeService.batchUpdateAdviceByIds(ids, subjectid);
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
	
	@RequestMapping("/admin/updatesubject")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String updateKnowledgeSubject(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String seller = request.getParameter("seller");
		String id = request.getParameter("subjectid");
		String subject = request.getParameter("subject");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			int count = knowledgeService.findSubjectByName(subject,seller);
			if(count<=0){
				knowledgeService.updateKnowledgeSubjectById(id, subject);
				param.put("result", "true");
			}else{
				logger.error("主题["+subject+"]已经存在!");
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
	
	@RequestMapping("/admin/deleteknowledge")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String deleteAdvices(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String ids = request.getParameter("ids");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			knowledgeService.deleteKnowledgesByIds(ids);
			param.put("result", "true");
				
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

	
	@RequestMapping("/updateknowledgesendnums")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String updateKnowledgeSendNums(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			knowledgeService.updateKnowledgeSendNumsById(type, id);
			param.put("result", "true");
				
		}catch(Exception e){
			logger.error("根据参数id["+id+"]更新知识库使用次数出错!");
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

	@RequestMapping("/deleteDefinedKnowledge")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String deleteDefinedKnowledge(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String ids = request.getParameter("id");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			knowledgeService.deleteDefinedKnowledgesByIds(ids);
			param.put("result", "true");
				
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

	
	@RequestMapping("/admin/deletesubject")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String deleteKnowledgeSubject(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String id = request.getParameter("id");
		Map<String, String> param = new Hashtable<String, String>();
		try{
			knowledgeService.deleteKnowledgeSubjectById(id);
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

	/**
	 * 
	 * @param map
	 * @param subjectid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/knowledgemgt")  
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String findKnowledgeListBySubjectid(HttpServletRequest request,HttpServletResponse response) throws Exception  {
		
		
		return "/worktable/addknowledge";
	}

	
	/**
	 * 根据关键字搜索
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/search")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String searchKnowlegeByKeyword(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		
		
		String keywords = request.getParameter("keywords");
		String itemid = request.getParameter("itemid");
		String subjectid = request.getParameter("subjectid");
		String creator = request.getParameter("creator");
		String seller = request.getParameter("seller");
		String searchType = request.getParameter("type");
		long item_id = 0l;
		if(StringUtils.isNullOrEmpty(keywords)){
			
		}
		if(!StringUtils.isNullOrEmpty(itemid)){
			item_id = Long.parseLong(itemid);
		}
		StringBuilder json = new StringBuilder("");
		try{
			List<Knowledge> knowlist = knowledgeService.searchKnowledgeByKeywords(seller,keywords,item_id,creator,subjectid);
			if(knowlist!=null&&knowlist.size()>0){
				if(SUBJECT_SEARCH.equals(searchType)){
					List<Object[]> subList = groupSearchKnowledge(knowlist);
					String wj = getSubjectListJson(subList,request);
					response.getWriter().write(wj.toString());
					return null;
				}else{
					PagedListHolder<Knowledge> list = new PagedListHolder<Knowledge>(knowlist);
					Utils.pagination(SEARCH_PAGE_SIZE,list, request);
					int curpage = list.getPage();
					int pageCount = list.getPageCount();
					json.append("{\"page\":\""+curpage+"\",\"pagecount\":\""+pageCount+"\",\"row\":[");
					Iterator<Knowledge> it = list.getPageList().iterator();
					int i=0;
			        while (it.hasNext()){
			        	Knowledge know = it.next();
			    		Map<String, String> param = new Hashtable<String, String>();
						param.put("id", know.getId());
						param.put("title", know.getTitle());
						param.put("content", know.getContent());
			    		JSONObject jsp = JSONObject.fromObject(param);
			    		if(i>0){
			             	json.append(",");
			            }
						json.append(jsp.toString());
			            i++;
			        }
			        json.append("]}");
			        response.getWriter().write(json.toString());
			        return null;
				}
			}else{
				json.append("{\"page\":\""+0+"\",\"pagecount\":\""+0+"\",\"row\":[]}");
			}
			
			response.getWriter().write(json.toString());
		}catch(Exception e){
			logger.error("根据关键字索引知识库失败!");
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}
		
		return null;
	}
	
	
	public Set<MultipartFile> getFileSet(HttpServletRequest request) {  
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	    Set<MultipartFile> fileset = new LinkedHashSet<MultipartFile>();  
	    for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {  
	        String key = (String)it.next();  
	        MultipartFile file = multipartRequest.getFile(key);  
	        if (file.getOriginalFilename().length() > 0) {  
	            fileset.add(file);  
	        }  
	    }  
	    return fileset;  
	}  
	
	/**
	 * 新增主题
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/addsubject")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String insertSubject(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("ContentType", "text/json");
		String sellerNick = req.getParameter("seller");
		String subject = req.getParameter("subject");
		String parentid = req.getParameter("parentid");
		String creator = req.getParameter("creator");
		KnowledgeSubject ks = new KnowledgeSubject();
		ks.setSubject(subject);
		ks.setParentId(parentid);
		ks.setCreator(creator);
		ks.setCreated(new Date());
		ks.setModified(new Date());
		ks.setId(ObjectId.get().toString());
		ks.setSellerNick(sellerNick);
		Map<String, String> param = new Hashtable<String, String>();
		try{
			int count = knowledgeService.findSubjectByName(subject,sellerNick);
			if(count<=0){
				knowledgeService.insertKnowledgeSubject(ks);
				param.put("result", "true");
			}else{
				logger.error("主题["+subject+"]已经存在!");
				param.put("result", "-1");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			param.put("result", "false");
		}
		JSONObject jsp = JSONObject.fromObject(param);
		try {
			 resp.getWriter().write(jsp.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			resp.getWriter().close();
		}
		
		return null;
	}
	
	/**
	 * 构建主题树
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/subjectlisttree")
	public String getKnowledgeSubject(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		
		String parentid = req.getParameter("parentid");
		if(StringUtils.isNullOrEmpty(parentid)){
			throw new Exception("参数[parentid]为空 ");
		}
		//sellerNick	
		List<KnowledgeSubject> list = knowledgeService.getSubjectByParentid(parentid,"");
		StringBuffer json = new StringBuffer("[");
		if(list!=null&&list.size()>0){
			int i=0;
			for(KnowledgeSubject su:list){
				Map<String, String> param = new Hashtable<String, String>();
				try{
					param.put("id",su.getId());
					param.put("name", su.getSubject());
				    if(su.getLeaf()==LEAF){
				    	param.put("leaf", "true");
					}else{
						param.put("leaf", "false");
					}
					
				}catch(Exception e){
					e.printStackTrace();
					param.put("result", "false");
				}
				JSONObject row = JSONObject.fromObject(param);
				if(i>0){
					json.append(",");
				}
				json.append(row.toString());
				i++;
			}
			json.append("]");
			
			PrintWriter out;
			try {
				out = resp.getWriter();
				out.write(json.toString());
				resp.flushBuffer();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
		
	}
	
	
	/**
	 * 显示主题列表
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/subjectlist")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String getAdminKnowledgeSubjectList(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		
		String sellerNick =req.getParameter("seller");
		try{
			List<KnowledgeSubject> genlist = knowledgeService.getSubjectByParentid(SUBJECT_GENERNANL,sellerNick);
			List<KnowledgeSubject> itemslist = knowledgeService.getSubjectByParentid(SUBJECT_ITEM,sellerNick);
			StringBuffer json = new StringBuffer("{\"gen\":[");
			if(genlist!=null&&genlist.size()>0){
				genernatSubjectJson(genlist,json);
			}
			json.append("]");
			json.append(",\"item\":[");
			if(itemslist!=null&&itemslist.size()>0){
				genernatSubjectJson(itemslist,json);
			}
			json.append("]}");
			resp.getWriter().write(json.toString());

		} catch (IOException e) {
			logger.error("加载知识库主题出错");
			e.printStackTrace();
		} finally {
			resp.getWriter().close();
		}
		return null;
		
		
	}
	
	
	/**
	 * 显示主题列表
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/subjectlist")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String getKnowledgeSubjectList(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		
		String sellerNick =req.getParameter("seller");
		String subjecttype = req.getParameter("stye");
		try{
			List<Object[]> genlist = knowledgeService.getSubjectAndKnowNumsByParentid(subjecttype,sellerNick);
			String json = getSubjectListJson(genlist,req);
			resp.getWriter().write(json.toString());

		} catch (IOException e) {
			logger.error("加载知识库主题出错");
			e.printStackTrace();
		} finally {
			resp.getWriter().close();
		}
		return null;
		
		
	}
	
	
	private List<Object[]> groupSearchKnowledge(List<Knowledge> list){
		if(list!=null&&list.size()>0){
			Map<String,String[]> subMap = new HashMap<String,String[]>();
			for(Knowledge know:list){
				if(!subMap.containsKey(know.getSubjectId())){
					subMap.put(know.getSubjectId(), new String[]{know.getSubjectName(),"1"});
				}else{
					String[] arry = subMap.get(know.getSubjectId());
					int subNums = Integer.parseInt(arry[1]);
					subNums ++;
					arry[1] = String.valueOf(subNums);
					subMap.put(know.getSubjectId(), arry);
				}
			}
			List<Object[]> subList = new ArrayList<Object[]>();
			Set<String> subjectSet = subMap.keySet();
			Iterator<String> it = subjectSet.iterator();
			while(it.hasNext()){
				String subjectId = it.next();
				String[] value = subMap.get(subjectId);
				Object[] subject = new Object[]{subjectId,value[0],value[1]};
				subList.add(subject);
			}
			return subList;
		}
		
		return null;
	}
	
	
	
	private String getSubjectListJson(List<Object[]> list,HttpServletRequest request){
		StringBuffer json = new StringBuffer("");
		PagedListHolder<Object[]> sublist = new PagedListHolder<Object[]>(list);
		Utils.pagination(PAGE_SIZE,sublist, request);
		int curpage = sublist.getPage();
		int pageCount = sublist.getPageCount();
		json.append("{\"page\":\""+curpage+"\",\"pagecount\":\""+pageCount+"\",\"row\":[");
		Iterator<Object[]> it = sublist.getPageList().iterator();
		int i=0;
        while (it.hasNext()){
        	Object[] obj = it.next();
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
        json.append("]}");
		return json.toString();
	}
	
	
	/**
	 * 查找卖家的产品
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/itemlist")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String getItemList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userid = "";
		// 通过userid找到所属的saller 
		String seller = request.getParameter("seller");
		String itemId = request.getParameter("itemid");
		
		//itemService.f
		long item_id = 0;
		StringBuilder json = new StringBuilder("");
		try{
			if(itemId!=null&&!itemId.equals("null")){
				item_id = Long.parseLong(itemId);
			}
			
			List<Object[]> itemlist = knowledgeService.findItemBySaller(seller);
			
			if(itemlist!=null&&itemlist.size()>0){
				if(item_id>0){
					Object[] selectedItem = null;
					for(int i=0;i<itemlist.size();i++){
						Object[] obj = itemlist.get(i);
						if(obj[0].toString().equals(String.valueOf(item_id))){
							selectedItem = obj;
							itemlist.remove(obj);
							break;
						}
					}
					if(selectedItem!=null){
						itemlist.add(0, selectedItem);
					}
					
				}
				PagedListHolder<Object[]> list = new PagedListHolder<Object[]>(itemlist);
				int currentPage=Integer.valueOf(request.getParameter("cpage"));
				list.setPage(currentPage);
				Utils.pagination(C.PAGINATION_SIZE,list, request);
				int curpage = list.getPage();
				int pageCount = list.getPageCount();
				json.append("{\"page\":\""+curpage+"\",\"pagecount\":\""+pageCount+"\",\"row\":[");
				Iterator it = list.getPageList().iterator();
				int i=0;
			    while (it.hasNext()) {
				 	Object[] obj = (Object[])it.next();
				 	Map<String, String> param = new Hashtable<String, String>();
					param.put("id",String.valueOf(obj[0]));
					param.put("name", String.valueOf(obj[2]));
					param.put("image", String.valueOf(obj[5]));
		    		JSONObject jsp = JSONObject.fromObject(param);
		    		if(i>0){
		             	json.append(",");
		            }
					json.append(jsp.toString());
		            i++;
		        }
		        json.append("]");
			}else{
				json.append("{\"page\":\""+0+"\",\"pagecount\":\""+0+"\",\"row\":[]");
			}
			json.append("}");
			response.getWriter().write(json.toString());
		}catch(Exception e){
			logger.error("查找卖家商品列表失败!");
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}
	
		return null;
		
	}


	/**
	 * 文件下载
	 */
	@RequestMapping("/downloadfile")
	public String downloadHealthTemp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		String fileName = request.getParameter("fileName");
		if(StringUtils.isNullOrEmpty(fileName)){
			return null;
		}
		fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
		String tempPath = request.getSession().getServletContext().getRealPath("/")+"knowledgestore/" + fileName;
		try {
			long fileLength = new File(tempPath).length();
			response.setContentType(this.getContentType(fileName));
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
	
	/**
	 * 店铺商品信息查询
	 * @throws IOException 
	 */
	@RequestMapping("/admin/itemsearch")
	@AopLogModule(name=C.LOG_MODULE_KNOWLEDGE,layer=C.LOG_LAYER_CONTROLLER)
	public String search(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String title = request.getParameter("title");
		String type = request.getParameter("type");
		String itemId = request.getParameter("itemid");
		String sellerNick = request.getParameter("userid");
		String p = request.getParameter("p");
		
		StringBuilder json = new StringBuilder("");
		try{
			long item_id = 0;
			if(itemId!=null&&!itemId.equals("null")){
				item_id = Long.parseLong(itemId);
			}
			
				
			if(sellerNick!=null){
				sellerNick = sellerNick.toLowerCase();
			}
			List<ItemSearch> itemSearchs = itemService.search(title, type, sellerNick, p);
			if(itemSearchs!=null&&itemSearchs.size()>0){
				if(item_id>0){
					ItemSearch selectedItem = null;
					for(int i=0;i<itemSearchs.size();i++){
						ItemSearch item = itemSearchs.get(i);
						if(item.getNumid().equals(String.valueOf(item_id))){
							selectedItem = item;
							itemSearchs.remove(item);
							break;
						}
					}
					if(selectedItem!=null){
						itemSearchs.add(0, selectedItem);
					}
					
				}
				PagedListHolder<ItemSearch> list = new PagedListHolder<ItemSearch>(itemSearchs);
				int currentPage=Integer.valueOf(request.getParameter("cpage"));
				list.setPage(currentPage);
				Utils.pagination(C.PAGINATION_SIZE,list, request);
				int curpage = list.getPage();
				int pageCount = list.getPageCount();
				json.append("{\"page\":\""+curpage+"\",\"pagecount\":\""+pageCount+"\",\"row\":[");
				Iterator<ItemSearch> it = list.getPageList().iterator();
				int i=0;
		        while (it.hasNext()) {
		        	ItemSearch item = it.next();
		    		Map<String, String> param = new Hashtable<String, String>();
					param.put("id", String.valueOf(item.getNumid()));
					param.put("name", item.getTitle());
					param.put("image",item.getPicUrl());
		    		JSONObject jsp = JSONObject.fromObject(param);
		    		if(i>0){
		             	json.append(",");
		            }
					json.append(jsp.toString());
		            i++;
		        }
		        json.append("]");
			}else{
				json.append("{\"page\":\""+0+"\",\"pagecount\":\""+0+"\",\"row\":[]");
			}
			json.append("}");
			response.getWriter().write(json.toString());
		}catch(Exception e){
			logger.error("根据关键字索引知识库失败!");
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}
		
		return null;
		
	}

	/**
	 * 生成知识库主题json
	 * @param list 主题集合
	 * @param json 
	 */
	private void genernatSubjectJson(List<KnowledgeSubject> list,StringBuffer json){
		int i=0;
		for(KnowledgeSubject su:list){
			if(i>0){
				json.append(",");
			}
			json.append("{\"id\":\""+su.getId()+"\",\"name\":\""+su.getSubject()+"\"}");
			i++;
		}
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
	/*

	设置文件类型

	*/

	 private String getContentType(String fileName) {
	  String fileNameTmp = fileName.toLowerCase();
	  String ret = "";
	  if (fileNameTmp.endsWith("txt")) {
	   ret = "text/plain";
	  }
	  if (fileNameTmp.endsWith("gif")) {
	   ret = "image/gif";
	  }
	  if (fileNameTmp.endsWith("jpg")) {
	   ret = "image/jpeg";
	  }
	  if (fileNameTmp.endsWith("jpeg")) {
	   ret = "image/jpeg";
	  }
	  if (fileNameTmp.endsWith("jpe")) {
	   ret = "image/jpeg";
	  }
	  if (fileNameTmp.endsWith("zip")) {
	   ret = "application/zip";
	  }
	  if (fileNameTmp.endsWith("rar")) {
	   ret = "application/rar";
	  }
	  if (fileNameTmp.endsWith("doc")) {
	   ret = "application/msword";
	  }
	  if (fileNameTmp.endsWith("ppt")) {
	   ret = "application/vnd.ms-powerpoint";
	  }
	  if (fileNameTmp.endsWith("xls")) {
	   ret = "application/vnd.ms-excel";
	  }
	  if (fileNameTmp.endsWith("html")) {
	   ret = "text/html";
	  }
	  if (fileNameTmp.endsWith("htm")) {
	   ret = "text/html";
	  }
	  if (fileNameTmp.endsWith("tif")) {
	   ret = "image/tiff";
	  }
	  if (fileNameTmp.endsWith("tiff")) {
	   ret = "image/tiff";
	  }
	  if (fileNameTmp.endsWith("pdf")) {
	   ret = "application/pdf";
	  }
	  return ret;
	 }
	 
}
