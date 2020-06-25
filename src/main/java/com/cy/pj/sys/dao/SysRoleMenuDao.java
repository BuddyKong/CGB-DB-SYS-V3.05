package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysRoleMenuDao {
	/**
	 * 基于角色id查询角色对应的菜单id
	 * @param id
	 * @return
	 */
	@Select("select menu_id from sys_role_menus where role_id=#{id}")
	List<Integer> findMenuIdsByRoleId(Integer id);
	
	/**
	 * 基于多个角色id查询对应的菜单id
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(List<Integer> roleIds);
	
	/**
	 * 保存角色和菜单的关系数据
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int insertObjects(Integer roleId,Integer[]menuIds);
	/**
	 * 基于角色id删除菜单和角色关系数据
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_role_menus where role_id=#{id}")
	int deleteObjectsByRoleId(Integer id);

	 /**
	  * 基于菜单id删除角色菜单关系数据
	  * @param id
	  * @return
	  */
	 @Delete("delete from sys_role_menus where menu_id=#{id}")
	 int deleteObjectsByMenuId(Integer id);
}
