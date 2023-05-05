package ru.virtu.my_test_app_01;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application_test.properties")
class SpringSecurityAppTests {

	@Test
	void contextLoads() {
	}

}
