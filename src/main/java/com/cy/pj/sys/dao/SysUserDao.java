package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.entity.SysUserDept;

@Mapper
public interface SysUserDao {
	/**
	 * 修改密码操作
	 * @param username
	 * @param password
	 * @param salt
	 * @return
	 */
	@Update("update sys_users set password=#{password},salt=#{salt},modifiedTime=now() where username=#{username}")
	int updatePassword(String username,String password,String salt);
	
	@Select("select * from sys_users where username=#{username}")
	SysUser findUserByUserName(String username);
	
	/**
	 * 获取用户以及用户对应的部门信息
	 * @param id
	 * @return
	 */
	SysUserDept findObjectById(Integer id);
	
	/**
	 * 更新用户自身信息
	 * @param entity
	 * @return
	 */
	int updateObject(SysUser entity);
	 /**
	  * 保存用户自身信息
	  * @param entity
	  * @return
	  */
	 int insertObject(SysUser entity);
     /**
      * 禁用或启用用户信息
      * @param id
      * @param valid
      * @param username
      * @return
      */
	 @Update("update sys_users set valid=#{valid},modifiedUser=#{modifiedUser},modifiedTime=now() where id=#{id}")
	 int validById(Integer id,Integer valid,String modifiedUser);
	 /**
	  * 基于条件查询用户以及用户对应的部门信息
	  * @param username
	  * @return
	  */
	 List<SysUserDept> findPageObjects(String username);
}
