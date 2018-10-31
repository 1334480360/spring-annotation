package com.test.bean;

import org.springframework.stereotype.Component;

/**
 * @title：通过@Bean指定init和destroy方法
 * @author：xuan
 * @date：2018/10/31
 */
@Component
public class Car extends BaseEntity {
	public Car() {
		System.out.println("car constructor");
	}

	public void init() {
		System.out.println("car....init....");
	}

	/**
	 * @title：方法在拦截器被垃圾回收之前调用，用来回收init方法初始化的资源。
	 * @author：xuan
	 * @date：2018/10/31
	 */
	public void destroy() {
		System.out.println("car...destroy");
	}
}
