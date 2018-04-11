package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

/**
 * <p>Description:功能号900002 请求VO</p>
 * @uthor YM10159
 * @date 2017年6月19日 上午10:52:52
 */
public class Vo_900002 extends BaseParamVo {
	
	private static final long serialVersionUID = 1858727657524583921L;
	
	@NotEmpty(message = "门店Code不能为空")
	private String orgCode;
	
	private int pageNum=1; //当前页数
	
	private int pageSize=20; //每页显示行数
	
	public String getOrgCode() {
		return orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
