package com.test.bean;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @title：基础实体
 * @author：xuan
 * @date：2018/3/20
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -5651501467149731391L;

	@Override
    public String toString() {
		 return ToStringBuilder.reflectionToString(this,
	                ToStringStyle.MULTI_LINE_STYLE);
    }  
	
}