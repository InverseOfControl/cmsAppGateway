import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdmoney.credit.common.util.MD5Util;
import com.zdmoney.credit.common.util.Strings;
import com.zdmoney.credit.framework.vo.Vo_100001;
import com.zdmoney.credit.framework.vo.common.BaseParamVo;

public class CarTest {
	public static void main(String[] args) {

		
		

		String jsonText = "{\"r\":\"x\",\"memo\":\"xxxxxxxxx\"}";
		BaseParamVo paramVo = (BaseParamVo) JSON.parseObject("{}", Vo_100001.class);
		System.out.println(paramVo);

		long s = System.currentTimeMillis();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		String json = "{\"age\":\"12\",\"name\":\"要要\",\"customerid\":\"rrrr\"}";

		Person person = JSON.parseObject(json, Person.class);
//		person.setName("");

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		for (ConstraintViolation<Person> constraintViolation : constraintViolations) {
			System.out.println(constraintViolation.getMessage());
			System.out.println(constraintViolation.getPropertyPath());
		}
		System.out.println(System.currentTimeMillis() - s);
	}
}
