/**
 * 
 */
package com.chamago.pcrm.worktable.notice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import nl.justobjects.pushlet.core.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.SessionAttribute;
import com.chamago.pcrm.common.utils.Subcriber;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.common.utils.polling.MsgEventSource;
import com.chamago.pcrm.worktable.connect.service.SysMgtService;
import com.chamago.pcrm.worktable.notice.pojo.Notice;
import com.chamago.pcrm.worktable.notice.pojo.NoticeDetail;
import com.chamago.pcrm.worktable.notice.service.NoticeService;
import com.mysql.jdbc.StringUtils;

/**
 * @author gavin.peng
 * 
 */
@Controller
@RequestMapping("notice")
public class NoticeController {

	/**
	 * 1不是叶子节点，0 是叶子节点
	 */
	private static int LEAF = 1;
	private static Logger logger = LoggerFactory
			.getLogger(NoticeController.class);
	private static int READ_OK = 1;
	private static int READ_NO = 0;
	private static int PAGE_SIZE = 6;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private SysMgtService sysMgtService;

	
	@RequestMapping("/index")
	@AopLogModule(name=C.LOG_MODULE_NOTICE,layer=C.LOG_LAYER_CONTROLLER)
	public String noticeIndex(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return "/worktable/notice";
	}

	@RequestMapping("/getlimitnotice")
	@AopLogModule(name=C.LOG_MODULE_NOTICE,layer=C.LOG_LAYER_CONTROLLER)
	public String findNoticeListByUseridAndStatus(ModelMap map,
			HttpServletRequest request) throws Exception {

		String userid = request.getParameter("userid");
		String type = request.getParameter("type");
		if (StringUtils.isNullOrEmpty(userid)
				|| StringUtils.isNullOrEmpty(type)) {
			throw new Exception("参数userid或者type为空!");
		}

		List<Object[]> noticeList = noticeService.findNoticeByUseridAndStatus(
				userid, Integer.parseInt(type));
		PagedListHolder<Object[]> list = new PagedListHolder<Object[]>(
				noticeList);
		list.setPageSize(C.PAGINATION_SIZE);
		map.put("noticeList", list);
		return "/worktable/noticelist";
	}

	@RequestMapping("/show")
	@AopLogModule(name=C.LOG_MODULE_NOTICE,layer=C.LOG_LAYER_CONTROLLER)
	public String createNotice(ModelMap map, HttpServletRequest request)
			throws Exception {

		return "/worktable/noticemgt";
	}

	
	@RequestMapping("/create")
	@AopLogModule(name=C.LOG_MODULE_NOTICE,layer=C.LOG_LAYER_CONTROLLER)
	public String toCreate(ModelMap map, HttpServletRequest request)
			throws Exception {

		return "/worktable/newnoticemgt";
	}
	
	
	@RequestMapping("/dispatchnotice")
	@AopLogModule(name=C.LOG_MODULE_NOTICE,layer=C.LOG_LAYER_CONTROLLER)
	public String dipatchNotice(ModelMap map, HttpServletRequest request)
			throws Exception {

		return "/dispatchnotice";
	}

