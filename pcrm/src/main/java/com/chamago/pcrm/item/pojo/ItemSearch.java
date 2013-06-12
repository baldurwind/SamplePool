package com.chamago.pcrm.item.pojo;

/**
 * 商品搜索
 * @author James.wang
 */
public class ItemSearch {

	//Item id
	private String numid;
	
	//商品名称
	private String title;
	
	//商品价格
	private String price;
	
	//收藏量
	private String collect;
	
	//上架时间
	private String newSpecies;
	
	//销售量
	private String salesVol;
	
	//库存
	private String num;
	
	//图片地址
	private String picUrl;

	public String getNumid() {
		return numid;
	}

	public void setNumid(String numid) {
		this.numid = numid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}

	public String getNewSpecies() {
		return newSpecies;
	}

	public void setNewSpecies(String newSpecies) {
		this.newSpecies = newSpecies;
	}

	public String getSalesVol() {
		return salesVol;
	}

	public void setSalesVol(String salesVol) {
		this.salesVol = salesVol;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}