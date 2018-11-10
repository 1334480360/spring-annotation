package com.test.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 *
 *
 * @author xuan
 * @date 2018/11/3
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBeanFactoryPostProcessor...postProcessBeanFactory...");

		int count = beanFactory.getBeanDefinitionCount();
		String[] definitionNames = beanFactory.getBeanDefinitionNames();

		System.out.println("当前beanFactory中有" + count + "个bean，分别是" + Arrays.toString(definitionNames));
	}

}
