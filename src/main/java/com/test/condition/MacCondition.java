package com.test.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @title：判断是否为mac os
 * @author：xuan
 * @date：2018/10/30
 */
public class MacCondition implements Condition {

	/**
	 * @title：条件匹配器
	 * @param context 上下文
	 * @param annotatedTypeMetadata 注释信息
	 * @author：xuan
	 * @date：2018/10/30
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
		//获取ioc使用的beanFactory
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		//获取类加载器
		ClassLoader classLoader = context.getClassLoader();
		//获取环境变量
		Environment environment = context.getEnvironment();
		//获取bean定义的注册类
		BeanDefinitionRegistry registry = context.getRegistry();

		//可以判断容器中的bean注册情况，也可以给容器中注册bean
//		boolean flag = registry.containsBeanDefinition("person");

		String property = environment.getProperty("os.name");
		if (property.contains("Mac")) {
			return true;
		}

		return false;
	}
}
