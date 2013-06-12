/**
 * 
 */
package com.chamago.pcrm.worktable.notice.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.worktable.notice.pojo.Notice;
import com.chamago.pcrm.worktable.notice.pojo.NoticeDetail;

/**
 * @author gavin.peng
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})
public class NoticeMapperImplTest {
	
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Test
	public void testInsertNotice(){
//		for(int i=0;i<=5;i++){
//			Notice obj = new Notice();
//			obj.setId(ObjectId.get().toString());
//			obj.setContent("test create notice");
//			obj.setCreator(2001l);
//			obj.setTitle("test");
//			obj.setCreated(new Date());
//			obj.setModified(new Date());
//			int rs = noticeMapper.insertNotice(obj);
//			System.out.println("insert notice result :"+rs);
//		}
		Notice obj1 = null;
		int rs = noticeMapper.insertNotice(obj1);
		
	}

	
	//@Test
	public void testInsertNoticeDetail(){
		for(int i=0;i<10;i++){
			NoticeDetail nd = new NoticeDetail();
			nd.setUserId("1001l");
			long noticeid = 1003+i;
			nd.setNoticeId(String.valueOf(noticeid));
			nd.setRead(0);
			nd.setCreated(new Date());
			int rs = noticeMapper.insertNoticeDetail(nd);
			System.out.println("insert noticeDetail result :"+rs);
		}
		
	}
	
	@Test
	public void testUpdateNoticeDetail(){
		NoticeDetail nd = new NoticeDetail();
		nd.setNoticeId("1005");
		nd.setUserId("1001");
		nd.setRead(1);
		nd.setModified(new Date());
		int rs = noticeMapper.updateNoticeDetail(nd);
		System.out.println("rs :"+rs);
	}
	
	@Test
	public void testFindNoticeByUserid(){
		List<Object[]> list = noticeMapper.findNoticeByUserid("1001");
		Object[] notice = list.get(0);
		for(int i=0;i<notice.length;i++){
			System.out.println(notice[i]);
		}
	}
	
	@Test
	public void testBatchInsertNoticeDetail(){
		Collection<NoticeDetail> detailCollection = new ArrayList<NoticeDetail>();
		String userids = "7001,3002,3003";
		String noticeid = "3001";
		String[] userArry = userids.split(",");
		for(String userid:userArry){
			NoticeDetail noticeDetail = new NoticeDetail();
			noticeDetail.setUserId(userid);
			noticeDetail.setNoticeId(noticeid);
			noticeDetail.setRead(0);
			noticeDetail.setCreated(new Date());
			noticeDetail.setModified(new Date());
			detailCollection.add(noticeDetail);
		}
		int rs = noticeMapper.batchInsertNoticeDetail(detailCollection);
		Assert.assertEquals("测试成功", 1, rs);
	}
	
	@Test
	public void testFindNoticeByNoticeId(){
		String noticeid = "1001";
		int rs = noticeMapper.findNoticeByNoticeId(noticeid);
		Assert.assertEquals("测试成功", 1, rs);
		
		String noticeid1 = "9001";
		rs = noticeMapper.findNoticeByNoticeId(noticeid1);
		Assert.assertEquals("测试失败", -1, rs);
		
	}
	
	@Test
	public void testFindNoReadNoticeCountByUserid(){
		String userid = "1001";
		int rs = noticeMapper.findNoReadNoticeCountByUserid(userid);
		System.out.println("共有"+rs+"条未读");
		
	}
	
	@Test
	public void testFindAllNotice(){
		List<Notice> list = noticeMapper.findAllNotice();
		for(Notice n:list){
			System.out.println(n.getId());
		}
	}
	
	@Test
	public void testFindNoticeByUseridAndStatus(){
		List<Object[]> list = noticeMapper.findNoticeByUseridAndStatus("1001", 1);
		Assert.assertEquals("test", 1, list.size());
		
	}
}
