package com.test.config.ioc;

import com.test.bean.ioc.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @title：@PropertySource加载外部配置文件
 * @author：xuan
 * @date：2018/10/31
 */
@PropertySource(value = {"person.properties"})
@Configuration
public class MainConfigOfPropertyValues {

	@Bean
	public Person person() {
		return new Person();
	}

}
