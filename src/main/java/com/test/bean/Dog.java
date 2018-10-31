package com.test.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @title：通过PostConstruct和@PreDestroy注解实现
 * @author：xuan
 * @date：2018/10/31
 */
@Component
public class Dog {

	public Dog() {
		System.out.println("dog constructor...");
	}

	@PostConstruct
	public void init() {
		System.out.println("dog @PostConstruct...");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("dog @PreDestroy...");
	}
}
