package com.test.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @title：通过@Bean实现InitializingBean,DisposableBean指定afterPropertiesSet和destroy方法
 * @author：xuan
 * @date：2018/10/31
 */
@Component
public class Cat implements InitializingBean, DisposableBean {
	public Cat() {
		System.out.println("cat constructor...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("cat destroy...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("cat afterPropertiesSet...");
	}
}
