package com.zdmoney.credit.framework.domain;

public class ListData extends BaseDomain{
	
	private String name;
	private String title;
	private String isDefault;
	
	public ListData(String name, String title, String isDefault) {
		super();
		this.name = name;
		this.title = title;
		this.isDefault = isDefault;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

}
