package com.test.config;

import com.test.bean.Car;
import com.test.bean.Color;
import com.test.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @title：autowired自动装配
 * spring利用依赖注入（DI）
 * @author：xuan
 * @date：2018/10/31
 */
@Configuration
@ComponentScan(value = {"com.test.controller", "com.test.service", "com.test.dao", "com.test.bean"})
public class MainConfigOfAutowired {

	@Primary
	@Bean("bookDao2")
	public BookDao bookDao() {
		return new BookDao(2);
	}

	@Bean
	public Color color(Car car) {
		return new Color();
	}

}
