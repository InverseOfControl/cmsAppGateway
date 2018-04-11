package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

/**
 * <p>Description:功能号900003 请求VO</p>
 * @uthor YM10159
 * @date 2017年6月19日 上午10:52:52
 */
public class Vo_900003 extends BaseParamVo {
	
	private static final long serialVersionUID = 1858727657524583921L;
	
	@NotEmpty(message = "申请件ID不能为空")
	private String id;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
}
