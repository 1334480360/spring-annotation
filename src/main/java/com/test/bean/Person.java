package com.test.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person extends BaseEntity{
	@Value("xuan")
	private String name;
	@Value("27")
	private int age;
	@Value("#{20-7}")
	private int count;
	@Value("${person.nickName}")
	private String nickName;

	public Person() {
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
