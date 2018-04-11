package com.zdmoney.credit.framework.vo;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

/**
 * 功能号400002 Vo对象
 * 
 * @author Ivan
 *
 */
public class Vo_400005 extends BaseParamVo {
	/** 查询页数 **/
	private int pagerNum = 1;
	/** 每页显示条数 **/
	private int pagerMax = 20;

	public int getPagerNum() {
		return pagerNum;
	}

	public void setPagerNum(int pagerNum) {
		this.pagerNum = pagerNum;
	}

	public int getPagerMax() {
		return pagerMax;
	}

	public void setPagerMax(int pagerMax) {
		this.pagerMax = pagerMax;
	}

}
