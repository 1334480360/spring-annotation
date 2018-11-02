package com.test.config.ioc;

import com.test.bean.ioc.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @title：配置类 == spring配置文件
 * Configuration：告诉spring这是个配置类
 * ComponentScan：包扫瞄，value:指定要扫描的包，excludeFilters指定排除的组件
 * @author：xuan
 * @date：2018/10/30
 */
@Configuration
@ComponentScan(value = "com.test", includeFilters = {
//		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class}),
		@ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
}, useDefaultFilters = false)
public class MainConfig {

	/**
	 * @title：给容器注册一个bean
	 * Bean：id默认是用方法名作为id，默认为单例
	 * @author：xuan
	 * @date：2018/10/30
	 */
	@Bean("person1")
	public Person person() {
		return new Person("Tom", 18);
	}
}
