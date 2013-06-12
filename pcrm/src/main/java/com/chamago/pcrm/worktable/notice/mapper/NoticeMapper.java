/**
 * 
 */
package com.chamago.pcrm.worktable.notice.mapper;

import java.util.Collection;
import java.util.List;

import com.chamago.pcrm.worktable.notice.pojo.Notice;
import com.chamago.pcrm.worktable.notice.pojo.NoticeDetail;


/**
 * 
 * 通知持久层接口
 * 
 * @author gavin.peng
 *
 */
public interface NoticeMapper {
	
	/**
	 * 根据用户查找对应的通知
	 * @param userid 用户ID
	 * @return 通知有序集合
	 */
	List<Object[]> findNoticeByUserid(String userid);
	
	/**
	 * 根据用户ID和是否已读的状态查找通知
	 * @param userid 用户ID
	 * @param status 0 未读，1 已读 
	 * @return
	 */
	List<Object[]> findNoticeByUseridAndStatus(String userid,int status);
	
	/**
	 * 新建通知
	 * @param notice 通知实例
	 * @return -1 失败，否则成功 
	 */
	int insertNotice(Notice notice);
	
	/**
	 * 新建通知明显
	 * @param noticeDetail 明细实例
	 * @return -1 失败，否则成功 
	 */
	int insertNoticeDetail(NoticeDetail noticeDetail);
	
	
	/**
	 * 批量新建通知明显
	 * @param noticeDetail 明细实例
	 * @return -1 失败，否则成功 
	 */
	int batchInsertNoticeDetail(Collection<NoticeDetail> detailCollection);
	
	/**
	 * 更新通知明细状态
	 * @param noticeDetail 目标实例 
	 * @return -1 失败，否则成功
	 */
	int updateNoticeDetail(NoticeDetail noticeDetail);
	
	
	/**
	 * 根据通知ID找通知
	 */ 
	int findNoticeByNoticeId(String noticeid);
	  
	/**
	 * 查找用户未读的通知树 
	 * @param userid 用户ID
	 * @return 未读他通知的个数 
	 */
	int findNoReadNoticeCountByUserid(String userid);
	
	
	
	
	List<Notice> findAllNotice();
	
}
