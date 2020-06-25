package com.cy.pj.sys.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.bo.PageObject;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@RestController
@RequestMapping("/log/")
public class SysLogController {

	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping("doDeleteObjects")
	//@ResponseBody
	public JsonResult doDeleteObjects(Integer...ids) {
		System.out.println(Arrays.toString(ids));
		//调用业务层方法执行删除业务
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok");
	}
	
	@RequestMapping("doFindPageObjects")
	//@ResponseBody
	public JsonResult doFindPageObjects(String username,
			Integer pageCurrent){
		PageObject<SysLog> pageObject=
		sysLogService.findPageObjects(username, pageCurrent);
		return new JsonResult(pageObject);//DispatcherServlet
	}
	
}








