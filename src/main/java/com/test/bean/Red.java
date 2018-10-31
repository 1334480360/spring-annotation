package com.test.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @title：调用Spirng底层组件
 * @author：xuan
 * @date：2018/10/31
 */
@Component
public class Red extends BaseEntity implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("传入的ioc：" + applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("当前bean的名字：" + name);
	}

	/**
	 * @title：String值解析器
	 */
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		String s = resolver.resolveStringValue("你好${os.name}");
		System.out.println("解析的字符串：" + s);
	}
}
