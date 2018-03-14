package com.ipay.bean;

/**
 * 契约单元
 * 
 * @author visen
 *
 */
public class Sub{
	private Integer waresId = null;
	private Integer feeType = null;
	private Integer leftCount = null;
	private String endTime = null;
	private Integer subStatus = null;
	public Integer getWaresId() {
		return waresId;
	}
	public void setWaresId(Integer waresId) {
		this.waresId = waresId;
	}
	public Integer getFeeType() {
		return feeType;
	}
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	public Integer getLeftCount() {
		return leftCount;
	}
	public void setLeftCount(Integer leftCount) {
		this.leftCount = leftCount;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(Integer subStatus) {
		this.subStatus = subStatus;
	}
	
	
}