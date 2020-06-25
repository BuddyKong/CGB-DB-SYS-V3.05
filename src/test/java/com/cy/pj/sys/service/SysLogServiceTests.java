package com.cy.pj.sys.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.bo.PageObject;
import com.cy.pj.sys.entity.SysLog;

@SpringBootTest
public class SysLogServiceTests {

	 /**
	  * 假如测试时在此位置出现了依赖注入异常，应
	  * 1)检测此接口是否有实现类
	  * 2)检测其实现类(SysLogServiceImpl)是否交给了spring管理(首先确定包正确，然后确定有特定注解描述)
	  */
	 @Autowired
	 private SysLogService sysLogService;
	 
	 @Test
	 void testFindPageObjects() {
		 PageObject<SysLog> pageObject=
		 sysLogService.findPageObjects("admin", 1);
		 System.out.println(pageObject);
	 }
}
