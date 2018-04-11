package com.zdmoney.credit.framework.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

/**
 * 功能号100001 Vo对象
 * 
 * @author Ivan
 *
 */
public class Vo_100002 extends BaseParamVo {
	@NotEmpty(message = "备注信息不允许为空100002")
	private String memo;
}
