package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cy.pj.common.bo.Node;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.entity.SysUserMenu;
import com.cy.pj.sys.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	/**
	 * 基于用户id获取用户对应的一级菜单和二级菜单信息
	 */
	@Override
	public List<SysUserMenu> findUserMenusByUserId(Integer id) {
		//1.基于用户id获取角色id
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(id);
		//2.基于角色id获取菜单id
		List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleIds(roleIds);
		//3.基于菜单id获取一级菜单和二级菜单信息
		return sysMenuDao.findMenusByIds(menuIds);
	}
	
	@CacheEvict(value = "menuCache")
	@Override
	public int updateObject(SysMenu entity) {
		//1.参数校验
		//2.将数据保存到数据库
		int rows=sysMenuDao.updateObject(entity);
		return rows;
	}
	@CacheEvict(value = "menuCache")
	@Override
	public int saveObject(SysMenu entity) {
		//1.参数校验
		//2.将数据保存到数据库
		int rows=sysMenuDao.insertObject(entity);
		return rows;
	}
	
	@Override
	public List<Node> findZTreeMenuNodes() {
		// TODO Auto-generated method stub
		return sysMenuDao.findZTreeMenuNodes();
	}
	
	@CacheEvict(value = "menuCache")
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("id值无效");
		//2.基于id统计子菜单数并进行校验
		int childCount=sysMenuDao.getChildCount(id);
		if(childCount>0)
			throw new ServiceException("请先删除子菜单");
		//3.删除菜单角色关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		//4.删除菜单自身信息
		int rows=sysMenuDao.deleteObject(id);
		if(rows==0)
			throw new ServiceException("记录可能已经不存在了");
		return rows;
	}
	//Spring中的cache：Map<String,Cache>
	//将查询结果进行cache
	@Cacheable(value = "menuCache") //具体cache操作由aop实现(@Cacheable描述的方法为切入点方法)
	@Override
	public List<SysMenu> findObjects() {
		System.out.println("SysMenuServiceImpl.findObjects");
		List<SysMenu> list=sysMenuDao.findObjects();
		if(list==null||list.size()==0)
			throw new ServiceException("没有找到对应的菜单");
		return list;
	}
}
