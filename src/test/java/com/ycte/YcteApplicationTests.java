package com.ycte;

import com.ycte.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YcteApplicationTests {

	@Autowired
	UserServiceImpl userService;

	@Test
	void contextLoads() {
		userService.addUser("22920202209999", "aaa", "changeme", "a",
				"a", "a", "a");
	}

}
