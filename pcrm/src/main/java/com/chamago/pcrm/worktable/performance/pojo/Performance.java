/**
 * 
 */
package com.chamago.pcrm.worktable.performance.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 
 * 业绩指标
 * @author gavin.peng
 * 
 *
 */
public class Performance extends BasePojo implements Comparable<Performance>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1188639441307605688L;
	
	
	private String id;
	private String userId;
	private int receivenNum;
	private double totalAmount;
	private int totalBuyers;
	private double customResult;
	private int avgRespTime;
	
	private int noreplyNums;
	private int chatpeerNums;
	private Date date;
	private Date created;
	private Date modified;
	private int ranking;
	private String nick;
	private int type;
	private long groupId;
	private float customPrice;
	private boolean equal = false;
	
	public float getCustomPrice() {
		return customPrice;
	}
	public void setCustomPrice(float customPrice) {
		this.customPrice = customPrice;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getTotalBuyers() {
		return totalBuyers;
	}
	public void setTotalBuyers(int totalBuyers) {
		this.totalBuyers = totalBuyers;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public double getCustomResult() {
		return customResult;
	}
	public void setCustomResult(double customResult) {
		this.customResult = customResult;
	}
	public int getAvgRespTime() {
		return avgRespTime;
	}
	public void setAvgRespTime(int avgRespTime) {
		this.avgRespTime = avgRespTime;
	}
	public int getNoreplyNums() {
		return noreplyNums;
	}
	public void setNoreplyNums(int noreplyNums) {
		this.noreplyNums = noreplyNums;
	}
	public int getChatpeerNums() {
		return chatpeerNums;
	}
	public void setChatpeerNums(int chatpeerNums) {
		this.chatpeerNums = chatpeerNums;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getReceivenNum() {
		return receivenNum;
	}
	public void setReceivenNum(int receivenNum) {
		this.receivenNum = receivenNum;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public boolean isEqual() {
		return equal;
	}
	public void setEqual(boolean equal) {
		this.equal = equal;
	}
	public int compareTo(Performance o) {
		// TODO Auto-generated method stub
		if(o == null){
			return 1;
		}
		
		if(this.getTotalAmount()>o.getTotalAmount()){
			return 1;
		}else if(this.getTotalAmount()<o.getTotalAmount()){
			return -1;
		}else{ 
			
			float ss = 0;
			float tss = 0;
			if(o.getReceivenNum()>0){
				ss = (float)o.getTotalBuyers()/o.getReceivenNum();
			}
			if(this.getReceivenNum()>0){
				tss =(float)this.getTotalBuyers()/this.getReceivenNum();
			}
			if(tss>ss){
				return 1;
			}else if(tss==ss){
				if(this.getReceivenNum()>o.getReceivenNum()){
					return 1; 
				}else if(this.getReceivenNum()<o.getReceivenNum()){
					return -1;
				}else{
					
					if(this.getNoreplyNums()>o.getNoreplyNums()){
						return -1;
					}else if(this.getNoreplyNums()>o.getNoreplyNums()){
						return 1;
					}else{
						if(this.getAvgRespTime()>o.getAvgRespTime()){
							return 1;
						}else if(this.getAvgRespTime()<o.getAvgRespTime()){
							return -1;
						}else{
							this.equal = true;
							o.equal = true;
							return 0;
						}
					}
					
				}
				
			}
			else{
				return -1;
			}
		}
	}
}
