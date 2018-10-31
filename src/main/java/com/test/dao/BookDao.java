package com.test.dao;

import com.test.bean.BaseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao extends BaseEntity {
	private int lable = 1;

	public BookDao() {
	}

	public BookDao(int lable) {
		this.lable = lable;
	}

	public int getLable() {
		return lable;
	}

	public void setLable(int lable) {
		this.lable = lable;
	}
}
