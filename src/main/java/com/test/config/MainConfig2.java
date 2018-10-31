package com.test.config;

import com.test.bean.Color;
import com.test.bean.ColorFactoryBean;
import com.test.bean.Person;
import com.test.bean.Red;
import com.test.condition.MacCondition;
import com.test.condition.MyImportBeanDefinitionRegistrar;
import com.test.condition.MyImportSelector;
import com.test.condition.WindowsCondition;
import org.springframework.context.annotation.*;

@Conditional({MacCondition.class})
@Configuration
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

	/**
	 * @title：
	 * 1、Scope：调整作用域
	 *  singleton单实例（默认值）：ioc容器启动时会调用方法创建对象放到ioc容器中，以后每次获取就是直接从容器中拿实例。
	 *  prototype多实例：ioc容器启动不会创建对象，每次获取时才会调用方法创建实例。
	 *  request同一次请求创建一个实例，
	 *  session同一个session创建一个实例
	 * 2、Lazy：懒加载
	 *  针对singleton，容器启动不创建对象，第一次获取时才创建实例
	 * @author：xuan
	 * @date：2018/10/30
	 */
//	@Scope("prototype")
	@Lazy
	@Bean
	public Person person() {
		System.out.println("给容器添加Person...");
		return new Person("zhangsan", 25);
	}

	@Conditional({WindowsCondition.class})
	@Bean("bill")
	public Person person01() {
		return new Person("Bill Gates", 62);
	}

	@Conditional({MacCondition.class})
	@Bean("mac")
	public Person person02() {
		return new Person("Mac", 48);
	}

	@Bean
	public ColorFactoryBean colorFactoryBean() {
		return new ColorFactoryBean();
	}

}
