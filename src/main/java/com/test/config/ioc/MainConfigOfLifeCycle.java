package com.test.config.ioc;

import com.test.bean.ioc.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.test.bean")
@Configuration
public class MainConfigOfLifeCycle {

	@Bean(initMethod = "init", destroyMethod = "destroy")
	public Car car() {
		return new Car();
	}

}
