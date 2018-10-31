package com.test.service;

import com.test.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

@Service
public class BookService {
//	@Qualifier("bookDao2")
//	@Autowired
//	@Resource(name = "bookDao2")
	@Inject
	private BookDao bookDao;

	public void print() {
		System.out.println(bookDao);
	}

}
