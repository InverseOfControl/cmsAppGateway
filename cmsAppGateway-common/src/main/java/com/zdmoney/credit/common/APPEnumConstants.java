package com.zdmoney.credit.common;

public class APPEnumConstants {
	
	/**
	 * APP网关系统编号
	 */
	public static final String GATEWAY_SYSCODE = "cmsAppGateway";
	public static final String JOB_BS = "标识";
	public static final String JOB_JJ = "拒绝";
	public static final String JOB_QX = "取消";
	public static final String GYHBZXTZ= "该用户不在系统中";
	/***
	 * 
	 * <pre>
	 * app网关申请件状态
	 * </pre>
	 *
	 * @author HQ-AT6
	 * @version $Id: EnumConstants.java, v 0.1 2014年8月26日 上午10:51:46 HQ-AT6 Exp $
	 */
	public enum appStatus {
		NEW_SQRD(0), 
		
		COMMIT(1);
		private Integer value;

		appStatus(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}
	/***
	 * 
	 * <pre>
	 * 标黄标识 Y,N
	 * </pre>
	 *
	 * @author HQ-AT6
	 * @version $Id: EnumConstants.java, v 0.1 2014年8月26日 上午10:51:46 HQ-AT6 Exp $
	 */
	public enum identificValue {
		YES("Y"), 
		
		NO("N");
		private String value;

		identificValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
