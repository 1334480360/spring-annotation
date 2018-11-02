package com.test.config.aop;

import com.test.aop.LogAspects;
import com.test.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * aop
 *  在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式
 * '@EnableAspectJAutoProxy'：开启基于注解的aop动态代理
 *
 * @author xuan
 * @date 2018/11/1
 */
@Configuration
@EnableAspectJAutoProxy
public class MainConfigOfAOP {

	/**
	 * 业务逻辑类加入容器中
	 *
	 * @author xuan
	 * @date 2018/11/1
	 */
	@Bean
	public MathCalculator calculator() {
		return new MathCalculator();
	}

	/**
	 * 切面类加入容器中
	 *
	 * @author xuan
	 * @date 2018/11/1
	 */
	@Bean
	public LogAspects logAspects(){
		return new LogAspects();
	}

}
