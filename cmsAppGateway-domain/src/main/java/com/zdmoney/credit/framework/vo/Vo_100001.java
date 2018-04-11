package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

/**
 * 功能号100001 Vo对象
 * 
 * @author Ivan
 *
 */
public class Vo_100001 extends BaseParamVo {
	@NotEmpty(message = "客户编号不允许为空")
	private String customerId;

	@Length(max = 10, message = "备注不能超过10个字")
	private String memo;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
