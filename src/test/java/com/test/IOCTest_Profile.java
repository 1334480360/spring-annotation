package com.test;

import com.test.config.MainConfigOfProfile;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.util.Map;

public class IOCTest_Profile {
	private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);

	@Test
	public void test01() {
		//1、创建一个无参applicationContext
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		//2、设置需要激活的环境
		applicationContext.getEnvironment().setActiveProfiles("test");
		//3、注册主配置类
		applicationContext.register(MainConfigOfProfile.class);
		//4、启动刷新容器
		applicationContext.refresh();

		Map<String, DataSource> beansOfType = applicationContext.getBeansOfType(DataSource.class);
		for (Map.Entry<String, DataSource> entry : beansOfType.entrySet()) {
			System.out.println("key:" + entry.getKey() + ", value:" + entry.getValue());
		}
	}


	private void printBeans(ApplicationContext applicationContext) {
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

}
