package com.test.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @title：自定义导入组件选择器
 * @author：xuan
 * @date：2018/10/30
 */
public class MyImportSelector implements ImportSelector {
	/**
	 * @title：返回值就是导入到容器中的组件的全类名数组
	 * @param annotationMetadata 当前标注@Import注解的类的所有注解信息
	 * @author：xuan
	 * @date：2018/10/30
	 */
	@Override
	public String[] selectImports(AnnotationMetadata annotationMetadata) {

		return new String[]{"com.test.bean.ioc.Blue", "com.test.bean.ioc.Yellow"};
	}
}
