package com.zdmoney.credit.common.json;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.common.constant.system.LogLevel;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.Dates;
import com.zdmoney.credit.common.util.Strings;

/**
 * JSON格式工具包
 * 
 * @author user
 *
 */
public class JSONUtil {

	/**
	 * 校验JSON格式
	 * 
	 * @param jsonText
	 *            如果空值 则返回{}内容
	 * @return
	 */
	public static JSONObject checkJSONObject(String jsonText) {
		JSONObject jsonObj = null;
		if (Strings.isEmpty(jsonText)) {
			jsonObj = new JSONObject();
		} else {
			try {
				jsonObj = JSON.parseObject(jsonText);
			} catch (Exception ex) {
				/** json格式异常 **/
				throw new PlatformException(ResponseEnum.VALIDATE_JSON_FORMAT_ERROR, "").applyLogLevel(LogLevel.WARN);
			}
		}
		return jsonObj;
	}
	/**
	 * 将JSON对象转换成 URL参数拼接(a=a&b=b)
	 * 
	 * @param jsonObj
	 * @return
	 */
	public static String toUrlParam(JSONObject jsonObj) {
		List<String> params = new ArrayList<String>();

		Iterator iter = jsonObj.keySet().iterator();
		while (iter.hasNext()) {
			String key = Strings.parseString(iter.next());
			Object value = jsonObj.get(key);
			if (value instanceof Date) {
				/** 将日期格式转换成 yyyy-MM-dd **/
				value = Dates.getDateTime((Date) value, Dates.DEFAULT_DATE_FORMAT);
			} else {
				try {
					value = URLEncoder.encode(Strings.parseString(value),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			params.add(key + "=" + value);
		}
		return StringUtils.join(params.toArray(), "&");
	}
}
