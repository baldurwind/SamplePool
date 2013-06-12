package com.chamago.pcrm.worktable.notice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.worktable.notice.pojo.NoticeDetail;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})
public class NoticeServiceImplTest {
	
	@Autowired
	private NoticeService noticeService;
	
	@Test
	public void testBatchInsertNoticeDetail(){
		Collection<NoticeDetail> detailCollection = new ArrayList<NoticeDetail>();
		String userids = "6001,5002,5003";
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
		try{
			noticeService.batchInsertNoticeDetail(detailCollection);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	

}
