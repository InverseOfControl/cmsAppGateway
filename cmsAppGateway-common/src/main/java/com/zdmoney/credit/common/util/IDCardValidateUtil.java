package com.zdmoney.credit.common.util;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class IDCardValidateUtil {
	public static final int IDENTITYCODE_OLD = 15; // 老身份证15位
	public static final int IDENTITYCODE_NEW = 18; // 新身份证18位
	
	//判断身份证号是否正确
	public static boolean isIdentityCode(String code) {

		if (StringUtils.isEmpty(code)) {
			return false;
		}

		code = code.trim();

		if ((code.length() != IDENTITYCODE_OLD)
				&& (code.length() != IDENTITYCODE_NEW)) {
			return false;
		}

		// 身份证号码必须为数字(18位的新身份证最后一位可以是x)
		Pattern pt = Pattern.compile("\\d{17}([\\dxX]{1})?");
		Matcher mt = pt.matcher(code);
		if (!mt.find()) {
			return false;
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		simpleDateFormat.setLenient(false);

		// 最后一位校验码验证
		if (code.length() == IDENTITYCODE_NEW) {
			String lastNum = getVerify(code.substring(0, IDENTITYCODE_NEW - 1));
			// check last digit
			if (!("" + code.charAt(IDENTITYCODE_NEW - 1)).toUpperCase().equals(
					lastNum)) {
				return false;
			}
		}

		return true;
	}

	protected static String getVerify(String eightcardid) {
		int remaining = 0;
		int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
		int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
		int[] ai = new int[18];
		String returnStr = null;
		try {
			if (eightcardid.length() == 18) {
				eightcardid = eightcardid.substring(0, 17);
			}
			if (eightcardid.length() == 17) {
				int sum = 0;
				String k = null;
				for (int i = 0; i < 17; i++) {
					k = eightcardid.substring(i, i + 1);
					ai[i] = Integer.parseInt(k);
					k = null;
				}
				for (int i = 0; i < 17; i++) {
					sum = sum + wi[i] * ai[i];
				}
				remaining = sum % 11;
			}
			returnStr = remaining == 2 ? "X" : String.valueOf(vi[remaining]);
		} catch (Exception ex) {
			return null;
		} finally {
			wi = null;
			vi = null;
			ai = null;
		}
		return returnStr;
	}
	
	public static String getGenderByIdNo(String idNo){
		String sex = idNo.substring(16, 17);
		if(Integer.parseInt(sex)%2==0){
			return "女";
		}else{
			return "男";
		}
	}
}
