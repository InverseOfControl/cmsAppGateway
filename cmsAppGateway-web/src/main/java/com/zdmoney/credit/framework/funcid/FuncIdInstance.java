package com.zdmoney.credit.framework.funcid;

import org.springframework.stereotype.Service;

import com.zdmoney.credit.framework.vo.common.BaseParamVo;

/**
 * 业务功能号实例
 * 
 * @author Ivan
 *
 */
@Service
public class FuncIdInstance {

	/** 业务功能号 对象 **/
	private FuncIdMethod funcIdMethod;
	/** 业务功能号参数 **/
	private BaseParamVo baseParamVo;

	public FuncIdMethod getFuncIdMethod() {
		return funcIdMethod;
	}

	public void setFuncIdMethod(FuncIdMethod funcIdMethod) {
		this.funcIdMethod = funcIdMethod;
	}

	public BaseParamVo getBaseParamVo() {
		return baseParamVo;
	}

	public void setBaseParamVo(BaseParamVo baseParamVo) {
		this.baseParamVo = baseParamVo;
	}

}
