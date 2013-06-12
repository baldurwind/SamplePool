/**
 * 
 */
package com.chamago.pcrm.worktable.notice.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.worktable.notice.mapper.NoticeMapper;
import com.chamago.pcrm.worktable.notice.pojo.Notice;
import com.chamago.pcrm.worktable.notice.pojo.NoticeDetail;
import com.chamago.pcrm.worktable.notice.service.NoticeService;
import com.chamago.pcrm.worktable.performance.mapper.EserviceMapper;
import com.mysql.jdbc.StringUtils;


/**
 * @author gavin.peng
 *
 */
@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private EserviceMapper eserviceMapper;

	
	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.notice.service.NoticeService#findNoticeByUserid(long)
	 */
	public List<Object[]> findNoticeByUserid(String userid) {
		// TODO Auto-generated method stub
		return noticeMapper.findNoticeByUserid(userid);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.notice.service.NoticeService#findNoticeByUseridAndStatus(long, int)
	 */
	public List<Object[]> findNoticeByUseridAndStatus(String userid, int status) {
		// TODO Auto-generated method stub
		return noticeMapper.findNoticeByUseridAndStatus(userid, status);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.notice.service.NoticeService#insertNotice(com.chamago.pcrm.worktable.notice.pojo.Notice)
	 */
	public int insertNotice(Notice notice) {
		// TODO Auto-generated method stub
		return noticeMapper.insertNotice(notice);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.notice.service.NoticeService#insertNoticeDetail(com.chamago.pcrm.worktable.notice.pojo.NoticeDetail)
	 */
	public int insertNoticeDetail(NoticeDetail noticeDetail) {
		// TODO Auto-generated method stub
		return noticeMapper.insertNoticeDetail(noticeDetail);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.notice.service.NoticeService#updateNoticeDetail(com.chamago.pcrm.worktable.notice.pojo.NoticeDetail)
	 */
	public int updateNoticeDetail(NoticeDetail noticeDetail) {
		// TODO Auto-generated method stub
		return noticeMapper.updateNoticeDetail(noticeDetail);
	}

	public int batchInsertNoticeDetail(
			Collection<NoticeDetail> noticeDetailCollection)  throws Exception{
		// TODO Auto-generated method stub 
		for(NoticeDetail detail:noticeDetailCollection){
			noticeMapper.insertNoticeDetail(detail);
		}
		return 1;
	}

	public boolean findNoticeByNoticeid(String noticeid) throws Exception{
		// TODO Auto-generated method stub
		if(noticeid ==null || noticeid.length() <=0){
			throw new Exception("参数用户ID为空");
		}
		int rs = noticeMapper.findNoticeByNoticeId(noticeid);
		if(rs >0){
			return true;
		}
		return false;
	}

	public int findNoReadNoticeCount(String userid) throws Exception{
		// TODO Auto-generated method stub 
		if(userid ==null || userid.length() <=0){
			throw new Exception("参数用户ID为空");
		}
		return noticeMapper.findNoReadNoticeCountByUserid(userid);
	}


	public List<Object[]> getGroups(String nick) {
		// TODO Auto-generated method stub
		if(StringUtils.isNullOrEmpty(nick)){
			return null;
		}
		return eserviceMapper.getGroupsByNick(nick);
	}

	public List<Object[]> getGroupMembers(String groupid) {
		// TODO Auto-generated method stub
		if(groupid ==null){
			return null;
		}
		return eserviceMapper.getMembersByGroupId(groupid);
	}

	public int insertNotice(Notice notice, Collection<NoticeDetail> detailCollection) throws Exception {
		// TODO Auto-generated method stub
		
		try{
			this.insertNotice(notice);
			this.batchInsertNoticeDetail(detailCollection);
		}catch(Exception e){
			//logger.error("批量新增通知明细失败!");
			e.printStackTrace();
		}

		
		return 0;
	}

}
