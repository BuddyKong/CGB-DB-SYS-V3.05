package com.cy.pj.sys.service;

import java.util.List;

import com.cy.pj.common.bo.CheckBox;
import com.cy.pj.common.bo.PageObject;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.entity.SysRoleMenu;

public interface SysRoleService {
	/**
	 * 查询所有角色的id和名字
	 * @return
	 */
	List<CheckBox> findObjects();
	
	SysRoleMenu findObjectById(Integer id);
	/**
	 * 更新角色以及角色和菜单关系数据
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int updateObject(SysRole entity,Integer[]menuIds);
	/**
	 * 保存角色以及角色和菜单关系数据
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int saveObject(SysRole entity,Integer[]menuIds);
	/**
	 * 基于角色id删除角色关系数据以及自身信息
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	 
	PageObject<SysRole> findPageObjects(
			 String name,Integer pageCurrent);
	 
}
