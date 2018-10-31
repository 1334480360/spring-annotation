package com.test;

import com.test.bean.Person;
import com.test.config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @title：测试类
 * @author：xuan
 * @date：2018/10/30
 */
public class MainTest {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
		Person bean = applicationContext.getBean(Person.class);
		System.out.println(bean);

		String[] nameForType = applicationContext.getBeanNamesForType(Person.class);
		for (String name : nameForType) {
			System.out.println(name);
		}
	}

}
