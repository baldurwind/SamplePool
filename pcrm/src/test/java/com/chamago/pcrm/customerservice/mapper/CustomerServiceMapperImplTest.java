package com.chamago.pcrm.customerservice.mapper;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.customerservice.pojo.CSIDetail;
import com.chamago.pcrm.customerservice.pojo.CSIStatus;
import com.chamago.pcrm.customerservice.pojo.CSIType;
import com.chamago.pcrm.customerservice.pojo.CustomerService;
import com.chamago.pcrm.customerservice.pojo.CustomerServiceIssues;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath*:ac.xml"})
public class CustomerServiceMapperImplTest {
	
	@Autowired
	private CustomerServiceMapper customerServiceMapper;

	@Test
	public void testGetCSITypeListBySellerNick() {
		List<CSIType> types = getCustomerServiceMapper().getCSITypeListBySellerNick("芳蕾玫瑰粉粉旗舰店");
		Assert.assertTrue("Test:", 1 == types.size());
	//	Assert.assertTrue("Test:", 1 == types.get(0).getId().intValue());
		Assert.assertTrue("Test:", "商品损坏".equals(types.get(0).getName()));
	}
	
	@Test
	public void testGetCSIPriorityListBySellerNick() {
	 /*	List<CSIPriority> csiPriorities = getCustomerServiceMapper().getCSIPriorityListBySellerNick("芳蕾玫瑰粉粉旗舰店");
		Assert.assertTrue("Test:", 1 == csiPriorities.size());
		Assert.assertTrue("Test:", 1 == csiPriorities.get(0).getId().intValue());
		Assert.assertTrue("Test:", "紧急".equals(csiPriorities.get(0).getName()));*/
	}
	
	@Test
	public void testSaveCustomerServiceIssueInfo() {
		/*//添加测试
		CustomerServiceIssues customerServiceIssues = new CustomerServiceIssues();
		customerServiceIssues.setBuyerNick("wanghe_taobao");
		customerServiceIssues.setSellerNick("芳蕾玫瑰粉粉旗舰店");
		customerServiceIssues.setTradeId("123456789001234567");
		customerServiceIssues.setType(1);
		customerServiceIssues.setPriority(1);
		customerServiceIssues.setTitle("商品损坏");
		customerServiceIssues.setContent("商品损坏");
		customerServiceIssues.setNick("良无限_玫瑰花");
		customerServiceIssues.setFilePath("D:/abcdefg.jpg");
		customerServiceIssues.setStatus(1);
		boolean falg = true;
		try {
			getCustomerServiceMapper().saveCustomerServiceIssueInfo(customerServiceIssues);
		} catch(Exception e) {
			falg = false;
			fail("saveCustomerServiceIssueInfo error " + e.getMessage());
		}
		
		if(falg) {
			//判断添加是否成功
			Object[] o =  getCustomerServiceMapper().getCustomerServiceIssueByCSIId(customerServiceIssues.getTradeId());	
			if(o == null) {
				fail("getCustomerServiceIssueByCSIId is null ");
			} else {
				Assert.assertTrue("Test:", customerServiceIssues.getTradeId().equals(o[0].toString()));
				Assert.assertTrue("Test:", "商品损坏".equals(o[1].toString()));
			}
		}*/
	}
	
	@Test
	public void testSaveCSIDetailInfo() {
		//添加测试
		/*CSIDetail csiDetail = new CSIDetail();
		csiDetail.setId(ObjectId.get().toString());
		csiDetail.setCsiId("123456789001234567");
		csiDetail.setNick("良无限_非洲菊");
		csiDetail.setStatus(2);
		csiDetail.setRemark("已处理");
		csiDetail.setFilePath("D:/1222.jpg");
		boolean falg = true;
		try {
			getCustomerServiceMapper().saveCSIDetailInfo(csiDetail);
		} catch(Exception e) {
			falg = false;
			fail("saveCSIDetailInfo error " + e.getMessage());
		}
		
		if(falg) {
			//判断添加是否成功
			CSIDetail csiDetail2 = getCustomerServiceMapper().getCsiDetailByCSId(csiDetail.getId());
			if(csiDetail2 == null) {
				fail("getCsiDetailByCSId is null");
			} else {
				Assert.assertTrue("Test:", csiDetail2.getId().equals(csiDetail2.getId()));
				Assert.assertTrue("Test:", "良无限_非洲菊".equals(csiDetail2.getNick()));
			}
		}*/
	}
	
	@Test
	public void testGetCustomerServiceIssueByCSIId() {
		Object[] o = getCustomerServiceMapper().getCustomerServiceIssueByCSIId("1234567890098");
		if(o == null) {
			fail("getCustomerServiceIssueByCSIId is null");
		} else {
			Assert.assertTrue("Test:", "1234567890098".equals(o[0].toString()));
			Assert.assertTrue("Test:", "商品损坏".equals(o[1].toString()));
		}
	}
	
	@Test
	public void testGetCustomerServiceIssueCountByTradeId() {
	/*	Integer count = getCustomerServiceMapper().getCustomerServiceIssueCountByTradeId("123456789001234567");
		Assert.assertTrue("Test:", 1 == count.intValue());*/
	}

