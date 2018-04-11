package com.zdmoney.credit.framework.vo.common;

import java.io.Serializable;
/**
 * 核心返回当前人的债券信息
 * @author YM10180
 *
 */
public class BaseLoanStatusVo implements Serializable{

	
	private String createTime;
	private String id;
	private String loanNo;
	private String loanState;
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getLoanState() {
		return loanState;
	}
	public void setLoanState(String loanState) {
		this.loanState = loanState;
	}
	
	
	
}
