package com.test.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @title：判断是否为win
 * @author：xuan
 * @date：2018/10/30
 */
public class WindowsCondition implements Condition {

	/**
	 * @title：条件匹配器
	 * @param context 上下文
	 * @param annotatedTypeMetadata 注释信息
	 * @author：xuan
	 * @date：2018/10/30
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
		//获取环境变量
		Environment environment = context.getEnvironment();

		String property = environment.getProperty("os.name");
		if (property.contains("windows")) {
			return true;
		}

		return false;
	}
}
