package com.zdmoney.credit.common.constant.system;

public enum SequencesEnum {

	ACCESS_LOG("ACCESS_LOG", "SEQ_ACCESS_LOG");

	/** 表名 **/
	private String tableName;
	/** 序列名 **/
	private String sequencesName;

	SequencesEnum(String tableName, String sequencesName) {
		this.tableName = tableName;
		this.sequencesName = sequencesName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSequencesName() {
		return sequencesName;
	}

	public void setSequencesName(String sequencesName) {
		this.sequencesName = sequencesName;
	}

}
