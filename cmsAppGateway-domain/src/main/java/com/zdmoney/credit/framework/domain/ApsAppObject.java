package com.zdmoney.credit.framework.domain;

import java.util.List;

public class ApsAppObject extends BaseDomain{

	private String sectionLabel;
	private String typeName;
	private List<FieldList> fieldList;
	private List<ExpandList> expandLists;
	
	public String getSectionLabel() {
		return sectionLabel;
	}
	public void setSectionLabel(String sectionLabel) {
		this.sectionLabel = sectionLabel;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<FieldList> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<FieldList> fieldList) {
		this.fieldList = fieldList;
	}
	public List<ExpandList> getExpandLists() {
		return expandLists;
	}
	public void setExpandLists(List<ExpandList> expandLists) {
		this.expandLists = expandLists;
	}
	
}
