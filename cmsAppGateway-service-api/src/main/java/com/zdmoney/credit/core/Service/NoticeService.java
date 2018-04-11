package com.zdmoney.credit.core.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.api.framework.service.BusinessService;
import com.zdmoney.credit.callinter.HttpUrlConnection;
import com.zdmoney.credit.common.annotate.FuncIdAnnotate;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.BeanUtils;
import com.zdmoney.credit.common.vo.FuncResult;
import com.zdmoney.credit.config.CoreProperties;
import com.zdmoney.credit.framework.vo.Vo_400005;

/**
 * 公告信息
 * 
 * @author Ivan
 *
 */
@Service
public class NoticeService extends BusinessService {

	protected static Log logger = LogFactory.getLog(NoticeService.class);

	@Autowired
	CoreProperties coreProperties;

	@Autowired
	HttpUrlConnection httpUrlConnection;

	/**
	 * 公告查询接口
	 * 
	 * @param vo_400005
	 * @return
	 */
	@FuncIdAnnotate(value = "400005", desc = "公告查询", voCls = Vo_400005.class)
	public FuncResult searchNotice(Vo_400005 vo_400005) {
		/** 调用接口返回数据 **/
		String result = null;
		JSONObject param = new JSONObject(BeanUtils.toMap(vo_400005));
		/** 查询调用核心登录校验接口url **/
		String requestUrl = coreProperties.getServiceUrl() + "/app/notice/searchNotice";
		result = httpUrlConnection.postForEntity(requestUrl, param, String.class);
		JSONObject obj = JSONObject.parseObject(result);
		String resCode = "";
		FuncResult funcResult = null;
		if (obj.containsKey("resCode")) {
			resCode = obj.getString("resCode");
			if ("000000".equals(resCode)) {
				JSONObject attachment = obj.getJSONObject("attachment");
				if (attachment == null) {
					throw new PlatformException(ResponseEnum.FULL_MSG, "缺少attachment数据项");
				}
				JSONObject json = new JSONObject();
				/** 总条数 **/
				json.put("total", attachment.get("totalCount"));
				/** 每页显示条数 **/
				json.put("max", attachment.get("rows"));
				/** 显示指定页数 **/
				json.put("offset", attachment.get("pgNumber"));
				/** 公告结果集 **/
				json.put("noticeList", attachment.getJSONArray("resultList"));
				funcResult = FuncResult.success("正常", json);
			} else {
				funcResult = FuncResult.fail(obj.getString("resMsg"));
			}
		} else {
			funcResult = FuncResult.fail("三方返回缺少【resCode】Key");
		}
		return funcResult;
	}
}
