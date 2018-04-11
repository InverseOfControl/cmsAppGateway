package com.zdmoney.credit.framework.domain;

import java.util.List;

public class ExpandList extends BaseDomain{

	private String sectionLabel;
	private List<FieldList> fieldList;
	
	public String getSectionLabel() {
		return sectionLabel;
	}
	public void setSectionLabel(String sectionLabel) {
		this.sectionLabel = sectionLabel;
	}
	public List<FieldList> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<FieldList> fieldList) {
		this.fieldList = fieldList;
	}
	
	
}
