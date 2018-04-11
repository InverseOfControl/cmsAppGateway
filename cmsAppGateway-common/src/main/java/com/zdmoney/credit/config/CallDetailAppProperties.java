package com.zdmoney.credit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CallDetailAppProperties {

	
	@Value("${call.unicom_login_url}")
	private String unicomLoginUrl;

	@Value("${call.unicom_check_url}")
	private String unicomCheckUrl;

	@Value("${call.unicom_call_detail_url}")
	private String unicomCallDetailUrl;

	@Value("${call.records_upload_url}")
	private String recordsUploadUrl;

	@Value("${call.mobile_isrequire_smscode_url}")
	private String mobileIsrequireSmscodeUrl;

	@Value("${call.mobile_login_url}")
	private String mobileLoginUrl;

	@Value("${call.mobile_login_cookie_url}")
	private String mobileLoginCookieUrl;

	@Value("${call.mobile_send_code_url}")
	private String mobileSendCodeUrl;

	@Value("${call.mobile_image_code_url}")
	private String mobileImageCodeUrl;

	@Value("${call.mobile_record_code_url}")
	private String mobileRecordCodeUrl;

	@Value("${call.mobile_login_agin_url}")
	private String mobileLoginAginUrl;

	@Value("${call.mobile_call_detail_url}")
	private String mobileCallDetailUrl;
	
	@Value("${call.records_exception_notice}")
	private String recordsExceptionNotice;

	public String getUnicomLoginUrl() {
		return unicomLoginUrl;
	}

	public void setUnicomLoginUrl(String unicomLoginUrl) {
		this.unicomLoginUrl = unicomLoginUrl;
	}

	public String getUnicomCheckUrl() {
		return unicomCheckUrl;
	}

	public void setUnicomCheckUrl(String unicomCheckUrl) {
		this.unicomCheckUrl = unicomCheckUrl;
	}

	public String getUnicomCallDetailUrl() {
		return unicomCallDetailUrl;
	}

	public void setUnicomCallDetailUrl(String unicomCallDetailUrl) {
		this.unicomCallDetailUrl = unicomCallDetailUrl;
	}

	public String getRecordsUploadUrl() {
		return recordsUploadUrl;
	}

	public void setRecordsUploadUrl(String recordsUploadUrl) {
		this.recordsUploadUrl = recordsUploadUrl;
	}

	public String getMobileIsrequireSmscodeUrl() {
		return mobileIsrequireSmscodeUrl;
	}

	public void setMobileIsrequireSmscodeUrl(String mobileIsrequireSmscodeUrl) {
		this.mobileIsrequireSmscodeUrl = mobileIsrequireSmscodeUrl;
	}

	public String getMobileLoginUrl() {
		return mobileLoginUrl;
	}

	public void setMobileLoginUrl(String mobileLoginUrl) {
		this.mobileLoginUrl = mobileLoginUrl;
	}

	public String getMobileLoginCookieUrl() {
		return mobileLoginCookieUrl;
	}

	public void setMobileLoginCookieUrl(String mobileLoginCookieUrl) {
		this.mobileLoginCookieUrl = mobileLoginCookieUrl;
	}

	public String getMobileSendCodeUrl() {
		return mobileSendCodeUrl;
	}

	public void setMobileSendCodeUrl(String mobileSendCodeUrl) {
		this.mobileSendCodeUrl = mobileSendCodeUrl;
	}

	public String getMobileImageCodeUrl() {
		return mobileImageCodeUrl;
	}

	public void setMobileImageCodeUrl(String mobileImageCodeUrl) {
		this.mobileImageCodeUrl = mobileImageCodeUrl;
	}

	public String getMobileRecordCodeUrl() {
		return mobileRecordCodeUrl;
	}

	public void setMobileRecordCodeUrl(String mobileRecordCodeUrl) {
		this.mobileRecordCodeUrl = mobileRecordCodeUrl;
	}

	public String getMobileLoginAginUrl() {
		return mobileLoginAginUrl;
	}

	public void setMobileLoginAginUrl(String mobileLoginAginUrl) {
		this.mobileLoginAginUrl = mobileLoginAginUrl;
	}

	public String getMobileCallDetailUrl() {
		return mobileCallDetailUrl;
	}

	public void setMobileCallDetailUrl(String mobileCallDetailUrl) {
		this.mobileCallDetailUrl = mobileCallDetailUrl;
	}

	public String getRecordsExceptionNotice() {
		return recordsExceptionNotice;
	}

	public void setRecordsExceptionNotice(String recordsExceptionNotice) {
		this.recordsExceptionNotice = recordsExceptionNotice;
	}

}
