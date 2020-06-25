package com.cy.pj.sys.service;

import java.util.Map;

import com.cy.pj.common.bo.PageObject;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.entity.SysUserDept;

public interface SysUserService {
	
	
	
	int updatePassword(String sourcePassword,String newPassword,String cfgPassword);
	 /**
	  * 基于用户id查询用户信息，用户对应的部门信息，用户对应的角色信息
	  * @param id
	  */
	 Map<String,Object> findObjectById(Integer id);
	
	 int updateObject(SysUser entity,Integer[] roleIds);
	 
	 int saveObject(SysUser entity,Integer[] roleIds);
	
	 int validById(Integer id,Integer valid);

	 PageObject<SysUserDept> findPageObjects(String username,
			                               Integer pageCurrent);
}
