package com.zdmoney.credit.framework.funcid;

import com.alibaba.fastjson.JSON;
import com.zdmoney.credit.common.constant.system.LogLevel;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.Strings;
import com.zdmoney.credit.common.validator.ValidatorUtil;
import com.zdmoney.credit.framework.vo.common.BaseParamVo;

/**
 * 功能号工具类
 * 
 * @author Ivan
 *
 */
public class FuncIdUtil {

	/**
	 * 判断功能号是否存在
	 * 
	 * @param funcId
	 *            功能号
	 * @return
	 */
	public static FuncIdMethod checkFuncIdExists(String funcId) {
		FuncIdMethod funcIdMethod = FuncIdManager.getMethod(funcId);
		if (funcIdMethod == null) {
			throw new PlatformException(ResponseEnum.FUNC_ID_NOT_EXISTS, funcId).applyLogLevel(LogLevel.WARN);
		}
		return funcIdMethod;
	}

	/**
	 * 校验功能号参数格式是否异常
	 * 
	 * @param funcId
	 *            功能号
	 * @param businessParams
	 *            功能号业务参数
	 * @return
	 */
	public static BaseParamVo checkFuncIdParams(String funcId, String businessParams) {
		businessParams = Strings.defaultValue(businessParams, "", "{}");
		FuncIdMethod funcIdMethod = checkFuncIdExists(funcId);
		Class<?> voCls = funcIdMethod.getVoCls();
		/** 将JSON字符串 转换成业务Vo **/
		BaseParamVo baseParamVo = (BaseParamVo) JSON.parseObject(businessParams, voCls);
		/** 校验业务Vo数据格式 **/
		ValidatorUtil.valid(baseParamVo);
		return baseParamVo;
	}

}
