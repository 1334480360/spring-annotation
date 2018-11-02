package com.test.bean.ioc;

import org.springframework.beans.factory.FactoryBean;

/**
 * @title：工厂Bean
 * @author：xuan
 * @date：2018/10/30
 */
public class ColorFactoryBean implements FactoryBean<Color> {
	/**
	 * @title：返回对象，添加到容器中
	 * @author：xuan
	 * @date：2018/10/30
	 */
	@Override
	public Color getObject() {
		System.out.println("注册对象");
		return new Color();
	}

	/**
	 * @title：返回对象类型
	 * @author：xuan
	 * @date：2018/10/30
	 */
	@Override
	public Class<?> getObjectType() {
		return Color.class;
	}

	/**
	 * @title：控制对象是否为单例
	 * @author：xuan
	 * @date：2018/10/30
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}
}
