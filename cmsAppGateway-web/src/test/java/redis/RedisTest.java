package redis;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdmoney.credit.common.validator.ValidatorUtil;
import com.zdmoney.credit.framework.redis.LoginRedisClientUtil;
import com.zdmoney.credit.framework.vo.common.RedisUserInfoVo;
import com.zdmoney.credit.framework.vo.request.RequestVo;

@RunWith(SpringJUnit4ClassRunner.class)
// 使用junit4进行测试
@ContextConfiguration({ "/spring/*.xml" })
// 加载配置文件
public class RedisTest {

	@Autowired
	LoginRedisClientUtil loginRedisClientUtil;

	// @Test
	public void testValid() {
		RequestVo requestVo = new RequestVo();
		requestVo.setProjectNo("1");
		requestVo.setDeviceId("2");
		try {
			ValidatorUtil.valid(requestVo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Test
	public void del() {
		loginRedisClientUtil.delByPattern("Lc_LD2016@*");
	}

	public static void main(String[] args) {
		Random random = new Random();
		for (int i = 0; i < 10000000; i++) {
			String num = (random.nextInt(9999 - 1000 + 1) + 1000) + "";
			if (num.length() == 4) {
				System.out.println(num);
			}
		}
	}

	// @Test
	public void writeVo() {
		RedisUserInfoVo user = new RedisUserInfoVo();
		user.setDeviceId("xxx");
		user.setUserCode("0022232");
		user.setLoginTime(new Date());
		user.setToken("xxx-xxx-xxx-xxx");
		long s = System.currentTimeMillis();

		loginRedisClientUtil.setBaseRedisVo("aaaaaaaaa", 30, user);
		RedisUserInfoVo user1 = (RedisUserInfoVo) loginRedisClientUtil.getBaseRedisVo("aaaaaaaaa",
				RedisUserInfoVo.class);
		System.out.println((System.currentTimeMillis() - s) + "!" + user1.getDeviceId());

		// long s1 = System.currentTimeMillis();
		// redisClientUtil.setValue("raaaaaaaaa", 30, JSON.toJSONString(user));
		// RedisUserInfoVo user2 =
		// JSON.parseObject(redisClientUtil.getValue("raaaaaaaaa"),
		// RedisUserInfoVo.class);
		// System.out.println((System.currentTimeMillis() - s1) + "!" +
		// user2.getDeviceId());
	}

}
