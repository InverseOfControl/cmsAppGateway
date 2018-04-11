package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

/**
 * <p>Description:功能号900004 请求VO</p>
 * @uthor YM10159
 * @date 2017年6月19日 上午10:52:52
 */
public class Vo_900004 extends BaseParamVo {
	
	private static final long serialVersionUID = 1858727657524583921L;
	
	@NotEmpty(message="申请产品不能为空")
	private String productCode;
	
	@NotEmpty(message="申请期限不能为空")
	private String applyTerm;
	
	@NotEmpty(message="申请额度不能为空")
	private String applyLmt;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getApplyTerm() {
		return applyTerm;
	}

	public void setApplyTerm(String applyTerm) {
		this.applyTerm = applyTerm;
	}

	public String getApplyLmt() {
		return applyLmt;
	}

	public void setApplyLmt(String applyLmt) {
		this.applyLmt = applyLmt;
	}
}
