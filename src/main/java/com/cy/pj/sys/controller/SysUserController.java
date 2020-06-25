package com.cy.pj.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;

@RestController
@RequestMapping("/user/")
public class SysUserController {
	
     @Autowired
	 private SysUserService sysUserService;
     
     @RequestMapping("doUpdatePassword")
     public JsonResult doUpdatePassword(String pwd,String newPwd,String cfgPwd) {
    	  sysUserService.updatePassword(pwd, newPwd, cfgPwd);
    	 return new JsonResult("update ok");
     }
     @RequestMapping("/doLogin")
     public JsonResult doLogin(String username,
    		 String password,
    		 boolean isRememberMe) {
    	 UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    	 //设置记住我功能
    	 token.setRememberMe(isRememberMe);
    	
    	 Subject currentUser = SecurityUtils.getSubject();
    	 currentUser.login(token);//将用户信息提交给securitymanager进行认证
    	 return new JsonResult("login ok");
     }
     
     @RequestMapping("doFindObjectById")
     public JsonResult doFindObjectById(Integer id) {
    	 return new JsonResult(sysUserService.findObjectById(id));
     }
     
     @RequestMapping("doUpdateObject")
     public JsonResult doUpdateObject(SysUser entity,Integer[] roleIds) {
    	 sysUserService.updateObject(entity, roleIds);
    	 return new JsonResult("update ok");
     }
     @RequestMapping("doSaveObject")
     public JsonResult doSaveObject(SysUser entity,Integer[] roleIds) {
    	 sysUserService.saveObject(entity, roleIds);
    	 return new JsonResult("save ok");
     }
 	
     @RequestMapping("doValidById")
     public JsonResult doValidById(Integer id,Integer valid) {
    	 sysUserService.validById(id, valid);
    	 return new JsonResult("update ok");
     }
     
     @RequestMapping("doFindPageObjects")
     public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
    	 return new JsonResult(sysUserService.findPageObjects(username, pageCurrent));
     }
}
/**
 * FAQ? 如何提高系统的响应速度？(性能)
 * 分析：影响系统性能的因素有哪些呢？(请求/响应模型)
 * 1)请求数据的传输时间？(数据量-压缩,带宽-加大，距离)
 * 2)请求数据的处理时间? (数据结构，算法，架构，磁盘，cpu，内存)
 * 3)响应数据的传输时间？(数据量，带宽，距离)
 * 4)响应数据的渲染时间？(数据量-局部刷新)
 * 
 * FAQ? 系统出现了线程不安全？
 * 分析：导致线程不安全的因素有哪些？
 * 1) 多个线程并发执行。
 * 2) 多个线程有共享数据集。
 * 3) 在共享数据集上的操作为非原子操作。
 * 解决方案：
 * 1) 取消线程并发(一个线程执行所有操作)
 * 2) 取消数据共享(每个线程一个数据，类似每个线程一个Connection对象)
 * 3) 加锁(排他锁，共享锁,...)或者CAS算法
 * 
 * FAQ? 系统中数据出现乱码，如何解决？
 * 分析：出现乱码的原因往往是编码和解码方式不一致所导致。
 * 在实际项目中可能会出现乱码的地方有两个？
 * 1)请求数据乱码？(请求方式，客户端编码->服务端解码)
 * 2)响应数据乱码？(数据来源:数据库还是文件,服务端编码->客户端解码)
 * ......
 */












