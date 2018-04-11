package com.zdmoney.credit.framework.system;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.zdmoney.credit.common.constant.system.ProjectEnum;
import com.zdmoney.credit.framework.system.base.BaseProject;

/**
 * 征信查询App 项目
 * 
 * @author Ivan
 *
 */
@Component("Lc_WS2015")
public class WS2015Project extends BaseProject {

	private static final ProjectEnum projectEnum = ProjectEnum.Lc_WS2015;

	protected static Log logger = LogFactory.getLog(WS2015Project.class);

	@Override
	public ProjectEnum getProjectEnum() {
		return projectEnum;
	}

}