	@Test
	public void testQueryCustomerServiceIssuesList() {
	/*	Map<String, String> map = new HashMap<String, String>();
		map.put("tradeId", "123456789001234567");
		List<Object[]> o = getCustomerServiceMapper().queryCustomerServiceIssuesList(map);
		Assert.assertTrue("Test:", 1 == o.size());
		Assert.assertTrue("Test:", "123456789001234567".equals(o.get(0)[0].toString()));*/
		
	}
	
	@Test
	public void testQueryCSIStatusList() {
		List<CSIStatus> csiStatus = getCustomerServiceMapper().queryCSIStatusList();
		Assert.assertTrue("Test:", 5 == csiStatus.size());
	}
	
	@Test
	public void testUpdateCSIStatus() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("status", "3");
		map.put("id", "26");
		boolean falg = true;
		try {
			//修改CSI　Status
			getCustomerServiceMapper().updateCSIStatus(map);
		} catch(Exception e) {
			falg = false;
			fail("updateCSIStatus error " + e.getMessage());
		}
		
		if(falg) {
			//验证是否修改成功
			Object[] o = getCustomerServiceMapper().getCustomerServiceIssueByCSIId("1234567890098");
			if( o == null) {
				fail("getCustomerServiceIssueByCSIId is null");
			} else {
				Assert.assertTrue("Test:", "1234567890098".equals(o[0].toString()));
			}
		}
	}
	

	@Test
	public void testQueryCustomerServiceByType() {
		List<CustomerService> customerServices = getCustomerServiceMapper().queryCustomerServiceByType(1);
		Assert.assertTrue("Test:", 2 == customerServices.size());
		Assert.assertTrue("Test:", 2 == customerServices.get(0).getId().intValue());
		Assert.assertTrue("Test:", "良无限_野菊花".equals(customerServices.get(0).getNick()));
	}
	
	@Test
	public void testGetCSIdByCSNick() {
		Integer id = getCustomerServiceMapper().getCSIdByCSNick("良无限_野菊花");
		Assert.assertTrue("Test:", 2 == id.intValue());
	}
	
	@Test
	public void testSaveAdmeasure() {
		Map<String, String> map = new HashMap<String, String>();
		boolean falg = true;
		//保存分配
		try {
			map.put("csNick", "良无限_野菊花");
			map.put("id", "26");
			getCustomerServiceMapper().saveAdmeasure(map);
		} catch(Exception e) {
			falg = false;
			fail("saveAdmeasure error " + e.getMessage());
		}
		
		if(falg) {
			Object[] o = getCustomerServiceMapper().getCustomerServiceIssueByCSIId("1234567890098");
			if(o == null) {
				fail("getCustomerServiceIssueByCSIId is null");
				Assert.assertTrue("Test:", "1234567890098".equals(o[0].toString()));
				Assert.assertTrue("Test:", "良无限_野菊花".equals(o[15].toString()));
			}
		}
	}
	
	@Test
	public void testGetEndCSIDetailOperateByCSIId() {
		String id = getCustomerServiceMapper().getEndCSIDetailOperateByCSIId("1234567890098");
		Assert.assertTrue("Test:", "4f41dd436813e01ab07a6c98".equals(id));
	}
	
	@Test
	public void testUpdateCSIDetailDisposalTime() {
		try {
			getCustomerServiceMapper().updateCSIDetailDisposalTime("55");
		} catch(Exception e) {
			fail("updateCSIDetailDisposalTime error " + e.getMessage());
		}
	}
	
	@Test
	public void testUpdateCSIDisposalNickISNUll() {
		try {
			getCustomerServiceMapper().updateCSIDisposalNickISNUll("123456789001234567");
		} catch(Exception e) {
			fail("updateCSIDisposalNickISNUll error " + e.getMessage());
		}
	}
	
	@Test
	public void testQueryCSIStatusByTipNOTNull() {
		List<CSIStatus> csiStatus = getCustomerServiceMapper().queryCSIStatusByTipNOTNull();
		Assert.assertTrue("Test:", 3 == csiStatus.size());
		Assert.assertTrue("Test:", "等待处理".equals(csiStatus.get(0).getName()));
	}
	
	@Test
	public void testQueryEndCSIDetailByCSIIds() {
		List<CSIDetail> details = getCustomerServiceMapper().queryEndCSIDetailByCSIIds("1234567890098");
		Assert.assertTrue("Test:", 1 == details.size());
		Assert.assertTrue("Test:", "4f41dd436813e01ab07a6c98".equals(details.get(0).getId()));
	}
	
	@Test
	public void testUpdateCSIDetailLightType() {
		boolean flag = true;
		try {
			getCustomerServiceMapper().updateCSIDetailLightType("55", 2);
		} catch(Exception e) {
			flag = false;
			fail("updateCSIDetailLightType error " + e.getMessage());
		}
		
		if(flag) {
			 CSIDetail csiDetail = getCustomerServiceMapper().getCsiDetailByCSId("55");
			 if(csiDetail == null) {
				 fail("getCsiDetailByCSId is null ");
				 Assert.assertTrue("Test:", "55".equals(csiDetail.getId()));
				 Assert.assertTrue("Test:", 2 == csiDetail.getLightType().intValue());
			 }
		}
	}
	
	public CustomerServiceMapper getCustomerServiceMapper() {
		return customerServiceMapper;
	}

	public void setCustomerServiceMapper(CustomerServiceMapper customerServiceMapper) {
		this.customerServiceMapper = customerServiceMapper;
	}
}