package com.test.ioc;

import com.test.bean.ioc.Person;
import com.test.config.ioc.MainConfig;
import com.test.config.ioc.MainConfig2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

public class IOCTest {
	ApplicationContext applicationContext2 = new AnnotationConfigApplicationContext(MainConfig2.class);


	@Test
	public void test01() {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
		this.printBeans(applicationContext);
	}

	private void printBeans(ApplicationContext applicationContext) {
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

	@Test
	public void test02() {
		String[] beanNames = applicationContext2.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}


		Person person1 = (Person) applicationContext2.getBean("person");
		Person person2 = (Person) applicationContext2.getBean("person");
		System.out.println(person1 == person2);
	}

	@Test
	public void test03() {
		//获取环境变量
		Environment environment = applicationContext2.getEnvironment();
		String property = environment.getProperty("os.name");
		System.out.println(property);

		String[] beanNames = applicationContext2.getBeanNamesForType(Person.class);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}

		Map<String, Person> persons = applicationContext2.getBeansOfType(Person.class);
		System.out.println(persons);
	}

	@Test
	public void testImport() {
		this.printBeans(applicationContext2);
//		Blue blue = applicationContext2.getBean(Blue.class);
//		System.out.println(blue);

		//工厂Bean获取的是调用getObject()创建的对象。
		Object colorFactoryBean = applicationContext2.getBean("colorFactoryBean");
		Object colorFactoryBean1 = applicationContext2.getBean("colorFactoryBean");
		Object colorFactoryBean2 = applicationContext2.getBean("&colorFactoryBean");
		System.out.println(colorFactoryBean.getClass());
		System.out.println(colorFactoryBean2.getClass());
		System.out.println(colorFactoryBean == colorFactoryBean1);
	}

}
