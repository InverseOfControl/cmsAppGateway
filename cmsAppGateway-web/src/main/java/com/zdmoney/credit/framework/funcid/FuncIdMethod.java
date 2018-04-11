package com.zdmoney.credit.framework.funcid;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zdmoney.credit.api.framework.service.BusinessService;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;

/**
 * 业务功能号 方法
 * 
 * @author Ivan
 *
 */
public class FuncIdMethod {

	protected static Log logger = LogFactory.getLog(FuncIdMethod.class);

	/**
	 * 功能号
	 */
	private String funcId;
	/**
	 * 业务Service 实例
	 */
	private BusinessService businessService;
	/**
	 * 业务Service 方法
	 */
	private Method businessMethod;

	private Class<?> voCls;

	private FuncIdAnnotate funcIdAnnotate;

	/**
	 * 调用功能号 对应的方法
	 * 
	 * @param args
	 *            业务参数
	 * @return
	 */
	public Object runMethod(Object... args) {
		try {
			return businessMethod.invoke(businessService, args);
		} catch (IllegalAccessException ex) {
			logger.error(ex, ex);
			throw new PlatformException(ResponseEnum.FUNC_ID_CALL_ERROR, "IllegalAccessException");
		} catch (IllegalArgumentException ex) {
			logger.error(ex, ex);
			throw new PlatformException(ResponseEnum.FUNC_ID_CALL_ERROR, "IllegalArgumentException");
		} catch (InvocationTargetException ex) {
			Throwable throwable = ex.getTargetException();
			if (throwable instanceof PlatformException) {
				PlatformException p = ((PlatformException) throwable);
				p.printStackTraceExt(logger);
				throw p;
			} else {
				logger.error(ex, ex);
				throw new PlatformException(ResponseEnum.FUNC_ID_CALL_ERROR, this.funcId);
			}
		}

	}

	public BusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(BusinessService businessService) {
		this.businessService = businessService;
	}

	public Method getBusinessMethod() {
		return businessMethod;
	}

	public void setBusinessMethod(Method businessMethod) {
		this.businessMethod = businessMethod;
	}

	public Class<?> getVoCls() {
		return voCls;
	}

	public void setVoCls(Class<?> voCls) {
		this.voCls = voCls;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public FuncIdAnnotate getFuncIdAnnotate() {
		return funcIdAnnotate;
	}

	public void setFuncIdAnnotate(FuncIdAnnotate funcIdAnnotate) {
		this.funcIdAnnotate = funcIdAnnotate;
	}

}
