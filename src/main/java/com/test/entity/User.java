package com.test.entity;

import com.test.bean.BaseEntity;

public class User extends BaseEntity {
	private int id;
	private String username;
	private int age;

	public User(String username, int age) {
		this.username = username;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
