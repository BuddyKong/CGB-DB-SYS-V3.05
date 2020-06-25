package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.bo.Node;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.entity.SysUserMenu;

@Mapper
public interface SysMenuDao {//SysMenuMapper
	/**
	 * 基于用户菜单id获取一级菜单，二级菜单信息
	 * @param menuIds
	 * @return
	 */
	List<SysUserMenu> findMenusByIds(
			List<Integer> menuIds);
	/**
	 * 基于菜单id获取授权标识对象
	 * @param menuIds
	 * @return
	 */
	List<String> findPermissions(List<Integer> menuIds);
	/**
	 * 将菜单信息写入到数据库
	 * @param entity
	 * @return
	 */
	int updateObject(SysMenu entity);
	/**
	 * 将菜单信息写入到数据库
	 * @param entity
	 * @return
	 */
	 int insertObject(SysMenu entity);
	 /**
	  * 查询所有菜单的id,name,parentId信息
	  * @return
	  */
	 @Select("select id,name,parentId from sys_menus")
	 List<Node> findZTreeMenuNodes();
	 /**
	  * 基于id统计子菜单的个数
	  * @param id
	  * @return
	  */
	 @Select("select count(*) from sys_menus where parentId=#{id}")
	 int getChildCount(Integer id);
	 
	 /**
	  * 基于id删除菜单信息
	  * @param id
	  * @return
	  */
	 @Delete("delete from sys_menus where id=#{id}")
	 int deleteObject(Integer id);
     /**
      * 查询菜单表中所有的菜单记录
      * 一行菜单记录映射为一个map对象(key为字段名，值为字段对应值)
      * @return
      */
	 List<SysMenu> findObjects();
}



