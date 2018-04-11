package com.zdmoney.credit.framework;

import com.zdmoney.credit.framework.wrap.PreAdviceWrap;

/**
 * 请求信息上下文 包含 头部信息和请求参数信息
 * 
 * @author Ivan
 *
 */
public class RequestContext {

	private static final ThreadLocal<PreAdviceWrap> T_PREADVICEWRAP = new ThreadLocal<PreAdviceWrap>();

	public static PreAdviceWrap getPreAdviceWrap() {
		PreAdviceWrap preAdviceWrap = T_PREADVICEWRAP.get();
		if (preAdviceWrap == null) {
			preAdviceWrap = new PreAdviceWrap();
			T_PREADVICEWRAP.set(preAdviceWrap);
		}
		return preAdviceWrap;
	}

	public static void clear() {
		T_PREADVICEWRAP.remove();
	}

}
