package com.zdmoney.credit.framework.funcid;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务功能号 管理 (保存系统所有功能号)
 * 
 * @author Ivan
 *
 */
public class FuncIdManager {

	static Map<String, FuncIdMethod> functionCodeMap = new HashMap<String, FuncIdMethod>();

	/**
	 * 添加业务功能号
	 * 
	 * @param funcId
	 *            业务功能号
	 * @param funcIdMethod
	 *            业务功能号方法
	 */
	public static void putFuncId(String funcId, FuncIdMethod funcIdMethod) {
		functionCodeMap.put(funcId, funcIdMethod);
	}

	/**
	 * 获取功能号对应的方法
	 * 
	 * @param funcId
	 *            功能号
	 * @return
	 */
	public static FuncIdMethod getMethod(String funcId) {
		if (functionCodeMap.containsKey(funcId)) {
			return functionCodeMap.get(funcId);
		} else {
			return null;
		}
	}

}
