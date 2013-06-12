package com.chamago.pcrm.common.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * 公共分页类
 */
public class PageModel {

	// 所有的记录数
	private int totalRecords;

	// 查询的结果集
	private List<? extends Object> list;

	// 第几页
	private int pageNo;

	// 每页多少条数据
	private int pageSize;

	public List<? extends Object> getList() {
		if(list == null) {
			return new ArrayList<Object>();
		}
		return list;
	}

	public void setList(List<? extends Object> list) {
		this.list = list;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageNo() {
		if(getTotalRecords() < getPageSize()) {
			return 1;
		}
		if(pageNo > getTotalPages()) {
			return getTotalPages();
		}
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPages() {
		return (totalRecords + pageSize - 1) / pageSize;
	}

	public int getTopPageNo() {
		return 1;
	}

	public int getPreviousPageNo() {
		if (pageNo == 1) {
			return 1;
		}
		return pageNo - 1;
	}

	public int getNextPageNo() {
		if (pageNo == getBottomPageNo()) {
			return getBottomPageNo();
		}
		return pageNo + 1;
	}

	public int getBottomPageNo() {
		return getTotalPages();
	}
}