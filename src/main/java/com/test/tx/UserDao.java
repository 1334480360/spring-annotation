package com.test.tx;

import com.test.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insert(User user) {
		String sql = "insert into user(username, age) values(?, ?)";
		jdbcTemplate.update(sql, new Object[]{user.getUsername(), user.getAge()});

	}
}
