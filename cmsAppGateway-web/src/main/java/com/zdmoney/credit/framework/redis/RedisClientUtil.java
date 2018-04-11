package com.zdmoney.credit.framework.redis;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.alibaba.fastjson.JSON;
import com.zdmoney.credit.framework.vo.common.BaseRedisVo;

/**
 * Redis客户端 工具
 * 
 * @author Ivan
 *
 */
public abstract class RedisClientUtil {

	protected static Log logger = LogFactory.getLog(RedisClientUtil.class);

	/**
	 * 获取数据库索引
	 * 
	 * @return
	 */
	public abstract int getDbIndex();

	/**
	 * 获取超时时间
	 * 
	 * @return
	 */
	public abstract int getTimeOut();

	@Autowired
	RedisTemplate redisTemplate;

	public RedisSerializer<String> getStringSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/**
	 * 保存字符串Key Value数据
	 * 
	 * @param key
	 * @param seconds
	 *            过期时间（秒）
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void setValue(final String key, final long seconds, final String value) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(getDbIndex());
				RedisSerializer<String> serializer = getStringSerializer();
				byte[] redisKey = serializer.serialize(key);
				byte[] redisValue = serializer.serialize(value);
				connection.setEx(redisKey, seconds, redisValue);
				return true;
			}
		});
	}

	/**
	 * 获取key对应的Value
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getValue(final String key) {
		return (String) redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(getDbIndex());
				RedisSerializer<String> serializer = getStringSerializer();
				byte[] redisValue = connection.get(serializer.serialize(key));
				return serializer.deserialize(redisValue);
			}
		});
	}

	/**
	 * 保存字符串Key Value数据
	 * 
	 * @param key
	 * @param seconds
	 *            过期时间（秒）
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void setBaseRedisVo(final String key, final long seconds, final BaseRedisVo baseRedisVo) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(getDbIndex());
				RedisSerializer<String> serializer = getStringSerializer();
				byte[] redisKey = serializer.serialize(key);
				byte[] redisValue = serializer.serialize(JSON.toJSONString(baseRedisVo));
				connection.setEx(redisKey, seconds, redisValue);
				return true;
			}
		});
	}

	/**
	 * 获取key对应的Value
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BaseRedisVo getBaseRedisVo(final String key, final Class clazz) {
		return (BaseRedisVo) redisTemplate.execute(new RedisCallback<BaseRedisVo>() {
			public BaseRedisVo doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(getDbIndex());
				RedisSerializer<String> serializer = getStringSerializer();
				byte[] redisValue = connection.get(serializer.serialize(key));
				String value = serializer.deserialize(redisValue);
				return (BaseRedisVo) JSON.parseObject(value, clazz);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void expire(final String key, final long seconds) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getStringSerializer();
				connection.select(getDbIndex());
				connection.expire(serializer.serialize(key), seconds);
				return true;
			}
		});
	}

	/**
	 * 批量删除Keys
	 * 
	 * @param preKey
	 *            开头字符串
	 */
	@SuppressWarnings("unchecked")
	public void delByPattern(final String preKey) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getStringSerializer();
				connection.select(getDbIndex());
				Set<byte[]> set = connection.keys(serializer.serialize(preKey));
				Iterator<byte[]> it = set.iterator();
				while (it.hasNext()) {
					connection.del(it.next());
				}
				return true;
			}
		});
	}

	/**
	 * 删除Key
	 * 
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public void del(final String key) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getStringSerializer();
				connection.select(getDbIndex());
				connection.del(serializer.serialize(key));
				return true;
			}
		});
	}

}
