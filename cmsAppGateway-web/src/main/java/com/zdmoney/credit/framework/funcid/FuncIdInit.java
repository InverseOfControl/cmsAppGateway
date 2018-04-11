package com.zdmoney.credit.framework.funcid;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import com.zdmoney.credit.api.framework.service.BusinessService;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;

/**
 * 业务功能号 系统级初始化
 * 
 * @author Ivan
 *
 */
@Service
public class FuncIdInit implements BeanPostProcessor {

	protected static Log logger = LogFactory.getLog(FuncIdInit.class);

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof BusinessService) {
			Class<?> cls = bean.getClass();
			Method[] methods = cls.getMethods();
			for (Method method : methods) {
				FuncIdAnnotate annotate = method.getAnnotation(FuncIdAnnotate.class);
				if (annotate != null) {
					/** 业务功能号 **/
					String funcId = annotate.value();
					FuncIdMethod funcIdMethod = new FuncIdMethod();
					funcIdMethod.setFuncId(funcId);
					funcIdMethod.setBusinessMethod(method);
					funcIdMethod.setBusinessService((BusinessService) bean);
					funcIdMethod.setVoCls(annotate.voCls());
					funcIdMethod.setFuncIdAnnotate(annotate);
					FuncIdManager.putFuncId(funcId, funcIdMethod);
				}
			}
		}
		return bean;
	}
}
