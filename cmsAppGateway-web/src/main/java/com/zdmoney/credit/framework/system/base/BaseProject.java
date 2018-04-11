package com.zdmoney.credit.framework.system.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zdmoney.credit.common.constant.system.LogLevel;
import com.zdmoney.credit.common.constant.system.ProjectEnum;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.SpringContextUtil;
import com.zdmoney.credit.common.util.Strings;
import com.zdmoney.credit.framework.RequestContext;
import com.zdmoney.credit.framework.vo.request.HeaderVo;
import com.zdmoney.credit.framework.vo.request.RequestVo;
import com.zdmoney.credit.system.service.pub.ISysParamDefineService;

/**
 * 项目基类 （网关调用方项目）
 * 
 * @author Ivan
 *
 */
public abstract class BaseProject {

	@Autowired
	ISysParamDefineService sysParamDefineServiceImpl;

	protected static Log logger = LogFactory.getLog(BaseProject.class);

	/** 获取项目实例 **/
	public static BaseProject build(ProjectEnum s) {
		String productNo = Strings.parseString(s.getProjectNo());
		return (BaseProject) SpringContextUtil.getBean(productNo);
	}

	/**
	 * 获取项目枚举
	 * 
	 * @return
	 */
	public abstract ProjectEnum getProjectEnum();

	/**
	 * 获取设备名称
	 * 
	 * @return
	 */
	public String getDeviceType() {
		RequestVo requestVo = RequestContext.getPreAdviceWrap().getRequestVo();
		HeaderVo headerVo = requestVo.getHeaderVo();
		String userAgent = headerVo.getUserAgent();
		/** 终端设备 **/
		String deviceType = "";
		if (userAgent.toUpperCase().indexOf("IOS") >= 0) {
			deviceType = "ios";
		} else if (userAgent.toUpperCase().indexOf("ANDROID") >= 0) {
			deviceType = "android";
		} else if (userAgent.toUpperCase().indexOf("WAP") >= 0) {
			deviceType = "wap";
			throw new PlatformException(ResponseEnum.APP_VERSION_FAIL, "wap暂不，请检查userAgent参数！")
					.applyLogLevel(LogLevel.WARN);
		} else {
			throw new PlatformException(ResponseEnum.APP_VERSION_FAIL, "未知手机平台，请检查userAgent参数！")
					.applyLogLevel(LogLevel.WARN);
		}
		return deviceType;
	}

	/**
	 * 获取App服务端 版本号信息
	 * 
	 * @return 版本号
	 */
	public String getServiceAppVersion() {
		/** 获取项目 **/
		ProjectEnum projectEnum = getProjectEnum();
		/** 版本号Key的前缀 **/
		String versionPrefix = projectEnum.name() + ".";

		RequestVo requestVo = RequestContext.getPreAdviceWrap().getRequestVo();
		HeaderVo headerVo = requestVo.getHeaderVo();
		String version = headerVo.getVersion();

		/** 获取设备类型 **/
		String deviceType = getDeviceType();
		logger.info(projectEnum.getProjectName() + "服务端苹果版本号："
				+ sysParamDefineServiceImpl.getSysParamValueCache("appVersion", versionPrefix + "ios"));
		logger.info(projectEnum.getProjectName() + "服务端安卓版本号："
				+ sysParamDefineServiceImpl.getSysParamValueCache("appVersion", versionPrefix + "android"));
		logger.info(projectEnum.getProjectName() + "端平台：" + deviceType);
		logger.info(projectEnum.getProjectName() + "端版本号：" + version);
		String serviceAppVersion = sysParamDefineServiceImpl.getSysParamValueCache("appVersion", versionPrefix
				+ deviceType);
		return serviceAppVersion;
	}
}
