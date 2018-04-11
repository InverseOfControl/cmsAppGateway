package com.zdmoney.credit.framework.system;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.zdmoney.credit.common.constant.system.ProjectEnum;
import com.zdmoney.credit.framework.system.base.BaseProject;

/**
 * 录单App 项目
 * 
 * @author Ivan
 *
 */
@Component("Lc_LD2017")
public class LD2016Project extends BaseProject {

	private static final ProjectEnum projectEnum = ProjectEnum.Lc_LD2017;

	protected static Log logger = LogFactory.getLog(LD2016Project.class);

	@Override
	public ProjectEnum getProjectEnum() {
		return projectEnum;
	}

}
