package com.test.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * '@profile'： Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能
 *
 * @author xuan
 * @date 2018/10/31
 */
@PropertySource("classpath:/dbconfig.properties")
@Configuration
public class MainConfigOfProfile {

	@Value("${db.url}")
	private String url;
	@Value("${db.userName}")
	private String userName;
	@Value("${db.password}")
	private String password;

	@Profile("dev")
	@Bean
	public DataSource dataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setURL(url);
		dataSource.setUser(userName);
		dataSource.setPassword(password);
		return dataSource;
	}

}