	/**
	 * 构建客服树结构
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberlist")
	@AopLogModule(name=C.LOG_MODULE_NOTICE,layer=C.LOG_LAYER_CONTROLLER)
	public String getMemberList(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		
		String seller = req.getParameter("seller");
		if(StringUtils.isNullOrEmpty(seller)){
			throw new Exception("参数[seller]为空 ");
		}
		List<Object[]> list = sysMgtService.getMembersBySeller(seller);

		// knowledgeService.getSubjectByParentid(parentid);
		StringBuffer json = new StringBuffer("[");
		if (list != null && list.size() > 0) {
			int i = 0;
			for (Object[] obj : list) {
				Map<String, String> param = new Hashtable<String, String>();
						param.put("id", obj[0].toString());
						param.put("name", obj[1].toString());
//						param.put("leaf", "true");
//					} else {
//						param.put("id", obj[0].toString());
//						param.put("name", obj[0].toString());
//						param.put("leaf", "false");
//					}

				JSONObject row = JSONObject.fromObject(param);
				if (i > 0) {
					json.append(",");
				}
				json.append(row.toString());
				i++;
			}
			
		}else{
			logger.info("卖家["+seller+"]目前还没有用户");
		}
		json.append("]");

		try {
			resp.getWriter().write(json.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			resp.getWriter().close();
		}
		return null;

	}

	@RequestMapping("/updatedetail")
	@AopLogModule(name=C.LOG_MODULE_NOTICE,layer=C.LOG_LAYER_CONTROLLER)
	public String updateNoticeDetail(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		final String userid = request.getParameter("userid");
		String noticeid = request.getParameter("noticeid");
		if (StringUtils.isNullOrEmpty(userid)
				|| StringUtils.isNullOrEmpty(noticeid)) {
			throw new Exception("参数userid或者noticeid为空!");
		}
		NoticeDetail nd = new NoticeDetail();
		nd.setNoticeId(noticeid);
		nd.setUserId(userid);
		nd.setRead(READ_OK);
		nd.setModified(new Date());
		try {
			noticeService.updateNoticeDetail(nd);
			new Thread(new Runnable(){
				public void run(){
					Map<String,MsgEventSource> msgMap = Subcriber.subjectI.get(Subcriber.NOTICE_MSG);
					try {
						int count = noticeService.findNoReadNoticeCount(userid);
						MsgEventSource eventSource = new MsgEventSource(userid,count);
						msgMap.put(userid, eventSource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			response.getWriter().write("{\"result\":\"true\"}");
		} catch (Exception e) {
			logger.error("更新状态通知失败!");
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"false\"}");
		} finally {
			response.getWriter().close();
		}
		logger.info("更新通知成功 ");
		return null;

	}

	@RequestMapping("/add")
	@AopLogModule(name=C.LOG_MODULE_NOTICE,layer=C.LOG_LAYER_CONTROLLER)
	public String insertNotice(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String userids = request.getParameter("userids");
		String creator = request.getParameter("creator");
		if (StringUtils.isNullOrEmpty(title)) {
			throw new Exception("参数title为空!");
		}
		if (StringUtils.isNullOrEmpty(content)) {
			throw new Exception("参数content为空!");
		}

		if (StringUtils.isNullOrEmpty(userids)) {
			throw new Exception("参数userids为空!");
		}
		
		
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setId(ObjectId.get().toString());

		notice.setCreator(creator);
		notice.setCreated(new Date());
		notice.setModified(new Date());

		Collection<NoticeDetail> detailCollection = new ArrayList<NoticeDetail>();
		final String[] userArry = userids.split(",");
		for (String userid : userArry) {
			NoticeDetail noticeDetail = new NoticeDetail();
			noticeDetail.setUserId(userid);
			noticeDetail.setNoticeId(notice.getId());
			noticeDetail.setRead(READ_NO);
			noticeDetail.setCreated(new Date());
			noticeDetail.setModified(new Date());
			detailCollection.add(noticeDetail);
		}
		Map<String, String> param = new Hashtable<String, String>();
		try {
			noticeService.insertNotice(notice, detailCollection);
			new Thread(new Runnable(){
				public void run(){
						Map<String,MsgEventSource> msgMap = Subcriber.subjectI.get(Subcriber.NOTICE_MSG);
						try {
							for (String userid : userArry) {
								int count = noticeService.findNoReadNoticeCount(userid);
								MsgEventSource eventSource = new MsgEventSource(userid,count);
								
									msgMap.put(userid, eventSource);
								
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			}).start();
			param.put("result", "true");
		} catch (Exception e) {
			logger.error("新增通知失败!");
			e.printStackTrace();
			param.put("result", "false");
		}
		try {
			String rs = param.get("result");
			if ("true".equals(rs)) {
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
		logger.info("新增通知成功 ");
		return null;
	}

	@RequestMapping("/adddetail")
	@AopLogModule(name=C.LOG_MODULE_NOTICE,layer=C.LOG_LAYER_CONTROLLER)
	public String insertNoticeDetail(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userids = request.getParameter("userid");
		String noticeid = request.getParameter("noticeid");
		if (StringUtils.isNullOrEmpty(userids)
				|| StringUtils.isNullOrEmpty(noticeid)) {
			throw new Exception("参数userid或者noticeid为空!");
		}
		Collection<NoticeDetail> detailCollection = new ArrayList<NoticeDetail>();
		String[] userArry = userids.split(",");
		if (!noticeService.findNoticeByNoticeid(noticeid)) {
			logger.info("通知ID:" + noticeid + "对应的通知不存在!");
			return "/worktable/noticeliswt";
		}
		for (String userid : userArry) {
			NoticeDetail noticeDetail = new NoticeDetail();
			noticeDetail.setUserId(userid);
			noticeDetail.setNoticeId(noticeid);
			noticeDetail.setRead(READ_NO);
			noticeDetail.setCreated(new Date());
			noticeDetail.setModified(new Date());
			detailCollection.add(noticeDetail);
		}
		try {
			noticeService.batchInsertNoticeDetail(detailCollection);
			response.getWriter().write("{\"result\":\"true\"}");
		} catch (Exception e) {
			logger.error("批量新增通知明细失败!");
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"false\"}");
		}
		return null;
	}

	@RequestMapping("/getnoreadcount")
	@AopLogModule(name=C.LOG_MODULE_NOTICE,layer=C.LOG_LAYER_CONTROLLER)
	public String getNoReadNoticeCount(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String userid = request.getParameter("userid");
		if (StringUtils.isNullOrEmpty(userid)) {
			throw new Exception("参数userid为空!");
		}
		try {
			int count = noticeService.findNoReadNoticeCount(userid);

			response.getWriter().write("{\"count\":\"" + count + "\"}");
		} catch (Exception e) {
			logger.error("获取用户未读通知个数出错");
			e.printStackTrace();
			response.getWriter().write("{\"count\":\"0\"}");
		} finally {
			response.getWriter().close();
		}
		return null;
	}


	@RequestMapping("/noticepage")
	@AopLogModule(name=C.LOG_MODULE_NOTICE,layer=C.LOG_LAYER_CONTROLLER)
	public String fidndNoticePageListByUserid(ModelMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setHeader("ContentType", "text/json");
		String userid = request.getParameter("userid");
		String page = request.getParameter("page");
		String type = request.getParameter("type");
		if (StringUtils.isNullOrEmpty(userid)) {
			throw new Exception("参数userid为空!");
		}
		if (StringUtils.isNullOrEmpty(type)) {
			throw new Exception("参数type为空!");
		}
		if (StringUtils.isNullOrEmpty(page)) {
			throw new Exception("参数page为空!");
		}

		try {
			StringBuilder json = new StringBuilder("");
			
			//只查未读的通知
			List<Object[]> noticeList = noticeService.findNoticeByUseridAndStatus(userid, Integer.parseInt(type));
					//.findNoticeByUserid(userid);
			if (noticeList != null && noticeList.size() > 0) {
				PagedListHolder<Object[]> list = new PagedListHolder<Object[]>(
						noticeList);
				int currentPage = Integer
						.valueOf(request.getParameter("cpage"));
				list.setPage(currentPage);
				Utils.pagination(PAGE_SIZE, list, request);

				int curpage = list.getPage();
				int pageCount = list.getPageCount();

				Iterator it = list.getPageList().iterator();
				// 显示每页的内容
				json.append("{\"page\":\"" + curpage + "\",\"pagecount\":\""
						+ pageCount + "\",\"row\":[");
				int i = 0;
				while (it.hasNext()) {
					Object[] obj = (Object[]) it.next();
					Map<String, String> param = new Hashtable<String, String>();
					String df = "";
					if(obj[4]!=null){
						Date created = Utils.StringToDate(obj[4].toString(),  "yyyy-MM-dd hh:mm:ss");
						df = Utils.DateToString(created, "yyyy-MM-dd hh:mm:ss");
					}
					param.put("id", String.valueOf(obj[0]));
					param.put("title", String.valueOf(obj[1]));
					param.put("content", String.valueOf(obj[2]));
					param.put("datetime", df);
					param.put("creator", String.valueOf(obj[3]));
					param.put("read", String.valueOf(obj[5]));
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

}
