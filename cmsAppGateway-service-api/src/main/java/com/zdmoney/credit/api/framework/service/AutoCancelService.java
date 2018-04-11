package com.zdmoney.credit.api.framework.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.zdmoney.credit.applyinfo.domain.TmApplyMainInfo;
import com.zdmoney.credit.applyinfo.service.pub.ITmApplyMainInfoService;
import com.zdmoney.credit.framework.vo.Vo_700005;

@Service
public class AutoCancelService extends BusinessService{

	protected static Log logger = LogFactory.getLog(AutoCancelService.class);

	@Autowired
	private ITmApplyMainInfoService applyMainInfoService;

	@Autowired
	private PreAPPService preAPPService;

	public void dispatch() {
		logger.info("APP自动超时取消定时任务开始");
		List<TmApplyMainInfo> tamiList = applyMainInfoService.getTimeoutApp();

		Vo_700005 vo_700005 = new Vo_700005();
		if(CollectionUtils.isNotEmpty(tamiList)){
			for (TmApplyMainInfo tmApplyMainInfo : tamiList) {
				String appNo = tmApplyMainInfo.getAppNo();
				logger.info("APP自动超时取消定时任务:appNo="+appNo);
				vo_700005.setAppNo(appNo);
				vo_700005.setRefuseReason("CA00037");
				try {
					preAPPService.cancelTempApplyInputInfo(vo_700005);
				} catch (Exception e) {
					logger.error("APP自动超时取消定时任务异常",e);
				}
			}
		}else{
			logger.info("APP自动超时取消定时任务：没有超时的案件！");
		}
		logger.info("APP自动超时取消定时任务结束");
	}
}
