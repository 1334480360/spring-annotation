package com.test.aop;

import com.test.config.aop.MainConfigOfAOP;
import com.test.config.ioc.MainConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AOPTest {

	@Test
	public void test01() {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
		MathCalculator calculator = applicationContext.getBean(MathCalculator.class);
		calculator.div(1, 0);
	}


}
