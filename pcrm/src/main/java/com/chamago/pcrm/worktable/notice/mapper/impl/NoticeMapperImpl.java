package com.chamago.pcrm.worktable.notice.mapper.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.chamago.pcrm.common.utils.MemCached;
import com.chamago.pcrm.worktable.notice.mapper.NoticeMapper;
import com.chamago.pcrm.worktable.notice.pojo.Notice;
import com.chamago.pcrm.worktable.notice.pojo.NoticeDetail;



@SuppressWarnings("unchecked")
public class NoticeMapperImpl extends SqlSessionDaoSupport implements
		NoticeMapper {

	private static Logger logger = LoggerFactory.getLogger(NoticeMapperImpl.class);
	
	//@MemCached
	public List<Object[]> findNoticeByUserid(String userid) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("NoticeMapper.findNoticeByUserid", userid);
	}

	//@MemCached
	public List<Object[]> findNoticeByUseridAndStatus(String userid, int status) {
		// TODO Auto-generated method stub
		Assert.notNull(userid);
		Assert.notNull(Integer.valueOf(status));
		Map<String,Object> map = new HashMap<String,Object>();
		int read = status;
		map.put("userid", userid);
		map.put("read", read);
		return getSqlSession().selectList("NoticeMapper.findNoticeByUseridAndStatus", map);
	}

	public int insertNotice(Notice notice) {
		// TODO Auto-generated method stub
		Assert.notNull(notice);
		return getSqlSession().insert("NoticeMapper.insertNotice", notice);
	}

	public int updateNoticeDetail(NoticeDetail noticeDetail) {
		// TODO Auto-generated method stub
		Assert.notNull(noticeDetail);
		return getSqlSession().update("NoticeMapper.updateNoticeDetail", noticeDetail);
	}

	//@MemCached
	public int insertNoticeDetail(NoticeDetail noticeDetail) {
		Assert.notNull(noticeDetail);
		return getSqlSession().insert("NoticeMapper.insertNoticeDetail", noticeDetail);
	}

	public int batchInsertNoticeDetail(Collection<NoticeDetail> detailCollection) {
		// TODO Auto-generated method stub
			for(NoticeDetail detail:detailCollection){
				insertNoticeDetail(detail);
			}
			return 1;
	}

	public int findNoticeByNoticeId(String noticeid) {
		// TODO Auto-generated method stub
		List<Object[]>  list = getSqlSession().selectList("NoticeMapper.findNoticeByNoticeid", noticeid);
		if(list!=null&&list.size()>0){
			return 1;
		}
		return -1;
	}

	public int findNoReadNoticeCountByUserid(String userid) {
		// TODO Auto-generated method stub
		List<Object[]>  list = getSqlSession().selectList("NoticeMapper.findNoReadeNoticeCountByUserid", userid);
		if(list!=null&&list.size()>0){
			Object[] obj = list.get(0);
			if(obj[0]!=null){
				return Integer.parseInt(obj[0].toString());
			}
		}
		return 0;
	}

	public List<Notice> findAllNotice() {
		// TODO Auto-generated method stub
		List<Notice>  list = getSqlSession().selectList("NoticeMapper.findAllNotice");
		return list;
	}

}
