package com.zdmoney.credit.framework.redis;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zdmoney.credit.common.constant.system.LogLevel;
import com.zdmoney.credit.common.exception.PlatformException;
import com.zdmoney.credit.common.exception.ResponseEnum;
import com.zdmoney.credit.common.util.Strings;
import com.zdmoney.credit.framework.vo.common.RedisUserInfoVo;
import com.zdmoney.credit.framework.vo.request.HeaderVo;
import com.zdmoney.credit.framework.vo.request.RequestVo;

/**
 * 保存相关登陆的数据
 * 
 * @author Ivan
 *
 */
@Component
public class LoginRedisClientUtil extends RedisClientUtil {

	protected static Log logger = LogFactory.getLog(LoginRedisClientUtil.class);

	private static final String PRE_FLAG = "@";

	@Value("${redis.loginUserInfo.dbIndex}")
	private int dbIndex;

	@Value("${redis.loginUserInfo.timeOut}")
	private int sessionTimeOut;

	@Override
	public int getDbIndex() {
		return dbIndex;
	}

	/**
	 * 保存用户登陆信息（载体：Redis）
	 * 
	 * @param token
	 *            App端会话Token
	 * @param redisUserInfoVo
	 */
	public void saveLoginUserInfo(RequestVo requestVo, RedisUserInfoVo redisUserInfoVo) {
		/** 项目编号 **/
		String projectNo = requestVo.getProjectNo();
		/** 工号 **/
		String userCode = redisUserInfoVo.getUserCode();
		/** 会话Token **/
		String token = redisUserInfoVo.getToken();
		/** 保存设备ID **/
		String deviceId = requestVo.getDeviceId();
		redisUserInfoVo.setDeviceId(deviceId);
		redisUserInfoVo.setLoginTime(new Date());

		/** 清空历史Key 格式：项目编号@设备号@* **/
		String key = projectNo + PRE_FLAG + deviceId + PRE_FLAG;
		delByPattern(key + "*");

		/** 保存Token与工号对应关系 **/
		setValue(key + token, sessionTimeOut, userCode);
		/** 保存工号与Vo对应关系 **/
		String voKey = projectNo + PRE_FLAG + userCode;
		setBaseRedisVo(voKey, sessionTimeOut, redisUserInfoVo);
	}

	/**
	 * 校验Token是否有效
	 */
	public void checkTokenInfo(RequestVo requestVo, HeaderVo headerVo) {
		String sessionToken = headerVo.getSessionToken();
		/** 项目编号 **/
		String projectNo = requestVo.getProjectNo();
		String deviceId = requestVo.getDeviceId();

		/** 获取Token对应的业务员工的工号（读取Redis数据） **/
		String key = projectNo + PRE_FLAG + deviceId + PRE_FLAG + sessionToken;
		String userCode = super.getValue(key);
		if (Strings.isEmpty(userCode)) {
			throw new PlatformException(ResponseEnum.SESSION_EXPIRE, "").applyLogLevel(LogLevel.WARN);
		}
		/** 重置过期时间 **/
		super.expire(key, sessionTimeOut);
		/** 跟据工号获取Vo对象（读取Redis数据） **/
		String voKey = projectNo + PRE_FLAG + userCode;
		RedisUserInfoVo redisUserInfoVo = (RedisUserInfoVo) super.getBaseRedisVo(voKey, RedisUserInfoVo.class);
		if (redisUserInfoVo == null) {
			throw new PlatformException(ResponseEnum.SESSION_EXPIRE, "").applyLogLevel(LogLevel.WARN);
		}
		/** 重置过期时间 **/
		super.expire(voKey, sessionTimeOut);
		/** 判断是否换过终端登陆 **/
		String redisDeviceId = redisUserInfoVo.getDeviceId();
		if (redisDeviceId.equals(requestVo.getDeviceId())) {

		} else {
			/** 强制下线 **/
			del(key);
			throw new PlatformException(ResponseEnum.SESSION_KILL, "").applyLogLevel(LogLevel.WARN);
		}
	}

	@Override
	public int getTimeOut() {
		return sessionTimeOut;
	}

}
