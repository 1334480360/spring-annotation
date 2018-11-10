package com.test.tx;

import com.test.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	/**
	 * 添加用户
	 *
	 * @author xuan
	 * @date 2018/11/3
	 */
	@Transactional
	public void insertUser() {
		User user = new User("zhangsan", 27);
		userDao.insert(user);
	}
}
