/**
 * 
 */
package com.chamago.pcrm.worktable.notice.service;

import java.util.Collection;
import java.util.List;

import com.chamago.pcrm.worktable.notice.pojo.Notice;
import com.chamago.pcrm.worktable.notice.pojo.NoticeDetail;


/**
 * 通知Service层接口
 * @author gavin.peng
 *
 */
public interface NoticeService {
	
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
	 * 新建通知和明细
	 * @param notice 通知实例
	 * @return -1 失败，否则成功 
	 */
	int insertNotice(Notice notice,Collection<NoticeDetail> detailCollection) throws Exception;
	
	/**
	 * 新建通知明细
	 * @param noticeDetail 明细实例
	 * @return -1 失败，否则成功 
	 */
	int insertNoticeDetail(NoticeDetail noticeDetail);
	
	/**
	 * 新建通知明细
	 * @param noticeDetail 明细实例
	 * @return -1 失败，否则成功 
	 */
	int batchInsertNoticeDetail(Collection<NoticeDetail> noticeDetailCollection) throws Exception;
	
	/**
	 * 更新通知明细状态
	 * @param noticeDetail 目标实例 
	 * @return -1 失败，否则成功
	 */
	int updateNoticeDetail(NoticeDetail noticeDetail);
	
	/**
	 * 根据通知ID查找通知
	 * @param noticeid  通知id
	 * @return 存在 true 否则 false
	 */
	boolean findNoticeByNoticeid(String noticeid) throws Exception;
	
	/**
	 * 查找用户未读的通知条数
	 * @param userid 用户ID
	 * @return 条数
	 */
	int findNoReadNoticeCount(String userid) throws Exception;
	
	/**
	 * 获取店铺的客服组
	 * @param nick 店铺名称
	 * @return List<Object[]>
	 */
	List<Object[]> getGroups(String nick);
	
	/**
	 * 获取客服组的成员 
	 * @param groupid 组ID
	 * @return List<Object[]>
	 */
	List<Object[]> getGroupMembers(String groupid);

}
