package com.cy.pj.common.demos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTests {

	@Test
	void testSendMsg() {
		MailService mailService=new LogMailService(new LogAspect());
		mailService.send(" hello cgb2003");
		//代理对象->目标对象
	}
}
