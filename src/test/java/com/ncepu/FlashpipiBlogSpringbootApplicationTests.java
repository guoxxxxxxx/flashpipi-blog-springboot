package com.ncepu;

import com.ncepu.dao.UserDao;
import com.ncepu.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlashpipiBlogSpringbootApplicationTests {

	@Autowired
	private UserDao userDao;
	@Test
	void contextLoads() {
		User user = userDao.selectById(1);
		System.out.println(user);
	}

}
