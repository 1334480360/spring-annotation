package com.test;

import com.test.bean.Boss;
import com.test.config.MainConfigOfAutowired;
import com.test.dao.BookDao;
import com.test.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Autowired {
	private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);

	@Test
	public void test01() {
//		BookService bookService = applicationContext.getBean(BookService.class);
//		BookDao bookDao = applicationContext.getBean(BookDao.class);

//		System.out.println(bookDao);
//		bookService.print();
		Boss boss = applicationContext.getBean(Boss.class);
		System.out.println(boss);
	}


	private void printBeans(ApplicationContext applicationContext) {
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

}
