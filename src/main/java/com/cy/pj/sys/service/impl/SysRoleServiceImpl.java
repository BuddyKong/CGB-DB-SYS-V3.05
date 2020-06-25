package com.cy.pj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cy.pj.common.bo.CheckBox;
import com.cy.pj.common.bo.PageObject;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.entity.SysRoleMenu;
import com.cy.pj.sys.service.SysRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public List<CheckBox> findObjects() {
		return sysRoleDao.findObjects();
	}
	
	@Override
	public SysRoleMenu findObjectById(Integer id) {
		//1.参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("id值无效");
		//2.查询角色自身信息
		SysRoleMenu rm=sysRoleDao.findObjectById(id);//id,name,note
		//3.查询角色对应的菜单id
		//List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleId(id);
		//4.封装两次查询结果并返回
		//rm.setMenuIds(menuIds);
		return rm;
	}
	
	@Transactional
	@Override
	public int saveObject(SysRole entity, Integer[] menuIds) {
		//1.参数校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))//StringUtils用的是spring框架的一个工具类
			throw new IllegalArgumentException("角色名不允许为空");
		if(menuIds==null||menuIds.length==0)
			throw new IllegalArgumentException("需要为角色分配权限");
		//2.保存角色自身信息
		int rows=sysRoleDao.insertObject(entity);
		//3.保存角色和菜单关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		return rows;
	}
	@Override
	public int updateObject(SysRole entity, Integer[] menuIds) {
		//1.参数校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))//StringUtils用的是spring框架的一个工具类
			throw new IllegalArgumentException("角色名不允许为空");
		if(menuIds==null||menuIds.length==0)
			throw new IllegalArgumentException("需要为角色分配权限");
		//2.更新角色自身信息
		int rows=sysRoleDao.updateObject(entity);
		//3.更新角色和菜单关系数据
		//3.1删除原有关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		//3.2添加新的关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		return rows;
	}
	
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		if(id==null||id<1)
		 throw new IllegalArgumentException("id值无效");
		//2.删除关系数据
		//2.1删除角色菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		//2.2删除角色和用户关系数据
		sysUserRoleDao.deleteObjectsByRoleId(id);
		//3.删除自身信息
		int rows=sysRoleDao.deleteObject(id);
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		return rows;
	}
	
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
		  throw new IllegalArgumentException("页码值无效");
		//2.查询当前页角色记录
		//2.1设置查询参数
		int pageSize=2;
		Page<SysRole> page=PageHelper.startPage(pageCurrent, pageSize);
		//2.2启动查询操作
		List<SysRole> records=sysRoleDao.findPageObjects(name);
		//3.封装查询结果
		return new PageObject<>(records, (int)page.getTotal(),
				               (int)page.getPages(), pageSize, pageCurrent);
	}
	
	/*
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
	    //1.参数校验
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("页码值无效");
		//2.查询总记录数并校验
		int rowCount=sysRoleDao.getRowCount(name);
		
		if(rowCount==0)
			throw new ServiceException("没有对应的记录");
		//3.查询当前页角色记录
		int pageSize=2;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysRole> records=
		sysRoleDao.findPageObjects(name, startIndex, pageSize);
		//4.封装查询结果并返回
		return new PageObject<>(records, rowCount, pageSize, pageCurrent);
	}
	*/

}
