package com.test.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Boss extends BaseEntity{

	private Car car;


//	@Autowired
	public Boss(Car car) {
		System.out.println("boss car...");
		this.car = car;
	}

	public Car getCar() {
		return car;
	}

//	@Autowired
	public void setCar(@Autowired Car car) {
		this.car = car;
	}
}
