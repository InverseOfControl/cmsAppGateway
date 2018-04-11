package com.zdmoney.credit.common.constant.system;

import com.zdmoney.credit.common.util.Strings;

/***
 * 网关接入 项目的枚举
 * 
 * @author Ivan
 *
 */
public enum ProjectEnum {
	Lc_WS2015("Lc_WS2015", "征信查询App"), Lc_LD2017("Lc_LD2017", "录单App");
	/** 项目编号 **/
	private String projectNo;
	/** 项目名 **/
	private String projectName;

	public static ProjectEnum get(String projectNo) {
		if (Strings.isEmpty(projectNo)) {
			return null;
		}
		for (ProjectEnum projectEnum : ProjectEnum.values()) {
			if (projectEnum.name().equals(projectNo)) {
				return projectEnum;
			}
		}
		return null;
	}

	ProjectEnum(String projectNo, String projectName) {
		this.projectNo = projectNo;
		this.projectName = projectName;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
