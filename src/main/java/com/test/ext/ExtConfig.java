package com.test.ext;

import com.test.bean.ioc.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.test.ext")
public class ExtConfig {

	@Bean
	public Car car() {
		return new Car();
	}

}
