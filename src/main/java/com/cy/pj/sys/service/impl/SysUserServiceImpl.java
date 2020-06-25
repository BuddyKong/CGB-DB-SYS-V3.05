package com.cy.pj.sys.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.bo.PageObject;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.utils.ShiroUtils;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.entity.SysUserDept;
import com.cy.pj.sys.service.SysUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Transactional(timeout = 60,
               isolation = Isolation.READ_COMMITTED,
               readOnly = false,
               rollbackFor = Throwable.class,
               propagation = Propagation.REQUIRED)
@Service //spring容器存储bean时会以类名(首字母作为key),对象作为value存储到spring容器
public  class SysUserServiceImpl implements SysUserService{
	@Autowired
    private SysUserDao sysUserDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public int updatePassword(String sourcePassword, String newPassword, String cfgPassword) {
		//1.参数校验
		if(StringUtils.isEmpty(sourcePassword))
			throw new IllegalArgumentException("原密码不能为空");
		if(StringUtils.isEmpty(newPassword))
			throw new IllegalArgumentException("新密码不能为空");
		if(!newPassword.equals(cfgPassword))
			throw new IllegalArgumentException("两次新密码输入不一致");
		//校验输入的原密码是否正确
		SysUser user=ShiroUtils.getUser();//获取登陆用户
		String sourceHashedPassword=user.getPassword();
		SimpleHash sh=new SimpleHash("MD5", sourcePassword, user.getSalt(), 1);
		String hashedInputPassword=sh.toHex();
		if(!sourceHashedPassword.equals(hashedInputPassword))
			throw new IllegalArgumentException("原密码输入的不正确");
		//2.更新密码
		String newSalt=UUID.randomUUID().toString();
		sh=new SimpleHash("MD5", newPassword,newSalt, 1);
		String newHashedPassword=sh.toHex();
		int rows=sysUserDao.updatePassword(user.getUsername(), newHashedPassword, newSalt);
		if(rows==0)
			throw new ServiceException("用户可能已经不存在");
		return rows;
	}
	
	@Transactional(readOnly = true)
	//@Override
	public Map<String, Object> findObjectById(Integer id) {
		//1.参数校验
		//2.查询用户以及用户对应的部门信息
		SysUserDept user=sysUserDao.findObjectById(id);
		if(user==null)
			throw new ServiceException("此用户可能不存在");
		//3.查询用户对应的角色信息
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(id);
		//4.封装两次查询结果并返回
		Map<String,Object> map=new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	
	//@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		if(entity==null)
			 throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能空");
		if(roleIds==null||roleIds.length==0)
			throw new IllegalArgumentException("必须为用户分配角色");
		//.....
		//2.将用户信息更新到数据库
		int rows=sysUserDao.updateObject(entity);
		//3.将用户对应的角色信息写入到数据库
		//3.1删除原有关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		//3.2添加新的关系数据
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		if(rows==0)
			throw new ServiceException("记录可能不存在了");
		return rows;
	}
	//@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能空");
		if(StringUtils.isEmpty(entity.getPassword()))
			throw new IllegalArgumentException("密码不能空");
		if(roleIds==null||roleIds.length==0)
			throw new IllegalArgumentException("必须为用户分配角色");
		//.....
		//2.对密码进行加密
		String salt=UUID.randomUUID().toString();
		SimpleHash sh=new SimpleHash(
				"MD5",//algorithmName算法名称
				entity.getPassword(), //source要加密的密码
				salt, //加密盐
				1);//加密次数
		String pwd=sh.toHex();
		entity.setSalt(salt);
		entity.setPassword(pwd);
		//3.将用户信息写入到数据库
		int rows=sysUserDao.insertObject(entity);
		//4.将用户对应的角色信息写入到数据库
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		return rows;
	}
	/**
	 * 禁用启用操作
	 * Shiro框架通过@RequiresPermissions注解定义切入点，由注解描述的方法
	 * 表示要进行授权访问。那什么情况下会进行授权？在执行此方法时要基于
	 * 登陆用户获取用户的权限标识({sys:user:update,sys:user:delete,....}),
	 * 然后判断用户拥有的这些权限标识是否包含@RequiresPermissions注解中定义
	 * 的权限标识，假如包含则授权访问。
	 */
	@RequiresPermissions("sys:user:update")
	public int validById(Integer id, Integer valid) {
	    //1.参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("id值无效");
		if(valid!=1&&valid!=0)
			throw new IllegalArgumentException("状态值无效");
		//2.修改用户状态
		int rows=sysUserDao.validById(id, valid, "admin");//后续会将用户名修改为登陆用户，现在是假数据
		//3.验证结果
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		return rows;
	}
	@Transactional(readOnly = true)//readOnly事务中不允许执行更新操作
	@RequiredLog(operation = "分页查询")
	//@Override
	public PageObject<SysUserDept> findPageObjects(String username, Integer pageCurrent) {
		String tName=Thread.currentThread().getName();
		System.out.println("SysUserService.findPageObjects.thread-->"+tName);
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("页码值无效");
		//2.设置分页参数
		int pageSize=3;
		Page<SysUserDept> page=PageHelper.startPage(pageCurrent,pageSize);
		//3.查询当前页记录
		List<SysUserDept> records=sysUserDao.findPageObjects(username);
		return new PageObject<>(records, (int)page.getTotal(), (int)page.getPages(), pageSize, pageCurrent);
	}

}
