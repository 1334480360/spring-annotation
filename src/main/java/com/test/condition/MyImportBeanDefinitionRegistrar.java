package com.test.condition;

import com.test.bean.ioc.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @title：自定义注册bean
 * @author：xuan
 * @date：2018/10/30
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
	/**
	 * @title：手动注册bean
	 * @param annotationMetadata 当前类的注解信息
	 * @param registry 注册类
	 * @author：xuan
	 * @date：2018/10/30
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
		boolean definition = registry.containsBeanDefinition("com.test.bean.ioc.Red");
		boolean definition2 = registry.containsBeanDefinition("com.test.bean.ioc.Blue");

		if (definition && definition2) {
			RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
			registry.registerBeanDefinition("rainBow", beanDefinition);
		}

	}
}
